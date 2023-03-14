package com.aquarium.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.aquarium.mapper.SysVenueMapper;
import com.aquarium.mapper.SysWaterQualityMapper;
import com.aquarium.pojo.SysVenue;
import com.aquarium.pojo.SysWaterQuality;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysWaterQualityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
public class SysWaterQualityServiceImpl extends ServiceImpl<SysWaterQualityMapper, SysWaterQuality> implements ISysWaterQualityService {

    @Resource
    private SysWaterQualityMapper waterQualityMapper;

    @Resource
    private SysVenueMapper venueMapper;

    @Override
    public ResponseVo listWaterData(long page, long limit, String name, int venueId, String date) {
        // 分页
        Page<SysWaterQuality> waterQualityPage = new Page<>();
        // 当前页面
        waterQualityPage.setCurrent(page);
        //页面大小
        waterQualityPage.setSize(limit);
        LambdaQueryWrapper<SysWaterQuality> wrapper = Wrappers.lambdaQuery();
        // 根据日期查询
        if (date != null) {
            wrapper.eq(SysWaterQuality::getDataDate, DateUtil.parse(date));
        }
        if (venueId != 0) {
            // 根据场馆ID查询场馆的水质数据
            wrapper.eq(SysWaterQuality::getVenueId, venueId);
        }
        // 根据日期顺序排列
        wrapper.orderByAsc(SysWaterQuality::getDataDate);
        Page<SysWaterQuality> selectPage = waterQualityMapper.selectPage(waterQualityPage, wrapper);
        return ResponseVo.success()
                .data("items", selectPage.getRecords())
                .data("totalCount", selectPage.getTotal())
                .data("pageNo", page);
    }

    @Override
    public ResponseVo createOne() {
        // 查询所有场馆信息，作为随机生成水质数据的归属场所
        List<SysVenue> venueList = venueMapper.findVenueList();
        SysWaterQuality waterQuality = new SysWaterQuality();
        // 随机生成数据并返回对象
        SysWaterQuality generatedWaterQuality = generateWaterQuality(venueList, waterQuality);
        if (waterQualityMapper.insert(generatedWaterQuality) <= 0) {
            return ResponseVo.exp();
        }
        return ResponseVo.success().data("item", generatedWaterQuality);
    }

    /**
     * 随机生成水质数据
     *
     * @param venueList
     * @param waterQuality
     * @return
     */
    private SysWaterQuality generateWaterQuality(List<SysVenue> venueList, SysWaterQuality waterQuality) {
        // 随机选择一个场馆
        SysVenue venue = RandomUtil.randomEle(venueList);
        String venueName = venueMapper.selectById(venue).getName();
        // 随机生成水温，数据乘上1.5是为了有几率产生超过阈值的数据以进行报警
        double randomTem = RandomUtil.randomDouble(venue.getMinWaterTemperature() * 0.9, venue.getMaxWaterTemperature() * 1.02);
        log.info("randomTem---{}", randomTem);
        // 随机生成PH值
        double randomPH = RandomUtil.randomDouble(venue.getMinWaterPh() * 0.9, venue.getMaxWaterPh());
        log.info("randomPH---{}", randomPH);
        // 随机生成盐度
        double randomSalt = RandomUtil.randomDouble(venue.getMinWaterSalt() * 0.9, venue.getMaxWaterSalt() * 1.01);
        log.info("randomSalt---{}", randomSalt);
        // 随机生成有效磷含量
        double randomAvaPho = RandomUtil.randomDouble(venue.getMinWaterAvailablePhosphorous() * 0.98, venue.getMaxWaterAvailablePhosphorous() * 1.02);
        log.info("randomAvaPho---{}", randomAvaPho);
        // 设置各数据报警状态
        SysWaterQuality setStatusWaterQuality = setWarningStatus(randomTem, randomPH, randomSalt, randomAvaPho, venue, waterQuality);
        log.info("isWarning---{}", setStatusWaterQuality.getWarnStatus());
        return setRandomDataWaterQuality(venue.getVenueId(), venueName, randomSalt, randomPH, randomAvaPho, randomTem, setStatusWaterQuality);
    }

    /**
     * 判断报警
     *
     * @param randomTem
     * @param randomPH
     * @param randomSalt
     * @param randomAvaPho
     * @param venue
     * @param waterQuality
     * @return
     */
    private SysWaterQuality setWarningStatus(double randomTem, double randomPH, double randomSalt, double randomAvaPho, SysVenue venue, SysWaterQuality waterQuality) {
        List<Boolean> warnList = new ArrayList<>();
        // 逐个数据判断是否有报警，若有一组数据超过或小于阈值，就报警
        if (compareData(randomTem, venue.getMinWaterTemperature(), venue.getMaxWaterTemperature())) {
            waterQuality.setTemStatus((byte) 1);
            log.info("温度报警");
            warnList.add(true);
        }
        // PH值比较
        if (compareData(randomPH, venue.getMinWaterPh(), venue.getMaxWaterPh())) {
            waterQuality.setPhStatus((byte) 1);
            log.info("PH值报警");
            warnList.add(true);
        }
        // 有效磷比较
        if (compareData(randomSalt, venue.getMinWaterAvailablePhosphorous(), venue.getMaxWaterAvailablePhosphorous())) {
            waterQuality.setAvaPhoStatus((byte) 1);
            log.info("盐度报警");
            warnList.add(true);
        }
        // 盐度比较
        if (compareData(randomAvaPho, venue.getMinWaterSalt(), venue.getMaxWaterSalt())) {
            waterQuality.setSaltStatus((byte) 1);
            log.info("有效磷含量报警");
            warnList.add(true);
        }
        // 有一组数据异常则报警
        if (warnList.size() > 0) {
            waterQuality.setWarnStatus((byte) 1);
        }
        return waterQuality;
    }

    /**
     * 数据比较
     *
     * @param randomData
     * @param minThreshold
     * @param maxThreshold
     * @return
     */
    private boolean compareData(double randomData, Double minThreshold, Double maxThreshold) {
        // 比较是否数据大于或小于阈值
        if (randomData < minThreshold || randomData > maxThreshold) {
            return true;
        }
        return false;
    }

    /**
     * 将随机数据设置到水质对象中
     *
     * @param venueName
     * @param randomSalt
     * @param randomPH
     * @param randomAvaPho
     * @param randomTem
     * @param waterQuality
     * @return
     */
    private SysWaterQuality setRandomDataWaterQuality(Integer venueId, String venueName, double randomSalt, double randomPH, double randomAvaPho, double randomTem, SysWaterQuality waterQuality) {
        waterQuality.setVenueId(venueId);
        waterQuality.setVenueName(venueName);
        // PH值
        waterQuality.setWaterPh(NumberUtil.round(randomPH, 1, RoundingMode.FLOOR).doubleValue());
        // 盐度
        waterQuality.setWaterSalt(NumberUtil.round(randomSalt, 2, RoundingMode.FLOOR).doubleValue());
        // 温度
        waterQuality.setWaterTemperature(NumberUtil.round(randomTem, 1, RoundingMode.FLOOR).doubleValue());
        // 有效磷含量
        waterQuality.setWaterAvailablePhosphorous(NumberUtil.round(randomAvaPho, 1, RoundingMode.FLOOR).doubleValue());
        return waterQuality;
    }


}
