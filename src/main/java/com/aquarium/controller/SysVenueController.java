package com.aquarium.controller;

import com.aquarium.dto.UpdateAdministratorDTO;
import com.aquarium.pojo.SysVenue;
import com.aquarium.response.ResponseVo;
import com.aquarium.service.ISysVenueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/venue")
public class SysVenueController {

    @Resource
    private ISysVenueService venueService;

    /**
     * 场馆分页查询
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
            @RequestParam(required = false) byte hasAdmin) {
        return venueService.listVenue(page, limit, name, hasAdmin);
    }

    /**
     * 查询场馆不分页
     *
     * @return
     */
    @GetMapping("/originList")
    public ResponseVo originList() {
        List<SysVenue> list = venueService.list();
        return ResponseVo.success().data("items", list).data("total", list.size());
    }

    /**
     * 删除
     *
     * @param venueId
     * @return
     */
    @PostMapping("/delete")
    public ResponseVo deleteStaff(int venueId) {
        return venueService.delete(venueId);
    }

    /**
     * 更新或新增场馆
     *
     * @param venue
     * @return
     */
    @PostMapping("/addOrUpdate")
    public ResponseVo addOrUpdate(@RequestBody SysVenue venue) {
        if (venueService.saveOrUpdate(venue)) {
            return ResponseVo.success();
        }
        return ResponseVo.exp();
    }

    /**
     * 查找实验室的管理员
     *
     * @param venueId
     * @return
     */
    @GetMapping("/administrator")
    public ResponseVo findAdministrator(int venueId) {
        return venueService.findAdministrator(venueId);
    }

    /**
     * 更新场馆管理员
     *
     * @param newAdminDTO
     * @return
     */
    @PostMapping("/updateAdministrator")
    public ResponseVo updateAdministrator(@RequestBody UpdateAdministratorDTO newAdminDTO) {
        return venueService.updateAdministrator(newAdminDTO);
    }

}
