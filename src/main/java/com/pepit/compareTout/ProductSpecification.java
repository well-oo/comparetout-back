package com.pepit.compareTout;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.Product;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.service.CriteriaService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductSpecification implements Specification<Product> {

    private ProductType productType;
    private CriteriaService criteriaService;
    private String search;
    private String descriptionSearch;

    public ProductSpecification(String search,
                                ProductType productType,
                                CriteriaService criteriaService,
                                String descriptionSearch) {
        super();
        this.criteriaService = criteriaService;
        this.search = search;
        this.productType = productType;
        this.descriptionSearch = descriptionSearch;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add( criteriaBuilder.equal( root.get("productType"), productType ) );
        predicates.add( criteriaBuilder.like( root.get("description"), "%"+descriptionSearch+"%") );

        if (search != null && !search.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d+)-([^-]+),");
            Matcher matcher = pattern.matcher(search + ",");
            Join join;

            while (matcher.find()) {
                Criteria criteria = criteriaService.findById( Long.valueOf(matcher.group(1)) );
                join = root.join("valueCriteriaProductList");
                switch ( criteria.getComparisonMethod().getComparisonType().getName() ) {
                    case "Enum":
                        predicates.add( criteriaBuilder.and( criteriaBuilder.equal(join.get("criteria"), criteria),
                                criteriaBuilder.equal(join.get("value"), matcher.group(2)) ) );
                        break;
                    case "Range":
                        String[] values = matcher.group(2).split("/");
                        predicates.add( criteriaBuilder.and( criteriaBuilder.equal(join.get("criteria"), criteria),
                                criteriaBuilder.between(join.get("value"), values[0], values[1]) ) );
                        break;
                    default:
                        return null;
                }
            }
        }
        return criteriaBuilder.and( predicates.toArray(new Predicate[0]) );
    }

}