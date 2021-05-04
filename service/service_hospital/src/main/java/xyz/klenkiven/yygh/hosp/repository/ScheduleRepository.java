package xyz.klenkiven.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.klenkiven.yygh.model.hosp.Schedule;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    /**
     * 根据医院编号和排班ID查找排班信息
     *
     * @param hoscode 医院编号
     * @param hosScheduleId 排班ID
     * @return 排版信息
     */
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

}
