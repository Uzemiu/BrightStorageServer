package cn.brightstorage.model.dto;

import cn.brightstorage.model.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class StorageDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserVO owner;

    private Integer type;

    private String name;

    private Integer amount;

    private Long parentId;

    private Boolean access;

    private String image;

    private Boolean deleted;

    private Date expireTime;

    private String note;
}
