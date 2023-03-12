package com.aquarium.service.impl;

import com.aquarium.mapper.SysDeviceMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysDevice;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysDeviceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@Service
public class SysDeviceServiceImpl extends ServiceImpl<SysDeviceMapper, SysDevice> implements ISysDeviceService {

    @Resource
    private SysDeviceMapper deviceMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Override
    public ResponseVo listDevice(long page, long limit, String name, String venueName) {
        // 分页
        Page<SysDevice> devicePage = new Page<>();
        // 当前页面
        devicePage.setCurrent(page);
        //页面大小
        devicePage.setSize(limit);
        LambdaQueryWrapper<SysDevice> wrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(name)) {
            // 根据设备名称查询
            wrapper.like(SysDevice::getName, name);
        }
        if (!StringUtils.isEmpty(venueName)) {
            // 根据设备所属场馆过滤
            wrapper.like(SysDevice::getVenueName, venueName);
        }
        // 顺序排列
        wrapper.orderByAsc(SysDevice::getVenueId);
        Page<SysDevice> selectPage = deviceMapper.selectPage(devicePage, wrapper);
        return ResponseVo.success()
                .data("items", selectPage.getRecords())
                .data("totalCount", selectPage.getTotal())
                .data("pageNo", page);
    }

    @Override
    public ResponseVo updateBelongsVenue(SysDevice device) {
        // 设定新绑定场馆绑定状态
        if (device.getVenueId() != 0) {
            // 查出新绑定场馆信息
            SysVenue newVenue = venueMapper.selectById(device.getVenueId());
            newVenue.setHasDevice((byte) 1);
            // 更新当前绑定场馆绑定状态
            if (venueMapper.updateById(newVenue) <= 0) {
                return ResponseVo.exp();
            }
            // 设置设备所属场馆名称
            device.setVenueName(newVenue.getName());
        }
        // 更新前找到原绑定的场馆
        SysVenue belongsVenue = deviceMapper.findBelongsVenue(device.getDeviceId());
        // 执行更新
        if (deviceMapper.updateById(device) <= 0) {
            return ResponseVo.exp();
        }
        // 设置原绑定的场馆绑定状态
        if (belongsVenue != null) {
            LambdaQueryWrapper<SysDevice> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SysDevice::getVenueId, belongsVenue.getVenueId());
            if (!deviceMapper.exists(wrapper)) {
                belongsVenue.setHasDevice((byte) 0);
                // 更新原绑定场馆绑定状态
                if (venueMapper.updateById(belongsVenue) <= 0) {
                    return ResponseVo.exp();
                }
            }
        }
        return ResponseVo.success();
    }
}
