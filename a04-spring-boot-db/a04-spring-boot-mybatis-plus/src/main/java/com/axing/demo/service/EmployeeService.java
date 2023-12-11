package com.axing.demo.service;

import com.axing.demo.domain.EmployeeEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author xing
 * @description 针对表【employee】的数据库操作Service
 * @createDate 2022-12-17 20:22:09
 */
public interface EmployeeService extends IService<EmployeeEntity> {

    List<EmployeeEntity> leftDepartmentList(Wrapper wrapper);
}
