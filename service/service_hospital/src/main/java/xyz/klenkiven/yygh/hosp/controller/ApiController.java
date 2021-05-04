package xyz.klenkiven.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.klenkiven.yygh.common.exception.YyghException;
import xyz.klenkiven.yygh.common.helper.HttpRequestHelper;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.common.result.ResultCodeEnum;
import xyz.klenkiven.yygh.common.utils.MD5;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.hosp.service.HospitalSetService;
import xyz.klenkiven.yygh.hosp.service.ScheduleService;
import xyz.klenkiven.yygh.model.hosp.Department;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.model.hosp.Schedule;
import xyz.klenkiven.yygh.vo.hosp.DepartmentQueryVo;
import xyz.klenkiven.yygh.vo.hosp.ScheduleQueryVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 关于医院文档的借口
 */
@Api(tags = "医院接口")
@RestController
@RequestMapping("/api/hosp")
@AllArgsConstructor
@CrossOrigin
public class ApiController {

    private final HospitalService hospitalService;
    private final HospitalSetService hospitalSetService;
    private final DepartmentService departmentService;
    private final ScheduleService scheduleService;

    /**
     * 保存医院相关信息
     * 为了保证医院信息只有该医院可以进行修改，因此需要进行
     * 签名校验。
     * 校验方法很简单，对签名进行MD5编码，之后进行比较就可以了
     *
     * @param servletRequest HTTP请求
     * @return 成功返回
     */
    @ApiOperation("医院上传接口")
    @PostMapping("saveHospital")
    public Result<?> saveHosp(HttpServletRequest servletRequest) {
        // Get parameter
        Map<String, String[]> resultMap = servletRequest.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(resultMap);

        // 1. Get Sign of Hospital encrypted in MD5
        String hospSign = (String) paramMap.get("sign");
        // 2. According to hospcode, query database for sign
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 3. Encrypt signKey
        String encryptSign = MD5.encrypt(signKey);
        // 4. Judge equal
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        // Call service`s method
        hospitalService.save(paramMap);
        return Result.ok();
    }

    /**
     * 医院查询接口
     *
     * @param request HTTP请求
     * @return 医院对象
     */
    @ApiOperation("查询医院接口")
    @PostMapping("/hospital/show")
    public Result<Hospital> showHospital(HttpServletRequest request) {
        // Get parameter
        Map<String, String[]> resultMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(resultMap);

        // get hoscode
        String hoscode = (String) paramMap.get("hoscode");

        // 校验签名
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        Hospital byHoscode = hospitalService.getByHoscode(hoscode);
        return Result.ok(byHoscode);
    }

    /**
     * 科室上传接口
     *      只能用于上传单个科室信息
     *
     * @param request HTTP请求
     * @return 成功信息
     */
    @ApiOperation("科室上传接口")
    @PostMapping("/saveDepartment")
    public Result<?> saveDepartment(HttpServletRequest request) {
        // Get parameter
        Map<String, String[]> resultMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(resultMap);

        // 校验签名
        String hoscode = (String) paramMap.get("hoscode");
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        departmentService.save(paramMap);

        return Result.ok();
    }

    /**
     * 科室查询接口
     *
     */
    @ApiOperation("科室查询接口")
    @PostMapping("/department/list")
    public Result<Page<Department>> listDepartment(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) paramMap.get("hoscode");
        int page = StringUtils.isEmpty(paramMap.get("page"))? 1: Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit"))? 1: Integer.parseInt((String) paramMap.get("limit"));

        // 校验签名
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        DepartmentQueryVo queryVo = new DepartmentQueryVo();
        queryVo.setHoscode(hoscode);
        Page<Department> departmentPage = departmentService.findPageDepartment(page, limit, queryVo);

        return Result.ok(departmentPage);
    }

    /**
     * 科室删除接口
     *
     * @param request HTTP请求
     * @return 成功信息
     */
    @ApiOperation("科室删除接口")
    @PostMapping("/department/remove")
    public Result<?> departmentRemove(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        // 校验签名
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        departmentService.remove(hoscode, depcode);

        return Result.ok();
    }

    /**
     * 排班上传接口
     *
     * @param request HTTP请求
     * @return 成功信息
     */
    @ApiOperation("排班上传接口")
    @PostMapping("/saveSchedule")
    public Result<?> saveSchedule(HttpServletRequest request) {
        Map<String, String[]> resultMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(resultMap);

        // 校验签名
        String hoscode = (String) paramMap.get("hoscode");
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        scheduleService.save(paramMap);

        return Result.ok();
    }

    /**
     * 排班查询接口
     *
     * @param request HTTP请求
     * @return 排版查询页
     */
    @ApiOperation("排班查询接口")
    @PostMapping("/schedule/list")
    public Result<Page<Schedule>> listSchedule(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) paramMap.get("hoscode");
        int page = StringUtils.isEmpty(paramMap.get("page"))? 1: Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit"))? 1: Integer.parseInt((String) paramMap.get("limit"));

        // 校验签名
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        ScheduleQueryVo queryVo = new ScheduleQueryVo();
        queryVo.setHoscode(hoscode);
        Page<Schedule> all = scheduleService.findPageSchedule(page, limit, queryVo);
        return Result.ok(all);
    }

    @ApiOperation("排班删除接口")
    @PostMapping("/schedule/remove")
    public Result<?> removeSchedule(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);

        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        // 校验签名
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String encryptSign = MD5.encrypt(signKey);
        if ( !hospSign.equals(encryptSign) )
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        scheduleService.remove(hoscode, hosScheduleId);

        return Result.ok();
    }
}
