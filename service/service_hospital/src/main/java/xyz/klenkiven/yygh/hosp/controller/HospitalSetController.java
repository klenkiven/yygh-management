package xyz.klenkiven.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.hosp.service.HospitalSetService;
import xyz.klenkiven.yygh.model.hosp.HospitalSet;

import java.util.List;

/**
 * 医院设置的Controller
 * 注意：这里使用的是构造器依赖注入的方法，
 *      属性注入的方法可能会导致注入的是
 *      空的依赖
 * 参考资料：
 *      https://blog.csdn.net/zhangjingao/article/details/81094529
 *
 * @author ：klenkiven
 * @date ：2021/4/8 19:06
 */
@Api("医院设置")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@AllArgsConstructor
public class HospitalSetController {

    /**
     * 通过构造器来强制注入
     */
    private final HospitalSetService hospitalSetService;

    /**
     * 查询医院的所有信息
     *
     * @return 医院的信息
     */
    @ApiOperation("查询医院所有信息")
    @GetMapping("/findAll")
    public List<HospitalSet> findAllHospitalSet() {
        // 调用Service的方法
        return hospitalSetService.list();
    }

    /**
     * 根据ID逻辑删除医院的设置信息
     *
     * @param id HospitalSet的ID
     * @return 成功与否
     */
    @DeleteMapping("/{id}")
    public boolean removeHospitalSet(@PathVariable Long id) {
        // 调用Hospital的删除方法（逻辑删除）
        return hospitalSetService.removeById(id);
    }
}
