package com.lsc.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.lsc.test.model.Test;
import com.lsc.test.model.Test3;
import com.lsc.test.mapper.Test3Mapper;
import com.lsc.test.service.ITest3Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class Test3ServiceImpl extends ServiceImpl<Test3Mapper, Test3> implements ITest3Service {

    @Autowired
    private Test3Mapper test3Mapper;

    @Override
    public IPage<Test3> selectMany(String name) {
        QueryWrapper<Test3> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("name",name);
        PageDTO<Test3> pageDTO = new PageDTO<>(0,10);

        return test3Mapper.selectPage(pageDTO,queryWrapper);
    }
}
