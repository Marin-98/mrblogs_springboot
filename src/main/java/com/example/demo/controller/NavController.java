package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Article;
import com.example.demo.entity.Result;
import com.example.demo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController()
public class NavController {

    @Autowired
    ArticleMapper articleMapper;

//    //实验室 列表项目
//    @GetMapping(value = "nav/navMenList")
//    @CrossOrigin       //后端跨域
//    public Result GetnavMenList(){
//        return new Result(1010,"","");
//    }
//


    //查询文章列表
    @GetMapping(value = "nav/ActiveClassAllData")
    @CrossOrigin       //后端跨域
    public Result GetActiveClassAllData(@RequestParam int art_id, @RequestParam int cate_id, @RequestParam String article_name) {
        QueryWrapper<Article> wp = new QueryWrapper<Article>();
        wp.orderByDesc("Id");
            if (cate_id > 0) {
                wp.eq("classid", cate_id);
            }
        List<Article> art = articleMapper.selectList(wp);
        return new Result(1001, "Query Success", art);
    }
}
