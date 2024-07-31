package com.ssmlearn.tasks.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssmlearn.tasks.generator.mapper.TaskMapper;
import com.ssmlearn.tasks.generator.service.TaskService;
import com.ssmlearn.tasks.generator.pojo.Task;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【TaskList_task】的数据库操作Service实现
* @createDate 2024-07-23 14:06:56
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService {

}




