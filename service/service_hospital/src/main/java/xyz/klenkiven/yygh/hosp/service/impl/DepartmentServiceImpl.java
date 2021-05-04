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

import java.util.Date;
import java.util.Map;

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
        Pageable pageable = PageRequest.of(page, limit);
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
}
