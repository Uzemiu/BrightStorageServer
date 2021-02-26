package cn.brightstorage.model.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterParam {

    @ApiParam(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiParam("加密过的密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiParam("短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String code;
}
