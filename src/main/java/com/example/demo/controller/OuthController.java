package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Result;
import com.example.demo.entity.Likenum;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.likeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class OuthController {

    @Autowired
    likeMapper likeMapper;

    @Autowired
    UserMapper userMapper;

//{"code":1001,"data":2147483647,"msg":"Query Success"}
    //查询网址点赞总数
    @GetMapping(value = "outh/showLikeData")
    @CrossOrigin       //后端跨域
    public Result ShowLikeData(){
        QueryWrapper<Likenum> wp=new QueryWrapper<Likenum>();
        wp.eq("id",1);
        Likenum lk=likeMapper.selectOne(wp);
        return new Result(1001,"Query Success",lk.getLikenum());
    }


//    {"code":1001,"msg":"Like Success"}
//    //点赞功能修改
    @GetMapping(value = "outh/GetLike")
    @CrossOrigin       //后端跨域
    public Result GetLike(@RequestParam long like_num){
        QueryWrapper<Likenum> wp=new QueryWrapper<Likenum>();
        wp.eq("id",1);
        Likenum lk=likeMapper.selectOne(wp);
        long num= lk.getLikenum()+(like_num==1?1:like_num%10+1);
        lk.setLikenum(num);
        if(likeMapper.updateById(lk)==1){
            return new Result(1001,"Like Success");
        }
        return new Result(400,"Like error");
    }


    //查询友情链接数据
    @GetMapping(value = "outh/FriendUrlData")
    @CrossOrigin       //后端跨域
    public Result FriendUrlData(){
        QueryWrapper<User> wp=new QueryWrapper<User>();
        wp.isNotNull("frieUrl");
        List<User> usr=userMapper.selectList(wp);

        return new Result(1001,"Query Success",usr);
    }
//
//    //查询关于我
//    @GetMapping(value = "outh/AboutMeData")
//    @CrossOrigin       //后端跨域
//    public Result AboutMeData(){
//        return new Result(1010,"","");
//    }
//

    //{"code":1001,"data":[{"id":2,"name":"\u8d28\u5fc3","money":"10000","account":"15035574759","pay_source":2,"image":"http:\/\/www.mangoya.cn\/upload\/admire\/20171207\/19121132a20dea29a40781864711375e.jpg","pay_time":"2017-12-07 15:34:52","time":"2017-12-07 15:34:54","state":1}],"admire_code":{"wechat_image":"http:\/\/www.mangoya.cn\/upload\/introme\/20180207\/fb43b468e2f39ec3498c8e2798ecd4d8.jpg","alipay_image":"http:\/\/www.mangoya.cn\/upload\/introme\/20180207\/c0ca34d27e8069b88ffc6670d8b0aef4.jpg"},"msg":"Query Success"}
//    //查询赞赏数据
//    @GetMapping(value = "outh/AdmireData")
//    @CrossOrigin       //后端跨域
//        return new Result(1010,"","");
//    public Result AdmireData(){
//    }
//


//    //获取主题信息
//    @GetMapping(value = "outh/ThemeMy")
//    @CrossOrigin       //后端跨域
//    public Result ThemeMy(){
//        return new Result(1010,"","");
//    }


}
