package com.aquarium.mapper;

import com.aquarium.pojo.SysStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
public interface SysStaffMapper extends BaseMapper<SysStaff> {

    /**
     * 查找管理的实验室
     *
     * @param staffId
     * @return
     */
    Set<Integer> findManagedVenue(int staffId);

    /**
     * 查找管理的实验室关系id
     *
     * @param staffId
     * @return
     */
    Set<Integer> findManagedVenueInterIds(int staffId);
}
