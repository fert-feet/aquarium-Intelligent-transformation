package com.aquarium.service.impl;

import com.aquarium.dto.UpdateManagedVenueDTO;
import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysStaffVenueMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysStaffVenue;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
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
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Resource
    private SysStaffVenueMapper staffVenueMapper;

    @Override
    public ResponseVo listStaff(long page, long limit, String name, byte hasVenue) {
        // 分页
        Page<SysStaff> staffPage = new Page<>();
        // 当前页面
        staffPage.setCurrent(page);
        //页面大小
        staffPage.setSize(limit);
        LambdaQueryWrapper<SysStaff> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据人员姓名过滤
            wrapper.like(SysStaff::getName, name);
        }
        if (hasVenue == 1 || hasVenue == 0) {
            // 根据是否已有管理的场馆过滤
            wrapper.eq(SysStaff::getHasVenue, hasVenue);
        }
        // 顺序排列
        wrapper.orderByAsc(SysStaff::getStaffId);
        Page<SysStaff> selectPage = staffMapper.selectPage(staffPage, wrapper);
        return ResponseVo.success()
                .data("items", selectPage.getRecords())
                .data("totalCount", selectPage.getTotal())
                .data("pageNo", page);
    }

    @Override
    public ResponseVo findManagedVenue(int staffId) {
        Set<Integer> managedVenueIds = staffMapper.findManagedVenue(staffId);
        return ResponseVo.success().data("items", managedVenueIds).data("total", managedVenueIds.size());
    }

    @Override
    public ResponseVo updateManagedVenue(UpdateManagedVenueDTO newVenueDTO) {
        // 查出旧关系id
        Set<Integer> managedVenueInterIds = staffMapper.findManagedVenueInterIds(newVenueDTO.getStaffId());
        // 查找出准备删除部分关系的场馆id
        Set<Integer> managedVenueIds = staffMapper.findManagedVenue(newVenueDTO.getStaffId());
        // 删除关系
        if (managedVenueInterIds.size() > 0) {
            if (staffVenueMapper.deleteBatchIds(managedVenueInterIds) < 0) {
                return ResponseVo.exp();
            }
        }
        SysStaff staff = new SysStaff();
        // 设置人员管理场馆状态
        staff.setHasVenue((byte) 0);
        staff.setStaffId(newVenueDTO.getStaffId());
        if (newVenueDTO.getVenueIdList().size() > 0) {
            // 增加新的人员场馆关系
            newVenueDTO.getVenueIdList().forEach(venueId -> {
                SysStaffVenue sysStaffVenue = new SysStaffVenue();
                sysStaffVenue.setStaffId(newVenueDTO.getStaffId());
                sysStaffVenue.setVenueId(venueId);
                staffVenueMapper.insert(sysStaffVenue);
                log.info("insertStaff---{}", sysStaffVenue.getId());
            });
            // 设置人员管理场馆状态
            staff.setHasVenue((byte) 1);
        }
        // 更新人员管理场馆状态
        if (staffMapper.updateById(staff) <= 0) {
            return ResponseVo.exp();
        }
        // 设置变动过的场馆管理员状态
        managedVenueIds.addAll(newVenueDTO.getVenueIdList());
        SysVenue venue = new SysVenue();
        // 循环设置管理状态
        managedVenueIds.forEach(venueId -> {
            venue.setVenueId(venueId);
            LambdaQueryWrapper<SysStaffVenue> wrapper = Wrappers.lambdaQuery();
            // 在关系表中是否存在该场馆id，存在则设置管理状态为1
            wrapper.eq(SysStaffVenue::getVenueId, venueId);
            // 设置管理状态
            venue.setHasAdmin((byte) 0);
            if (staffVenueMapper.exists(wrapper)) {
                // 设置管理状态
                venue.setHasAdmin((byte) 1);
            }
            venueMapper.updateById(venue);
        });
        return ResponseVo.success();
    }

    @Override
    public ResponseVo delete(int staffId) {
        // 执行删除前，查出管理的场馆id，然后查询并设置受影响场馆的管理状态
        Set<Integer> managedVenue = staffMapper.findManagedVenue(staffId);
        // 执行删除操作
        if (staffMapper.deleteById(staffId) <= 0) {
            return ResponseVo.exp();
        }
        SysVenue venue = new SysVenue();
        managedVenue.forEach(venueId -> {
            venue.setVenueId(venueId);
            LambdaQueryWrapper<SysStaffVenue> wrapper = Wrappers.lambdaQuery();
            // 在关系表中是否存在该场馆id，存在则设置管理状态为1
            wrapper.eq(SysStaffVenue::getVenueId, venueId);
            // 设置管理状态
            venue.setHasAdmin((byte) 0);
            if (staffVenueMapper.exists(wrapper)) {
                // 设置管理状态
                venue.setHasAdmin((byte) 1);
            }
            venueMapper.updateById(venue);
        });
        return ResponseVo.success();
    }

}
