package cn.brightstorage.model.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterParam {

    @ApiParam(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiParam("加密过的密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![.~!@#$%^&*]+$)[0-9A-Za-z.~!@#$%^&*]{8,31}$",
            message = "密码须在8~31位且须由数字、字母、字符(.~!@#$%^&*)中两者以上组成")
    private String password;

    @ApiParam("短信验证码")
    private String code;
}
