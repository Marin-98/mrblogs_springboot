package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr
 * @since 2021-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String title;

    private Integer browsecount;

    private String image;

    private String description;

    private String content;

    private String createTime;

    private Integer commentcount;

    private Integer likecount;

    private Integer collectcount;

    private Integer isrecommend;

    private Integer ishot;

    private Integer classid;

    private String catename;

    @TableField(exist = false)
    private String userlikestart;

    @TableField(exist = false)
    private String usercollectstart;

    private String wechatimage;

    private String alipayimage;

    @TableField(exist = false)
    private String comment;

    @TableField(exist = false)
    private String Avatar;

    @TableField(exist = false)
    private String username;


}
