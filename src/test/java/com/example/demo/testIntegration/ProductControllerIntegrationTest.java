package com.example.demo.testIntegration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepit.compareTout.CompareToutApplication;
import com.pepit.compareTout.entity.Product;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.service.ProductService;
import com.pepit.compareTout.service.ProductTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CompareToutApplication.class)
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductTypeService productTypeService;

    @Test
    public void testGetAllProducts() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/products/", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertThat(responseJson.isMissingNode(), is(false));
        //assertThat(responseJson.toString().isEmpty(), equalTo("[]"));
    }

    @Test
    public void testGetOneProduct() throws IOException {
        ProductType productType = new ProductType("ProductTypeTest1", "./image.jpg", "./favicon.jpg",
                "Page test");
        productTypeService.create(productType);
        Product product = new Product("ProduitTest1", null, "Produit numéro 1 affecté au test.",
                null, productType, null,"");
        productService.create(product);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/products/"+product.getIdProduct(), String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertThat(responseJson.isMissingNode(), is(false));
        assertThat(responseJson.get("description").asText(), equalTo(product.getDescription()));
    }
}
