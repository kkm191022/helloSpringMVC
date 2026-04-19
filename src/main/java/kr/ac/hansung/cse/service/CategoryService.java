package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        // [중요] 해당 카테고리를 사용 중인 상품이 있는지 먼저 확인
        long count = categoryRepository.countProductsByCategoryId(id);
        if (count > 0) {
            throw new RuntimeException("해당 카테고리에 등록된 상품이 있어 삭제할 수 없습니다.");
        }
        categoryRepository.delete(id);
    }
}