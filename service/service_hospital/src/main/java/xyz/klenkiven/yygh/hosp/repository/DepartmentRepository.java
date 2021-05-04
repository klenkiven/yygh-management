package xyz.klenkiven.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.klenkiven.yygh.model.hosp.Department;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {


    /**
     * 根据医院编号和科室编号查找科室对象
     *      因为医院和科室两个加在一起这个对象就被
     *      唯一确定了，因此需要使用两个参数。
     *
     * @param hoscode 医院编号
     * @param depcode 科室编号
     * @return 科室对象
     */
    Department getDepatmentByHoscodeAndDepcode(String hoscode, String depcode);
}
