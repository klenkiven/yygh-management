package xyz.klenkiven.yygh.hosp.service;

import org.springframework.data.domain.Page;
import xyz.klenkiven.yygh.model.hosp.Department;
import xyz.klenkiven.yygh.vo.hosp.DepartmentQueryVo;

import java.util.Map;

public interface DepartmentService {

    /**
     * 保存科室信息
     *
     * @param paramMap 参数Map
     */
    void save(Map<String, Object> paramMap);

    /**
     * 分页查询科室信息
     *
     * @param page 当前页
     * @param limit 页限长
     * @param queryVo 查询条件
     * @return 页信息
     */
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo queryVo);
}
