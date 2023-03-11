package com.aquarium.mapper;

import com.aquarium.pojo.SysVenue;
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
public interface SysVenueMapper extends BaseMapper<SysVenue> {

    /**
     * 查找实验室的管理员
     *
     * @param venueId
     * @return
     */
    Set<Integer> findAdministrator(int venueId);

    /**
     * 查找管理员关系id
     *
     * @param venueId
     * @return
     */
    Set<Integer> findAdministratorInterIds(int venueId);
}
