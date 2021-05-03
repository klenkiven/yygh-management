package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.klenkiven.yygh.common.exception.YyghException;
import xyz.klenkiven.yygh.common.helper.HttpRequestHelper;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.common.result.ResultCodeEnum;
import xyz.klenkiven.yygh.common.utils.MD5;
import xyz.klenkiven.yygh.hosp.service.HospitalService;
import xyz.klenkiven.yygh.hosp.service.HospitalSetService;

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

    /**
     * 保存医院相关信息
     * 为了保证医院信息只有该医院可以进行修改，因此需要进行
     * 签名校验。
     * 校验方法很简单，对签名进行MD5编码，之后进行比较就可以了
     *
     * @param servletRequest
     * @return
     */
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
}
