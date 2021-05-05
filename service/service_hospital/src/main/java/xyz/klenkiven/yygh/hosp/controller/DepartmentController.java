package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.vo.hosp.DepartmentVo;

import java.util.List;

@Api(tags = "医院科室")
@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @ApiOperation("根据医院编号，查询医院所有科室列表")
    @GetMapping("/list/{hoscode}")
    public Result<?> getDeptList(@PathVariable String hoscode) {

        List<DepartmentVo> deptList = departmentService.findDeptTree(hoscode);

        return Result.ok(deptList);
    }
}
