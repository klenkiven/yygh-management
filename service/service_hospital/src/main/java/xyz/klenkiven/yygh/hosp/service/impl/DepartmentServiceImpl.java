package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.DepartmentRepository;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.model.hosp.Department;
import xyz.klenkiven.yygh.vo.hosp.DepartmentQueryVo;
import xyz.klenkiven.yygh.vo.hosp.DepartmentVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        // Convert paramMap to Department instance
        String s = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(s, Department.class);

        Department departmentExist = departmentRepository.
                getDepatmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if (departmentExist != null) {
            department.setId(departmentExist.getId());
            department.setCreateTime(departmentExist.getCreateTime());
            department.setIsDeleted(departmentExist.getIsDeleted());
        } else {
            department.setCreateTime(new Date());
            department.setIsDeleted(0);
        }

        department.setUpdateTime(new Date());
        departmentRepository.save(department);
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo queryVo) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Department department = new Department();
        BeanUtils.copyProperties(queryVo, department);
        department.setIsDeleted(0);

        // Construct Query Condition
        ExampleMatcher matcher = ExampleMatcher.matching()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                    .withIgnoreCase(true);
        Example<Department> departmentExample = Example.of(department, matcher);

        return departmentRepository.findAll(departmentExample, pageable);
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepatmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null)
            departmentRepository.deleteDepartmentByHoscodeAndDepcode(hoscode, depcode);
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        List<DepartmentVo> result = new ArrayList<>();

        // 获取医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example<Department> departmentExample = Example.of(departmentQuery);
        List<Department> departmentList = departmentRepository.findAll(departmentExample);

        // 根据大科室编号 bigcode 分组, 获取大科室下级子科室
        Map<String, List<Department>> departmentMap =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));

        // 遍历Map集合 departmentMap
        for (Map.Entry<String, List<Department>> item: departmentMap.entrySet()) {
            String bigcode = item.getKey();
            List<Department> departmentChildrenList = item.getValue();

            // 封装大科室
            DepartmentVo departmentParentVo = new DepartmentVo();
            departmentParentVo.setDepcode(bigcode);
            departmentParentVo.setDepname(departmentChildrenList.get(0).getBigname());

            // 封装小科室
            List<DepartmentVo> departmentChildrenVoList = new ArrayList<>();
            for (Department department: departmentChildrenList) {
                DepartmentVo departmentVo = new DepartmentVo();
                BeanUtils.copyProperties(department, departmentVo);
                departmentChildrenVoList.add(departmentVo);
            }
            departmentParentVo.setChildren(departmentChildrenVoList);

            result.add(departmentParentVo);
        }

        return result;
    }
}
