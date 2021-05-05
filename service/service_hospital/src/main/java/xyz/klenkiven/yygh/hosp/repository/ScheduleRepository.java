package xyz.klenkiven.yygh.hosp.repository;

import com.mongodb.internal.thread.DaemonThreadFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.klenkiven.yygh.model.hosp.Schedule;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据医院编号和排班ID查找删除信息
     *
     * @param hoscode 医院编号
     * @param hosScheduleId 排班信息
     */
    void deleteScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    /**
     * 根据医院编号，科室编号和工作日期，查询排班详细信息
     * @param hoscode 医院编号
     * @param depcode 科室编号
     * @param workDate 工作日期
     * @return 排班详细信息
     */
    List<Schedule> findAllByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date workDate);
}
