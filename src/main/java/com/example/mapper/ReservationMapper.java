package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.DateCountDTO;
import com.example.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

    // 1. 查所有预约
    @Select("SELECT * FROM reservation")
    List<Map<String, Object>> listAll();

    // 2. 查总预约数
    @Select("SELECT COUNT(*) FROM reservation")
    Long countAll();

    // 3. 按日期统计近7天预约
    @Select("SELECT DATE(create_time) as date, COUNT(*) as count FROM reservation WHERE create_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) GROUP BY DATE(create_time)")
    List<Map<String, Object>> countByDate();

    /**
     * 按日期统计预约数（支持按create_time或travel_date）
     * @param startDate 开始日期（yyyy-MM-dd）
     * @param endDate 结束日期（yyyy-MM-dd）
     * @param dateField 统计字段（create_time 或 travel_date）
     * @return 日期+对应数量
     */
    @Select("SELECT DATE(${dateField}) AS name, COUNT(*) AS value " +
            "FROM reservation " +
            "WHERE DATE(${dateField}) BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(${dateField})")
    List<DateCountDTO> countByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("dateField") String dateField
    );
}