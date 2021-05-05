package xyz.klenkiven.yygh.hosp.service;

import org.springframework.data.domain.Page;
import xyz.klenkiven.yygh.model.hosp.Department;
import xyz.klenkiven.yygh.vo.hosp.DepartmentQueryVo;
import xyz.klenkiven.yygh.vo.hosp.DepartmentVo;

import java.util.List;
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

    /**
     * 根据医院编号和部门编号删除指定的部门信息
     *
     * @param hoscode 医院编号
     * @param depcode 部门编号
     */
    void remove(String hoscode, String depcode);

    /**
     * 根据医院编号，查询医院所有科室列表
     * @param hoscode
     * @return
     */
    List<DepartmentVo> findDeptTree(String hoscode);
}
