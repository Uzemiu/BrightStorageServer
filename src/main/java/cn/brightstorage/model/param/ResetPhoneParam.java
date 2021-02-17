package cn.brightstorage.model.param;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPhoneParam {

    @ApiParam("新手机号")
    @NotBlank(message = "手机号不能为空")
    private String newPhone;

    @ApiParam("短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String code;
}
