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
@TableName("sys_staff")
public class SysStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工作人员id
     */
    private Integer staffId;

    /**
     * 人员名字
     */
    private String name;

    /**
     * 人员登录账号
     */
    private String username;

    /**
     * 人员登录密码
     */
    private String password;

    /**
     * 管理的场馆id
     */
    private Integer venueId;
}
