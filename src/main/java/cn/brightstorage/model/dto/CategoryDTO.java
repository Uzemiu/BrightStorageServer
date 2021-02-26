package cn.brightstorage.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "分类名称不能为空")
    @Length(max = 255, message = "分类名称不能超过255个字符")
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long count;
}
