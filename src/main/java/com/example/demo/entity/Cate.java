package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Cate implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "classId", type = IdType.ID_WORKER_STR)
    private Integer classId;

    private String name;


}
