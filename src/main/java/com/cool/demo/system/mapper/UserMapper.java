package com.cool.demo.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cool.demo.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select sys_user.* from sys_user left join sys_dept on sys_user.dept_id = sys_dept.id where sys_dept.parent_id = 1")
    List<User> getUserByDept(Long deptId);

}
