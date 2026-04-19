package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.model.CategoryForm;
import kr.ac.hansung.cse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 1. 카테고리 목록 조회
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    // 2. 새 카테고리 등록 화면 요청
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        return "categoryForm";
    }

    // 3. 새 카테고리 저장 처리
    @PostMapping("/create")
    public String createCategory(@ModelAttribute("categoryForm") CategoryForm form) {
        categoryService.addCategory(form.getName());
        return "redirect:/categories";
    }

    // 4. 카테고리 삭제 처리
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes re) {
        try {
            categoryService.deleteCategory(id);
        } catch (RuntimeException e) {
            // 삭제 실패 시(상품이 연결된 경우 등) 에러 메시지 전달
            re.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/categories";
    }
}