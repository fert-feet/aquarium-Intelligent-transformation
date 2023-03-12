package com.aquarium.controller;

import com.aquarium.pojo.SysDevice;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysDeviceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tt-Tang
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/device")
public class SysDeviceController {

    @Resource
    private ISysDeviceService deviceService;

    /**
     * 设备分页查询
     *
     * @param page
     * @param limit
     * @param name
     * @param sort
     * @return
     */
    @GetMapping("/list")
    public ResponseVo list(
            @RequestParam(name = "pageNo") long page,
            @RequestParam(name = "pageSize") long limit,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) int venueId) {
        return deviceService.listDevice(page, limit, name, venueId);
    }

    /**
     * 查询设备列表不分页
     *
     * @return
     */
    @GetMapping("/originList")
    public ResponseVo originList() {
        List<SysDevice> list = deviceService.list();
        return ResponseVo.success().data("items", list).data("total", list.size());
    }

    /**
     * 新增或更新设备
     *
     * @param device
     * @return
     */
    @PostMapping("/addOrUpdate")
    public ResponseVo addOrUpdate(@RequestBody SysDevice device) {
        if (deviceService.saveOrUpdate(device)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    /**
     * 删除
     *
     * @return
     */
    @PostMapping("/delete")
    public ResponseVo delete(int deviceId) {
        return deviceService.delete(deviceId);
    }

    /**
     * 更新设备所属场馆
     *
     * @param device
     * @return
     */
    @PostMapping("/updateBelongsVenue")
    public ResponseVo updateBelongsVenue(@RequestBody SysDevice device) {
        return deviceService.updateBelongsVenue(device);
    }
}
