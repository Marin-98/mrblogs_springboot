package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.RegistDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.Stu;
import com.example.demo.entity.User;
import com.example.demo.mapper.StuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.StuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mr
 * @since 2022-04-25
 */
@Service
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {
    @Autowired
    private StuMapper userMapper;

    @Autowired
    StuServiceImpl userService;

    @Override
    public Result regist(Stu stu) {
        QueryWrapper<Stu> wrapper = new QueryWrapper();
        wrapper.eq("Sno", stu.getSno());
        Stu uer=userMapper.selectOne(wrapper);
        //比较密码
        if (uer==null){
            Stu usr=new Stu();
            usr.setSname(stu.getSname());
            usr.setSno(stu.getSno());
            usr.setStele(stu.getStele());
            usr.setImageurl(stu.getImageurl());
            if(userService.save(usr)){
                return new Result(200,"",uer);
            }
            return new Result(400,"注册失败","");
        }else {
            return new Result(2002,"注册失败","");
        }
    }
}
