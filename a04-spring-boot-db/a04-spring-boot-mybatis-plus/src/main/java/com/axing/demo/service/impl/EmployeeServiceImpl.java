package com.axing.demo.service.impl;

import com.axing.demo.domain.EmployeeEntity;
import com.axing.demo.mapper.EmployeeMapper;
import com.axing.demo.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xing
 * @description 针对表【employee】的数据库操作Service实现
 * @createDate 2022-12-17 20:22:09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity>
        implements EmployeeService {

    @Override
    public List<EmployeeEntity> leftDepartmentList(Wrapper wrapper) {
        return this.baseMapper.leftDepartment(wrapper);
    }

}




