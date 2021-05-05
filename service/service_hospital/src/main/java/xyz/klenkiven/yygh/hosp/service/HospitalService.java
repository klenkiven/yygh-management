package xyz.klenkiven.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Page;
import xyz.klenkiven.yygh.model.hosp.Hospital;
import xyz.klenkiven.yygh.vo.hosp.HospitalQueryVo;

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

    /**
     * 根据查询VO分页查询
     *
     * @param page 当前页
     * @param limit 每页大小
     * @param queryVo 查询条件
     * @return 页内容
     */
    Page<Hospital> selectHospPage(int page, int limit, HospitalQueryVo queryVo);

    /**
     * 更新医院状态
     * @param id 医院ID
     * @param status 状态值
     */
    void updateStatus(String id, Integer status);

    /**
     * 根据ID查询医院对象
     * @param id 医院ID
     * @return 医院对象
     */
    Map<String, Object> getHospById(String id);

    /**
     * 根据ID查询医院名称
     * @param hoscode  医院编号
     * @return 医院名称
     */
    String getHosnameByHoscode(String hoscode);
}
