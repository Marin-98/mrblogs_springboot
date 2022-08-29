package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr
 * @since 2021-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "commentid", type = IdType.AUTO)
    private Integer commentid;

    private String content;

    private Integer userid;

    private Integer articleid;

    private Integer leavepid;

    private Integer pid;

    private Integer leaveid;

    private String time;

    private String state;

    private String username;

    private String avatar;

    private String label;

    private String replyname;

    @TableField(exist = false)
    private Object ChildsSon;

}
