package com.aquarium.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2023-03-14
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
     * 是否报警
     */
    private Byte warnStatus;

    /**
     * 有效磷：0.2-1mg/L
     */
    private Double waterAvailablePhosphorous;

    /**
     * 有效磷报警状态
     */
    private Byte avaPhoStatus;

    /**
     * 水体盐度：0-1%
     */
    private Double waterSalt;

    /**
     * 盐度报警状态
     */
    private Byte saltStatus;

    /**
     * ph值：6.5-8.5
     */
    private Double waterPh;

    /**
     * ph值报警状态
     */
    private Byte phStatus;

    /**
     * 水温：18-35℃
     */
    private Double waterTemperature;

    /**
     * 水温报警状态
     */
    private Byte temStatus;

    /**
     * 水质记录日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dataDate;

    /**
     * 水质状况归属的场馆
     */
    private Integer venueId;

    /**
     * 归属场馆名称
     */
    private String venueName;
}
