package com.aquarium.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @作者: tt—Tang
 * @描述: 场所管理员中间表实体
 **/

@Getter
@Setter
@TableName("sys_staff_venue")
public class SysStaffVenue {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场馆id
     */
    private Integer venueId;

    /**
     * 人员id
     */
    private Integer staffId;
}
