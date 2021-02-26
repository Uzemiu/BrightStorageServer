package cn.brightstorage.model.dto;

import cn.brightstorage.model.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class StorageUnitDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserVO owner;

    private Integer type;

    @Length(max = 255, message = "物品名称不能超过255个字符")
    private String name;

    private Integer amount;

    private Long parentId;

    private Boolean access;

    @Length(max = 1023, message = "图片地址不能超过1023个字符")
    private String image;

    private Boolean deleted;

    private Date expireTime;

    @Length(max = 1023, message = "注释不能超过1023个字符")
    private String note;

    private Set<CategoryDTO> categories;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<StorageUnitDTO> children;
}
