package com.example.demo.service;

import com.example.demo.entity.LoginDTO;
import com.example.demo.entity.Result;

public interface LoginService {
    public Result login(LoginDTO loginDTO);
    public Result loginOut(String token);
}
