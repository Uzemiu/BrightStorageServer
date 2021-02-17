package cn.brightstorage.model.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginParam {

    @ApiParam(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiParam("加密过的密码")
    private String password;

    @ApiParam("短信验证码")
    private String code;
}
