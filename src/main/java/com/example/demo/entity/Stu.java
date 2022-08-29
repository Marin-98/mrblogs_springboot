package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mr
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Stu implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("Sno")
    private String Sno;

    @TableField("Sname")
    private String Sname;

    @TableField("Stele")
    private String Stele;

    @TableField("Imageurl")
    private String Imageurl;


}
