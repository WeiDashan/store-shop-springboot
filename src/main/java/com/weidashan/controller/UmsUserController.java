package com.weidashan.controller;

import com.alibaba.fastjson.JSONObject;
import com.weidashan.pojo.Email;
import com.weidashan.pojo.UmsUser;
import com.weidashan.service.IImgService;
import com.weidashan.service.IUmsUserService;
import com.weidashan.service.otherService.RedisService;
import com.weidashan.util.ResultJson;
import io.minio.errors.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/ums-user")
public class UmsUserController {
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    IUmsUserService umsUserService;

    @Resource
    IImgService imgService;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    RedisService redisService;

    @GetMapping("/list")
    ResultJson list(Integer pageNo, Integer pageSize, String name){
        return ResultJson.success(umsUserService.page(pageNo,pageSize,name));
    }
    @PostMapping("/add")
    ResultJson add(UmsUser umsUser, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        umsUser.setPassword(passwordEncoder.encode(umsUser.getRawPassword()));
        umsUser.setIcon(imgService.upload(file));
        return ResultJson.success(umsUserService.save(umsUser),"添加用户成功");
    }
    @GetMapping("/getone")
    ResultJson getOne(Long id) {
        return ResultJson.success(umsUserService.getById(id));
    }
    @PostMapping("/update")
    ResultJson update(UmsUser umsUser, MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        if(file != null && file.getSize() > 0) {
            umsUser.setIcon(imgService.upload(file));
        }
        return ResultJson.success(umsUserService.updateById(umsUser),"修改用户成功");
    }

    @PostMapping("/del")
    ResultJson del(UmsUser umsUser) {
        String message = umsUser.getActive() == 0 ? "删除用户成功" : "恢复用户成功";
        return ResultJson.success(umsUserService.updateById(umsUser),message);
    }

    @PostMapping("/login")
    ResultJson login(String username, String password) throws Exception {
        return ResultJson.success(umsUserService.login(username, password),"登录成功");
    }

    @PostMapping("/changePassword")
    ResultJson changePassword(String loginName, String code, String password){
        // 判断验证码是否正确
        String value = redisService.getString(loginName);
        if (value==null || value.length()==0){
            return ResultJson.success(loginName,"验证码已失效");
        }
        if (!value.equals(code)){
            return ResultJson.success(loginName,"验证码错误");
        }
        // 修改密码
        int result = umsUserService.updatePasswordByLoginName(loginName, passwordEncoder.encode(password));
        if (result == 1) {
            return ResultJson.success(loginName,"修改成功");
        }else{
            return ResultJson.success(loginName,"修改失败");
        }
    }

    @PostMapping("/getcode")
    ResultJson getcode(String loginName){
        //如果用户名存在，并且绑定了邮箱，则向其邮箱发送验证码，并返回成功
        UmsUser umsUser = umsUserService.getUserByLoginName(loginName);
        if (umsUser==null){
            return ResultJson.success(loginName,"用户不存在");
        }
        String emailTo = umsUser.getEmail();
        if (emailTo==null || emailTo.length()==0){
            return ResultJson.success(loginName,"用户未绑定邮箱");
        }


        //判断是否生成过验证码
        String value = redisService.getString(loginName);
        if (value!=null && value.length()!=0){
            return ResultJson.success(loginName,"请勿重复申请验证码");
        }
        String code = generateCode();
        long timeout = 1; // 设置验证码过期时间：1分钟
        redisService.set(loginName, code, timeout);

        //设置邮件主体
        Email email = new Email();
        email.setSubject("获取验证码");
        email.setMessage("验证码："+code);
        email.setTo(emailTo);

        //设置邮件发送
        rabbitTemplate.convertAndSend("email", JSONObject.toJSONString(email));

        return ResultJson.success(null,"发送验证码成功");
    }

    public String generateCode(){

        return generateCode(6);
    }
    public String generateCode(int number){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<number;i++){
            stringBuilder.append(new Random().nextInt(10));
        }
        return stringBuilder.toString();
    }
}
