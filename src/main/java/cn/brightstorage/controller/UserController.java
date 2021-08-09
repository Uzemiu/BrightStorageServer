package cn.brightstorage.controller;

import cn.brightstorage.annotation.AnonymousAccess;
import cn.brightstorage.model.dto.UserDTO;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.param.LoginParam;
import cn.brightstorage.model.param.RegisterParam;
import cn.brightstorage.model.param.ResetPasswordParam;
import cn.brightstorage.model.param.ResetPhoneParam;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.model.vo.LoginInfoVO;
import cn.brightstorage.service.UserService;
import cn.brightstorage.utils.AESUtil;
import cn.brightstorage.utils.RedisUtil;
import cn.brightstorage.utils.SecurityUtil;
import cn.hutool.crypto.digest.BCrypt;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation("获取当前登录用户信息")
    @GetMapping
    public BaseResponse<UserDTO> getUserInfo(){
        return BaseResponse.ok("ok",userService.toDto(SecurityUtil.getCurrentUser()));
    }

    @ApiOperation("注册")
    @AnonymousAccess
    @PostMapping("/register")
    public BaseResponse<LoginInfoVO> register(@Validated @RequestBody RegisterParam registerParam){
        return BaseResponse.ok("注册成功", userService.register(registerParam));
    }

    @ApiOperation("通过手机登录")
    @AnonymousAccess
    @PostMapping("/login/phone")
    public BaseResponse<LoginInfoVO> loginPhone(@Validated @RequestBody LoginParam loginParam){
        return BaseResponse.ok("登陆成功", userService.loginPhone(loginParam));
    }

    @ApiOperation("通过密码登录")
    @AnonymousAccess
    @PostMapping("/login/password")
    public BaseResponse<LoginInfoVO> loginPassword(@Validated @RequestBody LoginParam loginParam){
        return BaseResponse.ok("登陆成功", userService.loginPassword(loginParam));
    }

    @ApiOperation("更新用户信息")
    @PutMapping
    public BaseResponse<?> update(@Validated @RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return BaseResponse.ok();
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset/password")
    public BaseResponse<?> resetPassword(@Validated @RequestBody ResetPasswordParam param){
        userService.resetPassword(param);
        return BaseResponse.ok("更新密码成功");
    }

    @PostMapping("/reset/phone")
    public BaseResponse<?> resetPhone(@Validated @RequestBody ResetPhoneParam param){
        return BaseResponse.ok();
    }
}
