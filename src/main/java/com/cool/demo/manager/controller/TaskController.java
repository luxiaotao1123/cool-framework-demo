package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Task;
import com.cool.demo.manager.service.TaskService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TaskController extends AbstractBaseController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/task")
    public String index(){
        return "task/task";
    }

    @RequestMapping("/task_detail")
    public String detail(){
        return "task/task_detail";
    }

    @RequestMapping(value = "/task/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(taskService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/task/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Task> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(taskService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            if (entry.getKey().endsWith(">")) {
                wrapper.ge(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else if (entry.getKey().endsWith("<")) {
                wrapper.le(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else {
                wrapper.like(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    @RequestMapping(value = "/task/edit/auth")
    @ResponseBody
    public R edit(Task task) {
        if (Cools.isEmpty(task)){
            return R.error();
        }
        if (null == task.getId()){
            taskService.insert(task);
        } else {
            taskService.updateById(task);
        }
        return R.ok();
    }

    @RequestMapping(value = "/task/add/auth")
    @ResponseBody
    public R add(Task task) {
        taskService.insert(task);
        return R.ok();
    }

	@RequestMapping(value = "/task/update/auth")
    @ResponseBody
    public R update(Task task){
        if (Cools.isEmpty(task) || null==task.getId()){
            return R.error();
        }
        taskService.updateById(task);
        return R.ok();
    }

    @RequestMapping(value = "/task/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        taskService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/task/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Task> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("task"));
        convert(map, wrapper);
        List<Task> list = taskService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/taskQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<Task> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Task> page = taskService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Task task : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("value", task.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
