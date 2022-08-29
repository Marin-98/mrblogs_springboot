package com.example.demo.service;

import com.example.demo.entity.RegistDTO;
import com.example.demo.entity.Result;
import com.example.demo.entity.Stu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mr
 * @since 2022-04-25
 */
public interface StuService extends IService<Stu> {
    public Result regist(Stu stu);
}
