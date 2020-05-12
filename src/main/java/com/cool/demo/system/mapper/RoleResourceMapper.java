package com.cool.demo.system.mapper;

import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.entity.RoleResource;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    @Select("select sr.* from sys_resource sr left join sys_role_resource srr on srr.resource_id = sr.id left join sys_user su on su.role_id = srr.role_id where 1 = 1 and su.id = #{userId} and sr. level = 3 and sr.resource_id = #{fatherResourceId} and sr. status = 1 order by sr.sort")
    List<Resource> getMenuButtomResource(@Param("fatherResourceId") Long fatherResourceId, @Param("userId") Long userId);

}
