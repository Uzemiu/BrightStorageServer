package cn.brightstorage.model.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordParam {

    @ApiParam("加密过的原密码")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @ApiParam("加密过的新密码")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![.~!@#$%^&*]+$)[0-9A-Za-z.~!@#$%^&*]{8,31}$",
            message = "密码须在8~31位且须由数字、字母、字符(.~!@#$%^&*)中两者以上组成")
    private String newPassword;

    @ApiParam("短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String code;
}
