package com.aquarium.service.impl;

import com.aquarium.dto.UpdateAdministratorDTO;
import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysStaffVenueMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysStaffVenue;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysVenueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@Service
@Slf4j
public class SysVenueServiceImpl extends ServiceImpl<SysVenueMapper, SysVenue> implements ISysVenueService {

    @Resource
    private SysVenueMapper venueMapper;

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysStaffVenueMapper staffVenueMapper;

    @Override
    public ResponseVo listVenue(long page, long limit, String name, byte hasAdmin) {
        // 分页
        Page<SysVenue> venuePage = new Page<>();
        // 当前页面
        venuePage.setCurrent(page);
        //页面大小
        venuePage.setSize(limit);
        LambdaQueryWrapper<SysVenue> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据场馆名称查询
            wrapper.like(SysVenue::getName, name);
        }
        if (hasAdmin == 0 || hasAdmin == 1) {
            // 根据场所有无管理员过滤
            wrapper.eq(SysVenue::getHasAdmin, hasAdmin);
        }
        // 顺序排列
        wrapper.orderByAsc(SysVenue::getVenueId);
        Page<SysVenue> selectPage = venueMapper.selectPage(venuePage, wrapper);
        return ResponseVo.success()
                .data("items", selectPage.getRecords())
                .data("totalCount", selectPage.getTotal())
                .data("pageNo", page);
    }

    @Override
    public ResponseVo findAdministrator(int venueId) {
        Set<Integer> administratorIds = venueMapper.findAdministrator(venueId);
        return ResponseVo.success().data("items", administratorIds).data("total", administratorIds.size());
    }

    @Override
    public ResponseVo updateAdministrator(UpdateAdministratorDTO newAdminDTO) {
        // 查出旧关系id
        Set<Integer> administratorInterIds = venueMapper.findAdministratorInterIds(newAdminDTO.getVenueId());
        // 查找出准备删除部分关系的管理员id
        Set<Integer> administratorIds = venueMapper.findAdministrator(newAdminDTO.getVenueId());
        // 删除关系
        if (administratorInterIds.size() > 0) {
            if (staffVenueMapper.deleteBatchIds(administratorInterIds) < 0) {
                return ResponseVo.exp();
            }
        }
        SysVenue venue = new SysVenue();
        // 设置场馆管理员状态
        venue.setHasAdmin((byte) 0);
        venue.setVenueId(newAdminDTO.getVenueId());
        if (newAdminDTO.getStaffIdList().size() > 0) {
            // 增加新的人员场馆关系
            newAdminDTO.getStaffIdList().forEach(staffId -> {
                SysStaffVenue sysStaffVenue = new SysStaffVenue();
                sysStaffVenue.setVenueId(newAdminDTO.getVenueId());
                sysStaffVenue.setStaffId(staffId);
                staffVenueMapper.insert(sysStaffVenue);
                log.info("insertVenue---{}", sysStaffVenue.getId());
            });
            // 设置场馆管理员状态
            venue.setHasAdmin((byte) 1);
        }
        // 设置场馆管理员状态
        if (venueMapper.updateById(venue) <= 0) {
            return ResponseVo.exp();
        }
        // 设置变动过的管理员管理场馆状态
        administratorIds.addAll(newAdminDTO.getStaffIdList());
        SysStaff staff = new SysStaff();
        // 循环设置管理状态
        administratorIds.forEach(staffId -> {
            staff.setStaffId(staffId);
            LambdaQueryWrapper<SysStaffVenue> wrapper = Wrappers.lambdaQuery();
            // 在关系表中是否存在该人员id，存在则设置管理状态为1
            wrapper.eq(SysStaffVenue::getStaffId, staffId);
            // 设置管理状态
            staff.setHasVenue((byte) 0);
            if (staffVenueMapper.exists(wrapper)) {
                // 设置管理状态
                staff.setHasVenue((byte) 1);
            }
            staffMapper.updateById(staff);
        });
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(int venueId) {
        // 执行删除前，查出管理的场馆id，然后查询并设置受影响场馆的管理状态
        Set<Integer> administratorIds = venueMapper.findAdministrator(venueId);
        // 执行删除操作
        if (venueMapper.deleteById(venueId) <= 0) {
            return ResponseVo.exp();
        }
        SysStaff staff = new SysStaff();
        // 循环设置管理状态
        administratorIds.forEach(staffId -> {
            staff.setStaffId(staffId);
            LambdaQueryWrapper<SysStaffVenue> wrapper = Wrappers.lambdaQuery();
            // 在关系表中是否存在该人员id，存在则设置管理状态为1
            wrapper.eq(SysStaffVenue::getStaffId, staffId);
            // 设置管理状态
            staff.setHasVenue((byte) 0);
            if (staffVenueMapper.exists(wrapper)) {
                // 设置管理状态
                staff.setHasVenue((byte) 1);
            }
            staffMapper.updateById(staff);
        });
        return ResponseVo.success();
    }

}
