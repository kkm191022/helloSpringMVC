package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.repository.CategoryRepository;
import kr.ac.hansung.cse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void createProduct(Product product, String categoryName) {
        // 1. 입력받은 카테고리 이름으로 DB에서 Category 객체 조회
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    // 카테고리가 없으면 새로 생성 (과제 편의상)
                    Category newCat = new Category(categoryName);
                    categoryRepository.save(newCat);
                    return newCat;
                });

        // 2. 상품 객체에 조회된 카테고리 객체 주입
        product.setCategory(category);

        // 3. 최종 저장
        productRepository.save(product);
    }
}