package cn.brightstorage.model.vo;

import cn.brightstorage.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoVO {

    private String token;

    private UserDTO user;
}
