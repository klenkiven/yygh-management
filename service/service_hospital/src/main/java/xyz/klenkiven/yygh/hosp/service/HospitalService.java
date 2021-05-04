package xyz.klenkiven.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.klenkiven.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

    /**
     * 根据是否存在保存对象
     *
     * @param paramMap 参数Map
     */
    void save(Map<String, Object> paramMap);

    /**
     * 根据医院的hoscode获取医院对象
     *
     * @param hoscode 医院编号
     * @return 医院对象
     */
    Hospital getByHoscode(String hoscode);
}
