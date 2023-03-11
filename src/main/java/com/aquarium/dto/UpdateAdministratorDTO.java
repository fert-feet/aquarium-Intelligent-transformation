package com.aquarium.dto;

import lombok.Data;

import java.util.Set;

/**
 * @作者: tt—Tang
 * @描述: 更新场馆管理员DTO
 **/

@Data
public class UpdateAdministratorDTO {
    Set<Integer> staffIdList;

    Integer venueId;
}
