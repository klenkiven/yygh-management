package xyz.klenkiven.yygh.hosp.controller;

import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.model.hosp.BookingRule;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.vo.hosp.DepartmentVo;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

import java.util.List;
import java.util.Map;


@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/api/hosp/hospital")
@AllArgsConstructor
public class HospitalApiController {

    private final HospitalService hospitalService;
    private final DepartmentService departmentService;

    /**
     * 医院分页查询
     * @param page 当前页
     * @param limit 页大小
     * @param hospitalQueryVo 查询条件
     * @return 页内容
     */
    @ApiOperation(value = "查询医院的列表功能")
    @GetMapping("/find/{page}/{limit}")
    public Result<Page<Hospital>> findHospList(@PathVariable Integer page,
                                               @PathVariable Integer limit,
                                               HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospitalPage =
                hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitalPage);
    }

    /**
     * 根据医院名称查询
     * @param hosname 医院名称
     * @return 医院列表
     */
    @ApiOperation(value = "根据医院名称查询")
    @GetMapping("/find/{hosname}")
    public Result<List<Hospital>> findHospByHospName(@PathVariable String hosname) {
        List<Hospital> hospital = hospitalService.findByHospname(hosname);
        return Result.ok(hospital);
    }

    @ApiOperation(value = "获取科室列表")
    @GetMapping("department/{hoscode}")
    public Result<List<DepartmentVo>> findDepartmentList(@PathVariable String hoscode) {
        List<DepartmentVo> deptTree = departmentService.findDeptTree(hoscode);
        return Result.ok(deptTree);
    }

    @ApiOperation(value = "医院预约挂号详情")
    @GetMapping("{hoscode}")
    public Result<Map<String, Object>> getHospitalDetail(@PathVariable String hoscode) {
        Map<String, Object> hospitalDetail = hospitalService.getHospitalDetail(hoscode);
        return Result.ok(hospitalDetail);
    }

}
