package com.aquarium.service;

import com.aquarium.dto.UpdateManagedVenueDTO;
import com.aquarium.pojo.SysStaff;
import com.aquarium.response.ResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
public interface ISysStaffService extends IService<SysStaff> {

    /**
     * 分页查询人员
     *
     * @param page
     * @param limit
     * @param name
     * @param hasVenue
     * @return
     */
    ResponseVo listStaff(long page, long limit, String name, byte hasVenue);


    /**
     * 查找人员管理的场馆
     *
     * @param staffId
     * @return
     */
    ResponseVo findManagedVenue(int staffId);

    /**
     * 更新管理的场馆
     *
     * @param newVenueDTO
     * @return
     */
    ResponseVo updateManagedVenue(UpdateManagedVenueDTO newVenueDTO);

    /**
     * 人员删除
     *
     * @param staffId
     * @return
     */
    ResponseVo delete(int staffId);

}
