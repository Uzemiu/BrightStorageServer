package cn.brightstorage.service.impl;

import cn.brightstorage.exception.BadRequestException;
import cn.brightstorage.model.dto.UserDTO;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.param.LoginParam;
import cn.brightstorage.model.param.RegisterParam;
import cn.brightstorage.model.param.ResetPasswordParam;
import cn.brightstorage.model.param.ResetPhoneParam;
import cn.brightstorage.model.vo.LoginInfoVO;
import cn.brightstorage.repository.UserRepository;
import cn.brightstorage.service.UserService;
import cn.brightstorage.service.base.AbstractCrudService;
import cn.brightstorage.utils.AESUtil;
import cn.brightstorage.service.mapper.UserMapper;
import cn.brightstorage.utils.SMSUtil;
import cn.brightstorage.utils.SecurityUtil;
import cn.brightstorage.utils.TokenUtil;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Service("userService")
public class UserServiceImpl extends AbstractCrudService<User, String> implements UserService {

    private final UserRepository userRepository;
    private final AESUtil aesUtil;
    private final TokenUtil tokenUtil;
    private final SMSUtil smsUtil;
    private final UserMapper userMapper;
    private final Pattern passwordPattern;

    protected UserServiceImpl(UserRepository repository,
                              AESUtil aesUtil,
                              TokenUtil tokenUtil,
                              SMSUtil smsUtil,
                              UserMapper userMapper) {
        super(repository);
        this.userRepository = repository;
        this.aesUtil = aesUtil;
        this.tokenUtil = tokenUtil;
        this.smsUtil = smsUtil;
        this.userMapper = userMapper;

        this.passwordPattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![.~!@#$%^&*]+$)[0-9A-Za-z.~!@#$%^&*]{8,31}$");
    }

    @Override
    public LoginInfoVO loginPhone(LoginParam loginParam) {
        smsUtil.verifyCode(loginParam.getPhone(), loginParam.getCode());
        User user = userRepository.getUserByPhone(loginParam.getPhone()).orElse(null);
        return user == null ? null
                : new LoginInfoVO(tokenUtil.generateAndSetToken(user), userMapper.toDto(user));
    }

    @Override
    public LoginInfoVO loginPassword(LoginParam loginParam) {
        User user = userRepository.getUserByPhone(loginParam.getPhone())
                .orElseThrow(() -> new BadRequestException("用户尚未注册"));
//        String rawPassword = aesUtil.decrypt(loginParam.getPassword());
        String rawPassword = loginParam.getPassword();

        Assert.isTrue(BCrypt.checkpw(rawPassword, user.getPassword()), "手机号或密码错误");

        return new LoginInfoVO(tokenUtil.generateAndSetToken(user), userMapper.toDto(user));
    }

    @Override
    public void resetPassword(ResetPasswordParam param) {
        User user = SecurityUtil.getCurrentUser();

//        String rawOldPassword = aesUtil.decrypt(param.getOldPassword());
        String rawOldPassword = param.getOldPassword();
        Assert.isTrue(BCrypt.checkpw(rawOldPassword, user.getPassword()), "原密码不正确");

//        String rawNewPassword = aesUtil.decrypt(param.getNewPassword());
        String rawNewPassword = param.getNewPassword();
        Assert.isTrue(passwordPattern.matcher(rawNewPassword).matches(),
                "密码须在8~31位且须由数字、字母、字符(.~!@#$%^&*)中两者以上组成");
        user.setPassword(BCrypt.hashpw(rawNewPassword));
        userRepository.save(user);
    }

    @Override
    public void resetPhone(ResetPhoneParam param) {
        smsUtil.verifyCode(param.getNewPhone(), param.getCode());

        User user = SecurityUtil.getCurrentUser();
        user.setPhone(param.getNewPhone());
        userRepository.save(user);
    }

    @Override
    public LoginInfoVO register(RegisterParam registerParam) {
        smsUtil.verifyCode(registerParam.getPhone(), registerParam.getCode());

//        String rawPassword = aesUtil.decrypt(registerParam.getPassword());
        String rawPassword = registerParam.getPassword();
        Assert.isTrue(passwordPattern.matcher(rawPassword).matches(),
                "密码须在8~31位且须由数字、字母、字符(.~!@#$%^&*)中两者以上组成");

        User user = new User();
        user.setPhone(registerParam.getPhone());
        user.setPassword(BCrypt.hashpw(rawPassword));

        userRepository.save(user);
        return new LoginInfoVO(tokenUtil.generateAndSetToken(user), userMapper.toDto(user));
    }

    @Override
    public UserDTO toDto(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public User update(UserDTO userDTO) {
        User currentUser = SecurityUtil.getCurrentUser();
        currentUser.setNickname(userDTO.getNickname());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setAvatar(userDTO.getEmail());
        userRepository.save(currentUser);
        return currentUser;
    }
}
