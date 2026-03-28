package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Tourism;
import com.example.mapper.TourismMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class TourismService {

    @Resource
    private TourismMapper tourismMapper;

    public void add(Tourism tourism) {
        tourism.setDate(DateUtil.today());
        tourismMapper.insert(tourism);
    }

    public void updateById(Tourism tourism) {
        tourismMapper.updateById(tourism);
    }

    public void deleteById(Integer id) {
        tourismMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            tourismMapper.deleteById(id);
        }
    }

    public Tourism selectById(Integer id) {
        return tourismMapper.selectById(id);
    }

    public List<Tourism> selectAll(Tourism tourism) {
        return tourismMapper.selectAll(tourism);
    }

    public PageInfo<Tourism> selectPage(Tourism tourism, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tourism> list = tourismMapper.selectAll(tourism);
        return PageInfo.of(list);
    }

}
