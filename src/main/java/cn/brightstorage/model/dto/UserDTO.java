package cn.brightstorage.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;

@Data
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String nickname;

    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String phone;

    private String avatar;
}
