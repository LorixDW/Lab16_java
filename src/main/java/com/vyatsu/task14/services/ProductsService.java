package com.vyatsu.task14.services;

import com.vyatsu.task14.entities.Filter;
import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.repositories.IProductRepository;
import com.vyatsu.task14.repositories.ProductRepository;
import com.vyatsu.task14.repositories.specifications.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private IProductRepository repository;

    @Autowired
    public void setProductRepository(IProductRepository iProductRepository) {
        repository = iProductRepository;
    }

    public Product getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return repository.findAll((ProductSpecs.titleContainsWord("")));
    }
    public List<Product> getFilterProduct(Filter filter){
        return repository.findAll(ProductSpecs.titleContainsWord(filter.getPattern())
                .and(ProductSpecs.priceGreaterThanOrEq(BigDecimal.valueOf(filter.getMin())))
                .and(ProductSpecs.priceLesserThanOrEq(BigDecimal.valueOf(filter.getMax()))));
    }

    public void add(Product product) {
        repository.save(product);
    }
    public void remove(Product product){
        repository.delete(product);
    }
    public void update(Product product){
        repository.updateTitleAndPriceById(product.getTitle(), product.getPrice(), product.getId());
    }
    public void ViewIncrement(Long id){
        repository.AddView(id);
    }
    public List<Product> GetThreeMostSeen(){
        return getAllProducts().stream().sorted((p1, p2) -> p2.getView() - p1.getView()).limit(3).collect(Collectors.toList());
    }
    public long GetNextId(){
        Product product = getAllProducts().stream().sorted((p1, p2) -> (int) (p2.getId() - p1.getId())).findFirst().orElse(null);
        if(product != null){
            return (int) (product.getId() + 1);
        }
        else {
            return 1;
        }
    }
}
