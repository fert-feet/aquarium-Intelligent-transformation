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
    public ResponseVo listDevice(long page, long limit, String name) {
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
        // 顺序排列
        wrapper.orderByAsc(SysDevice::getVenueId);
        Page<SysDevice> selectPage = deviceMapper.selectPage(devicePage, wrapper);
        return ResponseVo.success().data("items", selectPage.getRecords()).data("total", selectPage.getTotal());
    }

    @Override
    public ResponseVo addOrUpdate(SysDevice device) {
        // 新增操作
        if (device.getDeviceId() == null) {
            deviceMapper.insert(device);
            return ResponseVo.success();
        }
        // 设置绑定实验室操作
        if (device.getVenueId() != null) {
            SysVenue venue = new SysVenue();
            venue.setVenueId(device.getVenueId());
            venue.setDeviceId(device.getDeviceId());
            // 同步设置实验室中设备ID
            if (venueMapper.updateById(venue) == 0) {
                return ResponseVo.exp();
            }
        }
        // 更新设备信息
        if (deviceMapper.updateById(device) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }
}
