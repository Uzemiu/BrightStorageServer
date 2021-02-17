package cn.brightstorage.service;

import cn.brightstorage.model.dto.UserDTO;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.param.LoginParam;
import cn.brightstorage.model.param.RegisterParam;
import cn.brightstorage.model.param.ResetPasswordParam;
import cn.brightstorage.model.param.ResetPhoneParam;
import cn.brightstorage.model.vo.LoginInfoVO;
import cn.brightstorage.service.base.CrudService;

public interface UserService extends CrudService<User, String> {

    /**
     * 通过短信验证码登录
     * @param loginParam /
     * @return 如果登陆成功且已注册，返回AccessToken以及用户信息；
     * 如果登录成功但未注册则返回null
     */
    LoginInfoVO loginPhone(LoginParam loginParam);

    /**
     * 通过密码登录
     * @param loginParam /
     * @return 用户登录信息
     */
    LoginInfoVO loginPassword(LoginParam loginParam);

    /**
     * 注册
     * @param registerParam /
     * @return 用户登录信息
     */
    LoginInfoVO register(RegisterParam registerParam);

    void resetPassword(ResetPasswordParam param);

    void resetPhone(ResetPhoneParam param);

    User update(UserDTO userDTO);

    UserDTO toDto(User user);
}
