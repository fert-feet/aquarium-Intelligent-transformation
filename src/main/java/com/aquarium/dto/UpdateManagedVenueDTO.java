package com.aquarium.dto;

import lombok.Data;

import java.util.Set;

/**
 * @作者: tt—Tang
 * @描述: 更新人员管理的场馆DTO
 **/

@Data
public class UpdateManagedVenueDTO {
    Set<Integer> venueIdList;

    Integer staffId;
}
