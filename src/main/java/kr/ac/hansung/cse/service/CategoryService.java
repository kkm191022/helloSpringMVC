package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Transactional
    public void createCategory(String name) {

        categoryRepository.findByName(name).ifPresent(c -> {
            throw new RuntimeException("이미 존재하는 카테고리입니다: " + name);
        });

        Category category = new Category(name);
        categoryRepository.save(category);
    }


    @Transactional
    public void deleteCategory(Long id) {

        long count = categoryRepository.countProductsByCategoryId(id);
        if (count > 0) {
            throw new IllegalStateException("해당 카테고리를 사용하는 상품이 " + count + "개 있어 삭제할 수 없습니다.");
        }

        categoryRepository.delete(id);
    }
}