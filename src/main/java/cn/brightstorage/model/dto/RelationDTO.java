package cn.brightstorage.model.dto;

import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RelationDTO {

    private Long id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @Length(max = 1023)
    private String avatar;

    private UserVO owner;

    private List<UserVO> members;
}
