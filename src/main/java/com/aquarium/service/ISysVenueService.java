package com.aquarium.service;

import com.aquarium.dto.UpdateAdministratorDTO;
import com.aquarium.pojo.SysVenue;
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
public interface ISysVenueService extends IService<SysVenue> {

    /**
     * 分页查询场馆
     *
     * @param page
     * @param limit
     * @param name
     * @param hasAdmin
     * @return
     */
    ResponseVo listVenue(long page, long limit, String name, byte hasAdmin);

    /**
     * 查找实验室管理员
     *
     * @param venueId
     * @return
     */
    ResponseVo findAdministrator(int venueId);

    /**
     * 更新场馆管理员
     *
     * @param newAdminDTO
     * @return
     */
    ResponseVo updateAdministrator(UpdateAdministratorDTO newAdminDTO);

    /**
     * 删除
     *
     * @param venueId
     * @return
     */
    ResponseVo delete(int venueId);
}
