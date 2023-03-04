package com.aquarium.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("sys_device")
public class SysDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备主键
     */
    private Integer dviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所属场馆id
     */
    private Integer venueId;
}
