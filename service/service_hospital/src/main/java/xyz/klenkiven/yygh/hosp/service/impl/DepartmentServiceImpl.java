package xyz.klenkiven.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.klenkiven.yygh.hosp.repository.DepartmentRepository;
import xyz.klenkiven.yygh.hosp.service.DepartmentService;
import xyz.klenkiven.yygh.model.hosp.Department;

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
}
