package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.IStatusMessage;
import com.example.demo.ServiceException;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.FileUpAndDownService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mr
 * @since 2021-09-15
 */
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;


//    查询用户信息
    @GetMapping(value = "Userinfo/getUserInfo")
    @CrossOrigin       //后端跨域
    public Result GetUserInfo(@RequestParam String userid){
        User user=userMapper.selectById(userid);
        return new Result(1001,"",user);
    }




    //修改用户信息
    @GetMapping(value = "Userinfo/UserInfoSave")
    @CrossOrigin       //后端跨域
    public Result UserInfoSave(User usr){
        QueryWrapper<User> wp=new QueryWrapper<User>();
        User uer=userMapper.selectOne(wp.eq("email",usr.getEmail()));
        uer.setUsername(usr.getUsername());
        uer.setAvatar(usr.getAvatar());
        uer.setDescription(usr.getDescription());
        uer.setImage(usr.getImage());
        uer.setHeadStart(usr.getHeadStart());
        uer.setLabel(usr.getLabel());
        uer.setLogoStart(usr.getLogoStart());
        uer.setSex(usr.getSex());
        uer.setName(usr.getName());
        uer.setState(usr.getState());
        uer.setUrl(usr.getUrl());
        if(userMapper.updateById(uer)==1){
            QueryWrapper<Comment> cp=new QueryWrapper<Comment>();
            cp.eq("userid",uer.getUserid());
            List<Comment> commentList= commentMapper.selectList(cp);
            for (Comment c:commentList) {
                c.setAvatar(uer.getAvatar());
                commentMapper.updateById(c);
            }
            return new Result(1001,"修改成功");

        }

        return new Result(400,"修改失败");
    }

    @Autowired
    private FileUpAndDownService fileUpAndDownService;

    @RequestMapping(value = "/Userinfo/UploadImg", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public Result setFileUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        Result result = new Result();
        try {
            Map<String, Object> resultMap = upload(file);
            if (!IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(resultMap.get("result"))) {
                result.setCode(400);
                result.setMsg((String) resultMap.get("msg"));
                return result;
            }
             return new Result(1001,"上传成功",resultMap.get("path").toString());
        } catch (ServiceException e) {
            e.printStackTrace();
            result.setCode(401);
            result.setMsg("图片上传异常");
        }
        return result;
    }

    private Map<String, Object> upload(MultipartFile file) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if (!file.isEmpty()) {
                Map<String, Object> picMap = fileUpAndDownService.uploadPicture(file);
                if (IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(picMap.get("result"))) {
                    return picMap;
                } else {
                    returnMap.put("result", 402);
                    returnMap.put("msg", picMap.get("result"));
                }
            } else {
                returnMap.put("result", 402);
                returnMap.put("msg", "上传信息为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

}

