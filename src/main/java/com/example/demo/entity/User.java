package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "userid", type = IdType.AUTO)
    private int userid;

    @TableField("userName")
    private String username;

    @TableField("userImg")
    private String avatar;

    @TableField("password")
    private String password;

    private String sex;

    @TableField("email")
    private String email;

    @TableField("friendStart")
    private String state;

    @TableField("frieName")
    private String Name;

    @TableField("frieUrl")
    private String url;

    @TableField("frieDescription")
    private String Description;

    @TableField("friendImg")
    private String image;

    @TableField("label")
    private String label;

    @TableField("headStart")
    private String headStart;

    @TableField("logoStart")
    private String logoStart;

    @TableField("token")
    private String token;



}
