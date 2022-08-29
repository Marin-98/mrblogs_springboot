package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.StuMapper;
import com.example.demo.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mr
 * @since 2022-04-25
 */
@RestController
public class StuController {

    @Autowired
    StuService stuService;

    @Autowired
    StuMapper   stuMapper;

    @GetMapping(value = "/stu/getStuInfo")
    @CrossOrigin       //后端跨域
    public Result GetStuInfo(){
        List<Stu> stu=stuMapper.selectList(null);
        return new Result(200,"",stu);
    }

    @GetMapping(value = "/stu/getStuRegister")
    @CrossOrigin       //后端跨域
    public Result Regist(@RequestParam String sno,@RequestParam String sname,@RequestParam String stele,@RequestParam  String url){
            Stu dto=new Stu();
            dto.setSno(sno);
            dto.setSname(sname);
            dto.setStele(stele);
            dto.setImageurl(url);
            return new Result(200,"success",stuService.regist(dto));

    }

    @GetMapping(value = "/stu/delStu")
    @CrossOrigin       //后端跨域
    public Result delStu(@RequestParam String sno){
        QueryWrapper<Stu> wp=new QueryWrapper<Stu>();
        wp.eq("sno",sno);
        stuMapper.delete(wp);
        return new Result(200,"success");

    }
}

