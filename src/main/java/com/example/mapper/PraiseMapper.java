package com.example.mapper;

import com.example.entity.Praise;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PraiseMapper {

    @Insert("insert into praise(fid, user_id) values (#{fid}, #{userId})")
    void insert(Praise praise);

    @Delete("delete from praise where fid = #{fid} and user_id = #{userId}")
    void deleteByFidAndUserId(@Param("fid") Integer fid, @Param("userId") Integer userId);

    @Select("select * from praise where fid = #{fid} and user_id = #{userId}")
    Praise selectByFidAndUserId(@Param("fid") Integer fid, @Param("userId") Integer userId);

    @Select("select count(*) from praise where fid = #{fid}")
    Integer selectCount(Integer fid);

}
