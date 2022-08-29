package com.example.demo.controller;

import com.example.demo.entity.LoginDTO;
import com.example.demo.entity.RegistDTO;
import com.example.demo.entity.Result;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController()
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    RegistService registService;

    @Value("${mail.fromMail.sender}")
    private String sender;// 发送者

    @Autowired
    private JavaMailSender javaMailSender;

    private Map<String, Object> resultMap = new HashMap<>();

   //用户登录
    @GetMapping(value = "/login/UserLogin")
    @CrossOrigin       //后端跨域
    public Result login(@RequestParam String email,@RequestParam  String password){
        LoginDTO dto=new LoginDTO();
        dto.setEmail(email);
        dto.setPassword(password);
        return loginService.login(dto);
    }

  //用户注册
    @GetMapping(value = "/login/getRegister")
    @CrossOrigin       //后端跨域
    public Result Regist(@RequestParam String username,@RequestParam String email,@RequestParam  String password){


        if(!sendEmail(email).equals("success")){
            return new Result(2009,"failed");
        }else {
            RegistDTO dto=new RegistDTO();
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setUserName(username);
            return new Result(1010,"success",registService.regist(dto));
        }
    }
    public String sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = VerifyCode(6);    //随机数生成6位验证码
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject("博客系统");// 标题
        message.setText("【博客系统】你的验证码为："+code+"，有效时间为5分钟(若不是本人操作，可忽略该条邮件)");// 内容
        try {
            javaMailSender.send(message);
            saveCode(code);
            return "success";
        }catch (MailSendException e){
            return "false";
        } catch (Exception e) {
            return "failure";
        }
    }


    private String VerifyCode(int n){
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < n;i ++){
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
//        System.out.println(sb);
        return sb.toString();
    }

    //保存验证码和时间
    private void saveCode(String code){
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期

        String hash =  MD5Utils.code(code);//生成MD5值
        resultMap.put("hash", hash);
        resultMap.put("tamp", currentTime);
    }

    //用户登出
    @GetMapping(value = "/login/LoginOut")
    @CrossOrigin
    public Result LoginOut(@RequestParam String token){
        return loginService.loginOut(token);
    }
}
