package xyz.klenkiven.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.klenkiven.yygh.common.exception.YyghException;
import xyz.klenkiven.yygh.common.result.Result;
import xyz.klenkiven.yygh.common.utils.MD5;
import xyz.klenkiven.yygh.hosp.service.HospitalSetService;
import xyz.klenkiven.yygh.model.hosp.HospitalSet;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

import java.util.List;
import java.util.Random;

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
@Api(tags = "医院设置")
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
    @PostMapping("/findPage/{current}/{limit}")
    public Result<Page<HospitalSet>> findPageHospSet(@PathVariable long current,
                                                     @PathVariable long limit,
                                                     @RequestBody(required = false) HospitalQueryVo hospitalQueryVo) {
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

    /**
     * 添加医院设置接口
     *
     * @param hospitalSet 医院设置内容
     * @return 返回结果
     */
    @ApiOperation("添加医院设置接口")
    @PostMapping("/saveHospitalSet")
    public Result<?> saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        Random random = new Random();

        // 设置状态 1 可以使用 0 不可以使用
        hospitalSet.setStatus(1);
        // Sign Private Secret
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        // Change info
        boolean save = hospitalSetService.save(hospitalSet);
        if (save)
            return Result.ok();
        else
            return Result.fail();
    }

    /**
     * 获取通过ID医院设置
     *
     * @param id 医院ID
     * @return 医院设置信息
     */
    @ApiOperation("获取通过ID医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result<HospitalSet> getHospSetById(@PathVariable Long id) {
        HospitalSet byId = hospitalSetService.getById(id);
        return Result.ok(byId);
    }

    /**
     * 修改医院设置
     *
     * @param hospitalSet 医院修改后的信息
     * @return 执行结果
     */
    @ApiOperation("修改医院设置")
    @PutMapping("/updateHospSet")
    public Result<?> updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b)
            return Result.ok();
        else
            return Result.fail();
    }

    /**
     * 批量删除医院设置
     *
     * @param ids  医院ID
     * @return 执行结果
     */
    @ApiOperation("批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public Result<?> batchRemoveHosp(@RequestBody List<Long> ids) {
        hospitalSetService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 锁定或者解锁医院设置
     *
     * @param id 医院设置ID
     * @param status 医院设置锁定状态
     * @return 返回执行结果
     */
    @ApiOperation("锁定或者解锁医院设置")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result<?> lockHospitalSet(@PathVariable Long id,
                                     @PathVariable int status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        if (hospitalSet == null) return Result.fail();

        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);

        return Result.ok();
    }

    /**
     * 发送签名密钥
     *
     * @param id 医院设置ID
     * @return 返回结果
     */
    @ApiOperation("发送签名密钥")
    @PutMapping("/sendKey/{id}")
    public Result<?> sentKeyToHospital(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();

        // TODO 发送短信
        return Result.ok();
    }
}
