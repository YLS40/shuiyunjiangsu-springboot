package com.example.mapper;

import com.example.entity.Tourism;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TourismMapper {

    int insert(Tourism tourism);

    void updateById(Tourism tourism);

    void deleteById(Integer id);

    @Select("select * from `tourism` where id = #{id}")
    Tourism selectById(Integer id);

    List<Tourism> selectAll(Tourism tourism);

}
