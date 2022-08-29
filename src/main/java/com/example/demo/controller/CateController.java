package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Article;
import com.example.demo.entity.Cate;
import com.example.demo.entity.Result;
import com.example.demo.mapper.CateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mr
 * @since 2022-03-25
 */
@RestController
@RequestMapping("/cate")
public class CateController {
    @Autowired
    private CateMapper cateMapper;

    @GetMapping(value = "catelist")
    @CrossOrigin
    public Result GetCateList(){
        List<Cate> list=cateMapper.selectList(null);
        return new Result(1001, "Success", list);
    }
}

