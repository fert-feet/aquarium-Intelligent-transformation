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
@TableName("sys_administrator")
public class SysAdministrator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员登录id
     */
    private Integer adminId;

    /**
     * 管理员账号
     */
    private String adminName;

    /**
     * 管理员密码
     */
    private String password;
}
