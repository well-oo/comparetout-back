package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.*;
import com.pepit.compareTout.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("api/addData")
@Slf4j
public class AddDataController {

    public AddDataController(CriteriaService criteriaService, ComparisonTypeService comparisonTypeService, DataTypeService dataTypeService, ComparisonMethodService comparisonMethodService, EmailTypeService emailTypeService, EmailService emailService, ProductTypeService productTypeService, CriteriaImportanceService criteriaImportanceService, ProductService productService, UserService userService) {
        this.criteriaService = criteriaService;
        this.comparisonTypeService = comparisonTypeService;
        this.dataTypeService = dataTypeService;
        this.comparisonMethodService = comparisonMethodService;
        this.emailTypeService = emailTypeService;
        this.emailService = emailService;
        this.productTypeService = productTypeService;
        this.criteriaImportanceService = criteriaImportanceService;
        this.productService = productService;
        this.userService = userService;
    }

    private int randInt(int min, int max) {
        Random rand=new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private final ComparisonTypeService comparisonTypeService;
    private final DataTypeService dataTypeService;
    private final ComparisonMethodService comparisonMethodService;
    private final EmailTypeService emailTypeService;
    private final EmailService emailService;
    private final ProductTypeService productTypeService;
    private final CriteriaImportanceService criteriaImportanceService;
    private final CriteriaService criteriaService;
    private final ProductService productService;
    private final UserService userService;

    private String getUrTypeProduit(String type){
        switch(type){
            case "Ecran d'ordinateur":
                return "https://www.cybertek.fr/images_produits/4f02e729-d179-4347-86b5-910c9ebb6529.jpg";
            case "Mobile & Smartphone":
                return "https://dyw7ncnq1en5l.cloudfront.net/optim/produits/642/50925/xiaomi-mi-9_5db2afabb6f51ba0__908_512__overflow.jpg";
            case "PC portable":
                return "https://www.cybertek.fr/images_produits/3a6c9974-12d1-4526-9c45-4268548f6616.jpg";
            case "Cartouche d'imprimante":
                return "https://static.fnac-static.com/multimedia/Images/FD/Comete/88617/CCP_IMG_ORIGINAL/1123165.jpg";
        }
        return "erreur.jpg";
    }

    @GetMapping("/add")
    public void addDataTest() throws IOException, InvalidFormatException {
        System.out.println("Ajout de données de test en cours...");
        //add dataType
        List<DataType> dataTypes =new ArrayList<>();
        dataTypes.add(new DataType("Textuel"));
        dataTypes.add(new DataType("Nombre"));
        for(DataType o : dataTypes){
            dataTypeService.create(o);
        }
        //add comparisonType
        List<ComparisonType> comparisonTypes =new ArrayList<>();
        comparisonTypes.add(new ComparisonType("Enum"));
        comparisonTypes.add(new ComparisonType("Range"));
        for(ComparisonType o : comparisonTypes){
            comparisonTypeService.create(o);
        }
        //add comparisonMethodService
        List<ComparisonMethod> comparisonMethods =new ArrayList<>();
        comparisonMethods.add(new ComparisonMethod(comparisonTypes.get(0),dataTypes.get(0)));
        comparisonMethods.add(new ComparisonMethod(comparisonTypes.get(1),dataTypes.get(1)));
        for(ComparisonMethod o : comparisonMethods){
            comparisonMethodService.create(o);
        }
        //add typemail
        List<EmailType> emailTypes =new ArrayList<>();
        emailTypes.add(new EmailType("Abonnement"));
        emailTypes.add(new EmailType("Fournisseur : erreur ajout donnnée via API"));
        emailTypes.add(new EmailType("Un utilisateur contacte un fournisseur"));
        for(EmailType o : emailTypes){
            emailTypeService.create(o);
        }
        //ajout email
        List<Email> emails =new ArrayList<>();
        for(int i =1 ; i<50;i++){
            emails.add(new Email("Objet du mail "+i, "emailFrom"+i+"@from.com", "emailTo@"+i+"@to.com", Instant.now(), emailTypes.get(this.randInt(0,emailTypes.size()-1))));
        }
        for(Email o : emails){
            emailService.create(o);
        }
        //add criteriaImportance
        List<CriteriaImportance> criteriaImportances =new ArrayList<>();
        criteriaImportances.add(new CriteriaImportance(1,50000));
        criteriaImportances.add(new CriteriaImportance(2,1000));
        criteriaImportances.add(new CriteriaImportance(3,980));
        criteriaImportances.add(new CriteriaImportance(4,960));
        for(int i = 5;i<50;i++){
            criteriaImportances.add(new CriteriaImportance(i,960-(i*2)));
        }
        for(CriteriaImportance o : criteriaImportances){
            criteriaImportanceService.create(o);
        }

        User u = userService.findByName("supplier");

        HashMap<String,String> excels =new HashMap<String,String>();
        excels.put("Ecran d'ordinateur","Ecranordinateur.xlsx");
        excels.put("Cartouche d'imprimante","Cartoucheimprimante.xlsx");
        excels.put("Mobile & Smartphone","Mobile&smartphone.xlsx");
        excels.put("PC portable","PCportable.xlsx");
        excels.forEach((TypeProduit, FichierExcel) -> {
            System.out.println("Lecture de : "+FichierExcel);
            Resource resource = new ClassPathResource(FichierExcel);
            HashMap<Integer, Criteria> numColonne_toCriteria = new HashMap<>();
            File file = null;
            try {
                file = resource.getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(Objects.requireNonNull(file));
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }

            Sheet sheet = Objects.requireNonNull(workbook).getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = sheet.rowIterator();

            List<Criteria> criteriaListeAdd=new ArrayList<>();
            ProductType productType=new ProductType();
            List<Product> productsAdd =new ArrayList<>();

            boolean isFirstLine = true;

            // ITERATION LIGNES
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                List<ValueCriteriaProduct> valuesCriteres =new ArrayList<>();
                int numColonne = 0;
                String picture = "";
                String description = "";
                String name = "";

                // ITERATION COLONNES
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    // CREATION DES CRITERES
                    if(isFirstLine){
                        List<String> forbidenColumns = Arrays.asList("ID","Image","URL","Fournisseur","Grande catégorie","Sous catégorie","Sous sous catégorie","Nom","Description", "Modèle");
                        // SI LE CRITERE N'EST PAS CONTENU DANS LA LISTE DU DESSUS
                        if(!forbidenColumns.contains(cellValue)){
                            Criteria criteria = criteriaService.findByName(cellValue);
                            //SI LE CRITERE N'EXISTE PAS DEJA ON LE CREE
                            if(criteria == null){
                                criteria = (cellValue.equals("Prix (€)")) ? criteriaService.create(new Criteria(cellValue, false,false,"",comparisonMethods.get(1))) : criteriaService.create(new Criteria(cellValue, false,false,"",comparisonMethods.get(0)));
                            }
                            criteriaListeAdd.add(criteria);
                            // AJOUT DICTIONNAIRE KEY (numColonne) / VALUE (criteria)
                            numColonne_toCriteria.put(numColonne, criteria);
                        }
                        // SI LE CRITERE EST CONTENU DANS LA LISTE DU DESSUS
                        else {
                            numColonne_toCriteria.put(numColonne,null);
                        }
                    }
                    // AJOUT DES VALEURS
                    else{
                        Criteria currentCriteria = numColonne_toCriteria.get(numColonne);
                        // SI LA VALEUR CORRESPOND A UN CRITERE
                        if(currentCriteria != null) {
                            valuesCriteres.add(new ValueCriteriaProduct(currentCriteria, cellValue));
                        }
                        if(numColonne == 0) picture = cellValue;
                        else if (numColonne == 6) name = cellValue;
                        else if (numColonne == 7) description = cellValue;
                    }
                    numColonne++;
                }

                if(isFirstLine){
                    isFirstLine = false;
                    productType=new ProductType(TypeProduit,getUrTypeProduit(TypeProduit), "img/favicon.png", TypeProduit, criteriaListeAdd);
                    productTypeService.create(productType);
                }
                else {
                    productsAdd.add(new Product(picture,valuesCriteres,description, Instant.now(),productType,u, name));
                }
            }

            for(Product p : productsAdd){
                productService.create(p);
            }
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
