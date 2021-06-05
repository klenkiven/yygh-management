package xyz.klenkiven.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.klenkiven.yygh.model.hosp.Hospital;

import java.util.List;
import java.util.Map;

/**
 * 医院的MongoRepository
 *
 * @author klenkiven
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    /**
     * 通过hoscode判断数据是否存在
     *      注意：
     *          Mongo根据命名规范自动生成对应的方法
     *          不要太方便
     *
     * @param hoscode 医院代码
     * @return 对应医院code
     */
    Hospital getHospitalByHoscode(String hoscode);

    /**
     * 根据医院名称获取医院列表
     * @param hospname 医院名称
     * @return 医院列表
     */
    List<Hospital> findHospitalByHosnameLike(String hospname);
}
