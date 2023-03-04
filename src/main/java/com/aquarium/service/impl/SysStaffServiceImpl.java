package com.aquarium.service.impl;

import com.aquarium.mapper.SysStaffMapper;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.pojo.SysStaff;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysStaffService;
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
public class SysStaffServiceImpl extends ServiceImpl<SysStaffMapper, SysStaff> implements ISysStaffService {

    @Resource
    private SysStaffMapper staffMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Override
    public ResponseVo createOrUpdate(SysStaff sysStaff) {
        // 直接插入
        if (sysStaff.getStaffId() == null) {
            staffMapper.insert(sysStaff);
            return ResponseVo.success();
        }
        // 若更改归属实验室，级联更新实验室管理人员
        if (sysStaff.getVenueId() != null) {
            SysVenue sysVenue = venueMapper.selectById(sysStaff.getVenueId());
            sysVenue.setStaffId(sysStaff.getStaffId());
            venueMapper.updateById(sysVenue);
        }
        // 最后都要更新人员信息
        if (staffMapper.updateById(sysStaff) > 0) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }
}
