package com.aquarium.dto;

import lombok.Data;

import java.util.Set;

/**
 * @作者: tt—Tang
 * @描述: 更新人员DTO
 **/

@Data
public class UpdateStaffDto {
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
     * 是否有管理的场馆
     */
    private Byte hasVenue;

    /**
     * 人员身份证号码
     */
    private String idNum;

    private Set<Integer> venueIdList;
}
