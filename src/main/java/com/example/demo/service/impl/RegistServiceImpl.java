package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.RegistDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.RegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistServiceImpl implements RegistService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
     UserServiceImpl userService;

    @Override
    public Result regist(RegistDTO registDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("email", registDTO.getEmail());
        User uer=userMapper.selectOne(wrapper);

        //比较密码
        if (uer==null){
            User usr=new User();
            usr.setUsername(registDTO.getUserName());
            usr.setEmail(registDTO.getEmail());
            usr.setPassword(registDTO.getPassword());
            System.out.println("进来了");
            if(userService.save(usr)){
                System.out.println("成功了");
                return new Result(1010,"",uer);
            }
            System.out.println("失败了");
            return new Result(400,"注册失败","");
        }else {
            System.out.println("meijin");
            return new Result(2002,"注册失败","");
        }

    }
}
