package com.lsc.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.lsc.test.model.Test;
import com.lsc.test.mapper.TestMapper;
import com.lsc.test.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Autowired
    private TestMapper testMapper;


    @Override
    public Test getOne(long id) {
        return testMapper.selectById(id);
    }

    @Override
    public IPage<Test> selectMany() {
        QueryWrapper<Test> queryWrapper =new QueryWrapper<>();
        queryWrapper.le("a",100).ge("a",95);
        PageDTO<Test> pageDTO = new PageDTO<>(0,1);

        return testMapper.selectPage(pageDTO,queryWrapper);
    }
}
