package com.example.mapper;

import com.example.entity.Travels;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TravelsMapper {

    int insert(Travels travels);

    void updateById(Travels travels);

    void deleteById(Integer id);

    @Select("select travels.*, user.name as userName, user.avatar as userAvatar from `travels`" +
            " left join user on travels.user_id = user.id where travels.id = #{id}")
    Travels selectById(Integer id);

    List<Travels> selectAll(Travels travels);

    @Update("update travels set read_count = read_count + 1 where id = #{id}")
    void updateReadCount(Integer id);

}
