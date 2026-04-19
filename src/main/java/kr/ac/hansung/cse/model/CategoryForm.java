package kr.ac.hansung.cse.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 등록 폼 데이터를 바인딩하기 위한 DTO
 */
@Getter
@Setter
public class CategoryForm {

    @NotBlank(message = "카테고리 이름을 입력하세요")
    @Size(max = 50, message = "카테고리 이름은 50자 이내여야 합니다")
    private String name;


    public CategoryForm() {}

    public CategoryForm(String name) {
        this.name = name;
    }
}