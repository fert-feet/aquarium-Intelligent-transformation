package com.aquarium.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@Getter
@Setter
@TableName("sys_water_quality")
public class SysWaterQuality implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 水质状况id
     */
    @TableId(value = "water_quality_id", type = IdType.AUTO)
    private Integer waterQualityId;

    /**
     * 水质数值
     */
    private Integer waterQualityNum;

    /**
     * 水质记录日期
     */
    private LocalDateTime dataDate;

    /**
     * 水质状况归属的实验室
     */
    private Integer venueId;
}
