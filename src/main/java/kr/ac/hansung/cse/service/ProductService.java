package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // [추가] 이름으로 상품 검색 로직
    @Transactional(readOnly = true)
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    // [추가] 카테고리 ID로 상품 필터링 로직
    @Transactional(readOnly = true)
    public List<Product> searchByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}