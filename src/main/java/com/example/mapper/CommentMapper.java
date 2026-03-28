package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    int insert(Comment comment);

    void updateById(Comment comment);

    void deleteById(Integer id);

    Comment selectById(Integer id);

    List<Comment> selectAll(Comment comment);

    @Select("select count(*) from comment where fid = #{fid} and module = #{module}")
    Integer selectCount(@Param("fid") Integer fid, @Param("module") String module);

    List<Comment> selectRoot(Comment comment);

    List<Comment> selectByRootId(Integer rootId);

    @Select("select * from comment where pid = #{pid}")
    List<Comment> selectByPid(Integer pid);
}
