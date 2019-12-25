package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.TaskMapper;
import com.cool.demo.manager.entity.Task;
import com.cool.demo.manager.service.TaskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

}
