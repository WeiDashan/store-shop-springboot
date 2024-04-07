package com.weidashan.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.weidashan.pojo.AppUser;
import com.weidashan.service.IAppUserService;
import com.weidashan.service.IImgService;
import com.weidashan.service.otherService.GenerateCodeService;
import com.weidashan.service.otherService.RabbitMQService;
import com.weidashan.service.otherService.RedisService;
import com.weidashan.util.ResultJson;
import io.minio.errors.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/app-user")
public class AppUserController {

    @Resource
    IAppUserService appUserService;
    @Resource
    RedisService redisService;

    @Resource
    GenerateCodeService generateCodeService;

    @Resource
    RabbitMQService rabbitMQService;

    @Resource
    BCryptPasswordEncoder passwordEncoder;

    @Resource
    IImgService imgService;
    /**
     *
     *  首先需要判断该email是否存在于库中， 因为email是唯一的
     *  其次需要查看Redis中，是否存在该验证码email-code的验证码，避免重复生成
     *  最后生成验证码，将验证码保存在Redis中， 并向指定邮件利用MQ发送验证码
     *
     * @param email 根据email生成验证码，注册时用
     * @return 返回验证码
     */
    @PostMapping("/getCodeByEmail")
    ResultJson getCodeByEmail(String email){
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if (appUser != null){
            return ResultJson.error("邮箱已存在");
        }
        long minutesTimeOut = 1;//验证码过期时间
        String code = redisService.getString(email);
        if (code != null){
            return ResultJson.error("请勿在"+minutesTimeOut+"分钟内重复申请");
        }
        code = generateCodeService.generateCode();
        redisService.set(email, code, minutesTimeOut);
        redisService.set(code, email, minutesTimeOut);

        //rabbitMQ发送验证码到邮箱
        rabbitMQService.sendCodeToEmail(code, email);

        return ResultJson.success("","验证码生成成功");
    }
    @PostMapping("/getCodeByEmailToLogin")
    ResultJson getCodeByEmailToLogin(String email){

        // 判断邮箱是否绑定过现有用户
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if (appUser == null){
            return ResultJson.error("邮箱未绑定用户");
        }

        // 判断在1分钟内是否申请过验证码
        String checkLoginCode = "Generating:"+email;
        String code = redisService.getString(checkLoginCode);
        if (code==null){
            //rabbitMQ发送验证码到邮箱
            code = generateCodeService.generateCode();
            rabbitMQService.sendCodeToEmail(code, email);
        }else{
            return ResultJson.error("请勿在"+1+"分钟内重复申请");
        }


        return ResultJson.success("","验证码生成中，请稍后");
    }

    /**
     *  检验邮箱和验证码是否匹配
     * @param code 验证码
     * @param email 邮箱
     */
    @PostMapping("/checkCodeByEmail")
    ResultJson checkCodeByEmail(String code, String email){
        String redisCode = redisService.getString(email);
        if (redisCode == null){
            return ResultJson.error("验证码已失效");
        }
        if (!redisCode.equals(code)){
            return ResultJson.error("验证码错误");
        }
        return ResultJson.success("","邮箱验证成功");
    }

    /**
     * 注册添加用户
     * @param appUser 注册用户信息
     * @param iconFile 用户头像
     */
    @PostMapping("/registerAppUser")
    ResultJson registerAppUser(AppUser appUser, MultipartFile iconFile) throws ServerException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        appUser.setPassword(passwordEncoder.encode(appUser.getRawPassword()));
        appUser.setIcon(imgService.upload(iconFile));
        return ResultJson.success(appUserService.save(appUser),"注册成功");
    }

    /**
     * 登录
     * @param email 邮箱
     * @param rawPassword 密码
     */
    @PostMapping("/loginByPassword")
    ResultJson loginByPassword(String email, String rawPassword){
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if (appUser == null){
            return ResultJson.error("用户不存在");
        }
        if (!passwordEncoder.matches(rawPassword, appUser.getPassword())){
            return ResultJson.error("密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        String token = JWT.create()
                .sign(Algorithm.HMAC256("weidashan"));
        map.put("appUser", appUser);
        map.put("token", token);
        return ResultJson.success(map, "登录成功");
    }

    /**
     * 登录
     * @param email 邮箱
     * @param code 验证码
     */
    @PostMapping("/loginByCode")
    ResultJson loginByCode(String email, String code){
        AppUser appUser = appUserService.getAppUserByEmail(email);
        if (appUser == null){
            return ResultJson.error("用户不存在");
        }
        String redisCode = redisService.getString(email);
        if (redisCode == null){
            return ResultJson.error("验证码已失效");
        }
        if (!redisCode.equals(code)){
            return ResultJson.error("验证码错误");
        }
        Map<String, Object> map = new HashMap<>();
        String token = JWT.create()
                .sign(Algorithm.HMAC256("weidashan"));
        map.put("appUser", appUser);
        map.put("token", token);
        return ResultJson.success(map, "登录成功");
    }
}
