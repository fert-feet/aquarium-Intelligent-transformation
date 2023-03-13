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
 * @since 2023-03-13
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
     * 有效磷：0.2-1mg/L
     */
    private Double waterAvailablePhosphorous;

    /**
     * 水体盐度：0-1%
     */
    private Double waterSalt;

    /**
     * ph值：6.5-8.5
     */
    private Double waterPh;

    /**
     * 水温：18-35℃
     */
    private Double waterTemperature;

    /**
     * 水质记录日期
     */
    private LocalDateTime dataDate;

    /**
     * 水质状况归属的场馆ID
     */
    private Integer venueId;

    /**
     * 水质状况归属的场馆名称
     */
    private String venueName;


    /**
     * 是否报警
     */
    private Byte warnStatus;
}
