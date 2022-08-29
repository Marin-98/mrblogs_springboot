package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ZqlSeatOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Date subscribeTime;

    private Integer timeCode;

    private Long readingRoomId;

    private Long seatId;

    private Long studentId;

    private Date signInTime;

    private Integer signResult;


}
