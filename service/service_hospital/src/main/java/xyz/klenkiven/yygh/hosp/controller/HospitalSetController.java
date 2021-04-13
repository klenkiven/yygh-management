package xyz.klenkiven.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.poi.util.StringUtil;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.hosp.service.HospitalSetService;
import xyz.klenkiven.yygh.model.hosp.HospitalSet;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

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
    public Result<List<HospitalSet>> findAllHospitalSet() {
        // 调用Service的方法
        return Result.ok(hospitalSetService.list());
    }

    /**
     * 根据ID逻辑删除医院的设置信息
     *
     * @param id HospitalSet的ID
     * @return 成功与否
     */
    @ApiOperation("逻辑删除医院设置")
    @DeleteMapping("/{id}")
    public Result<?> removeHospitalSet(@PathVariable Long id) {
        // 调用Hospital的删除方法（逻辑删除）
        if (hospitalSetService.removeById(id))
            return Result.ok();
        else
            return Result.fail();
    }

    /**
     * 分页查询 && 条件查询
     *
     * @param current 当前页面
     * @param limit 每页限制
     * @param hospitalQueryVo 传递条件
     * @return 返回Page结果
     */
    @ApiOperation("条件查询分页展示")
    @GetMapping("/findPage/{current}/{limit}")
    public Result<Page<HospitalSet>> findPageHospSet(@PathVariable long current,
                                                     @PathVariable long limit,
                                                     HospitalQueryVo hospitalQueryVo) {
        // 创建Page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        // 构造条件
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalQueryVo.getHosname();
        String hoscode = hospitalQueryVo.getHoscode();

        if (!StringUtils.isEmpty(hosname))
            queryWrapper.like("hosname", hosname);
        if (!StringUtils.isEmpty(hoscode))
            queryWrapper.eq("hoscode", hoscode);
        // Get Page Info
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);

        return Result.ok(hospitalSetPage);
    }
}
