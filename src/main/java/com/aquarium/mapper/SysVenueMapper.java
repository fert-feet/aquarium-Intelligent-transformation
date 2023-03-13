package com.aquarium.mapper;

import com.aquarium.pojo.SysDevice;
import com.aquarium.pojo.SysVenue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
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
     * 查找场馆的管理员
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

    /**
     * 查找场馆绑定的设备
     *
     * @param venueId
     * @return
     */
    Set<SysDevice> findBindDevicesByVenueId(int venueId);

    /**
     * 查询场馆列表
     *
     * @return
     */
    List<SysVenue> findVenueList();
}
