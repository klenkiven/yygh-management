package xyz.klenkiven.yygh.hosp.service;

import org.springframework.data.domain.Page;
import xyz.klenkiven.yygh.model.hosp.Schedule;
import xyz.klenkiven.yygh.vo.hosp.ScheduleQueryVo;

import java.util.Map;

public interface ScheduleService {

    /**
     * 保存排版信息对象
     *
     * @param paramMap 参数Map
     */
    void save(Map<String, Object> paramMap);

    /**
     * 分页获取排版信息
     *
     * @param page 当前页
     * @param limit 每页大小
     * @param queryVo 查询条件
     * @return 页信息
     */
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo queryVo);

    /**
     * 根据医院ID和排班ID唯一删除一个排班信息
     *
     * @param hoscode 医院编号
     * @param hosScheduleId 排班编号
     */
    void remove(String hoscode, String hosScheduleId);

    /**
     * 根据医院编号和科室编号查询排班规则数据
     * @param page 当前页
     * @param limit 每页大小
     * @param hoscode 医院编号
     * @param depcode 部门编号
     * @return 排班规则查询
     */
    Map<String, Object> getScheduleRule(long page, long limit, String hoscode, String depcode);
}
