package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

import java.util.Map;

@Api(tags = "医院管理")
@RestController
@RequestMapping("/admin/hosp/hospital")
@AllArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    /**
     * 医院分页查询
     * @param page 当前页
     * @param limit 页大小
     * @param queryVo 查询条件
     * @return 页内容
     */
    @ApiOperation("医院分页查询")
    @GetMapping("/list/{page}/{limit}")
    public Result<Page<Hospital>> pagingResult(@PathVariable int page,
                                               @PathVariable int limit,
                                               HospitalQueryVo queryVo) {
        Page<Hospital> hospitalPage = hospitalService.selectHospPage(page, limit, queryVo);
        return Result.ok(hospitalPage);
    }

    /**
     * 更新医院状态设置
     * @param id 医院ID
     * @param status 医院状态
     * @return 成功返回
     */
    @ApiOperation("更新医院状态设置")
    @PutMapping("/status/{id}/{status}")
    public Result<?> updateHospitalStatus(@PathVariable String id,
                                          @PathVariable Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    @ApiOperation("医院详情信息")
    @GetMapping("/{id}")
    public Result<Map<String, Object>> showHospitalDetail(@PathVariable String id) {
        Map<String, Object> hospital = hospitalService.getHospById(id);
        return Result.ok(hospital);
    }
}
