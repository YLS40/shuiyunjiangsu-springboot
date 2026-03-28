package com.example.mapper;

import com.example.entity.Routes;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoutesMapper {

    int insert(Routes routes);

    void updateById(Routes routes);

    void deleteById(Integer id);

    @Select("select * from `routes` where id = #{id}")
    Routes selectById(Integer id);

    List<Routes> selectAll(Routes routes);

}
