package com.aquarium.vo;

import lombok.Data;

/**
 * @作者: tt—Tang
 * @描述: 员工列表VO
 **/

@Data
public class StaffListVo {
    private Integer staffId;
    private String name;
    private String username;
    private String password;
    private String venueName;
    private String idNum;
}
