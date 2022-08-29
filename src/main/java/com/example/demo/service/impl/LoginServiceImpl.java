package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.entity.LoginDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.LoginService;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public Result login(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getEmail())){
            return new Result(400,"账号不能为空","");
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            return new Result(400,"密码不能为空","");
        }
        //通过登录名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("email", loginDTO.getEmail());
        User uer=userMapper.selectOne(wrapper);
        //比较密码
        if (uer!=null&&uer.getPassword().equals(loginDTO.getPassword())){

            String token=tokenService.getToken(uer);
            uer.setToken(token);
            if(userMapper.updateById(uer)==1){
                return new Result(1010,"",uer,token);
            }
            return new Result(401,"密码错误","");
        }
        return new Result(400,"登录失败","");
    }

    @Override
    public Result loginOut(String token) {
        //通过token查询用户
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("token", token);
        User uer=userMapper.selectOne(wrapper);
        if (uer!=null){
            uer.setToken("0");
            if(userMapper.updateById(uer)==1){
                return new Result(1010,"");
            }
        }
        return new Result(1010,"");
    }
}
