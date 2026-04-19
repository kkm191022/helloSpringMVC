package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "productList";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        // 수정한 생성자 파라미터에 맞춰 초기값 설정 (Category는 null로 시작)
        model.addAttribute("product", new Product("", null, BigDecimal.ZERO, ""));
        return "productForm";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("categoryName") String categoryName,
                                RedirectAttributes redirectAttributes) {

        // 수정한 서비스 메서드 호출 (상품 객체와 카테고리 이름을 함께 전달)
        productService.createProduct(product, categoryName);

        redirectAttributes.addFlashAttribute("successMessage",
                "'" + product.getName() + "' 상품이 등록되었습니다.");

        return "redirect:/products";
    }
}