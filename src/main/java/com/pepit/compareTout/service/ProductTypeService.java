package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.Product;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductService productService;

    public ProductType findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isPresent()){
            return productType.get();
        } else {
            throw new NoSuchElementException("No productType with this id");
        }
    }

    public ProductType create(ProductType productType){
        if(productType == null){
            throw new IllegalArgumentException("ProductType must not be null");
        }
        return productTypeRepository.save(productType);
    }

    public Collection<ProductType> findAll(){
        return productTypeRepository.findAll();
    }

    public void delete(Long id){
        Collection<Product> products;
        if(productService==null)
            products = new ArrayList<>();
        else
            products = productService.findByProductType(id);

        for(Product p : products)
            productService.delete(p.getIdProduct());
        productTypeRepository.delete(this.findById(id));
    }

    public void removeCriteria(Criteria criteria){
        Collection<ProductType> productTypes = productTypeRepository.findAllByCriteriaListContains(criteria);
        for (ProductType productType: productTypes) {
            List<Criteria> criteriaList = productType.getCriteriaList();
            criteriaList.removeIf(criteria1 -> (criteria1.equals(criteria)));
            productType.setCriteriaList(criteriaList);
            productTypeRepository.save(productType);
        }
    }

    public ProductType update(ProductType productType, Long id) {
        if(productType == null){
            throw new IllegalArgumentException("Product must not be null");
        }
        ProductType pType = this.findById(id);
        pType.setName(productType.getName());
        pType.setHeaderImage(productType.getHeaderImage());
        pType.setFavicon(productType.getFavicon());
        pType.setTitleWebPage(productType.getTitleWebPage());
        return productTypeRepository.save(pType);
    }

}
