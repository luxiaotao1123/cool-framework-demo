package com.cool.demo.common.web;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.controller.AbstractBaseController;
import com.core.exception.CoolException;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by vincent on 2019-09-09
 */
public class BaseController extends AbstractBaseController {

    protected static final String RANGE_TIME_LINK = " - ";

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private UserService userService;

    protected Long getUserId(){
        return Long.parseLong(String.valueOf(request.getAttribute("userId")));
    }

    protected User getUser(){
        User user = userService.selectById(getUserId());
        if (null == user) {
            throw new CoolException(BaseRes.DENIED);
        }
        return user;
    }

    protected String getComment(Class<?> cls, String fieldName){
        Field[] fields = Cools.getAllFields(cls);
        for (Field field : fields){
            if (fieldName.equals(field.getName())){
                return field.getAnnotation(ApiModelProperty.class).value();
            }
        }
        return "";
    }

    /**
     * 分页组装
     * @param pageNumber
     * @param pageSize
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    protected <T> Page<T> toPage(Integer pageNumber, Integer pageSize, Map<String, Object> map, Class<T> cls){
        // 分页索引和单页数量组装
        pageNumber = Optional.ofNullable(pageNumber).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(10);
        Page<T> page = new Page<>(pageNumber, pageSize);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);

        // 全字段模糊搜索 todo
        if (!Cools.isEmpty(map.get("condition"))) {
            Set<String> columns = new HashSet<>();
            for (Field field : Cools.getAllFields(cls)){
                if (Modifier.isFinal(field.getModifiers())
                        || Modifier.isStatic(field.getModifiers())
                        || Modifier.isTransient(field.getModifiers())){
                    continue;
                }
                String column = null;
                if (field.isAnnotationPresent(TableField.class)) {
                    column = field.getAnnotation(TableField.class).value();
                }
                if (Cools.isEmpty(column)) {
                    column = field.getName();
                }
                if (!map.keySet().contains(column)) {
                    columns.add(column);
                }
            }
            columns.forEach(col->map.put(col, map.get("condition")));
        }
        page.setCondition(map);
        return page;
    }

    /**
     * 全字段模糊搜索
     * @param cls 模型类
     * @param set 排除字段集合
     * @param condition 搜索内容
     */
    protected <T> void allLike(Class<T> cls, Set<String> set, EntityWrapper<T> wrapper, String condition){
        if (Cools.isEmpty(condition)) {
            return;
        }
        List<String> columns = new ArrayList<>();
        for (Field field :Cools.getAllFields(cls)){
            if (Modifier.isFinal(field.getModifiers())
                    || Modifier.isStatic(field.getModifiers())
                    || Modifier.isTransient(field.getModifiers())){
                continue;
            }
            String column = null;
            if (field.isAnnotationPresent(TableField.class)) {
                column = field.getAnnotation(TableField.class).value();
            }
            if (Cools.isEmpty(column)) {
                column = field.getName();
            }
            if (!set.contains(column)) {
                columns.add(column);
            }
        }
        if (columns.isEmpty()) {
            return;
        }
        for (int i=0;i<columns.size();i++){
            if (i==0){
                wrapper.andNew();
            } else {
                wrapper.or();
            }
            wrapper.like(columns.get(i), condition);
        }
    }
}
