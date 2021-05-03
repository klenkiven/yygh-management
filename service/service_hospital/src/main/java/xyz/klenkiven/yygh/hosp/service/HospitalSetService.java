package xyz.klenkiven.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.klenkiven.yygh.model.hosp.HospitalSet;

/**
 * 医院设置的服务类接口
 *
 * @author ：klenkiven
 * @date ：2021/4/8 19:03
 */
public interface HospitalSetService extends IService<HospitalSet> {

    /**
     * 通过hoscode获取医院的签名
     *
     * @param hoscode 医院编号
     * @return 医院签名
     */
    String getSignKey(String hoscode);

}
