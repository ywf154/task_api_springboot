package com.ssmlearn.tasks.generator.controller;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ssmlearn.tasks.generator.mapper.KindMapper;
import com.ssmlearn.tasks.generator.mapper.PlateMapper;
import com.ssmlearn.tasks.generator.mapper.TaskMapper;
import com.ssmlearn.tasks.generator.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskMapper TM;
    @Autowired
    private KindMapper kindMapper;
    @Autowired
    private PlateMapper plateMapper;

    @GetMapping("/all")
    public R<List<Task>> tasksList() {
        List<Task> tasks = TM.selectList(new LambdaQueryWrapper<Task>()
                .orderByDesc(Task::getAddTime));
        for (Task task : tasks) {
            Kind kind = kindMapper.selectById(task.getTypeId());
            task.setKind(kind);
        }
        return R.success(tasks);
    }

    @GetMapping()
    public R<List<Task>> tasks0List() {
        List<Task> tasks = TM.selectList(new LambdaQueryWrapper<Task>()
                .eq(Task::getStatus, false)
                .orderByDesc(Task::getAddTime));

        for (Task task : tasks) {
            Kind kind = kindMapper.selectById(task.getTypeId());
            Plate plate = plateMapper.selectById(kind.getPlateId());
            task.setKind(kind);
            task.setPlate(plate);
        }
        return R.success(tasks);
    }

    @PostMapping()
    public R addTask(@RequestBody @Validated Task Task) {
        TM.insert(Task);
        return R.success();
    }

    @PutMapping()
    public R updateTask(@RequestBody @Validated Task Task) {
        if (Task.getId() == null) {
            return R.error("id为空");
        }
        TM.updateById(Task);
        return R.success();
    }

    @DeleteMapping()
    public R deleteTask(Integer id) {
        if (TM.selectById(id) == null) {
            return R.error("任务不存在");
        }
        TM.deleteById(id);
        return R.success();
    }

    @GetMapping("/f")
    public R finishTask(Integer id) {
        Task task = TM.selectById(id);
        if (task == null) {
            return R.error("任务不存在");
        }
        task.setStatus(true);
        task.setFinishTime(new Date());
        TM.updateById(task);
        return R.success();
    }

    @GetMapping("/nf")
    public R unfinishTask(Integer id) {
        Task task = TM.selectById(id);
        if (task == null) {
            return R.error("任务不存在");
        }
        if (!task.getStatus()) {
            return R.error("任务状态：未完成");
        }
        UpdateWrapper<Task> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("finish_time", null).set("status", false);
        TM.update(updateWrapper);
        return R.success();
    }


    @GetMapping("/t")
    public R<Task> getATaskById(Integer id) {
        Task task = TM.selectById(id);
        if (task == null) {
            return R.error("任务不存在");
        }
        return R.success(task);
    }

    @PostMapping("/notice")
    public R noticeAddTask(@RequestBody Map<String, Object> notice ) {
        String text = (String) notice.get("text");
        Pattern pattern = Pattern.compile("【(.*?)】(.*?)\\s");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String taskKind = matcher.group(1);
            String taskName = matcher.group(2).trim();
            QueryWrapper<Plate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", taskKind);
            List<Plate> plates = plateMapper.selectList(queryWrapper);

            if (!plates.isEmpty()) {
                Plate plate = plates.get(0);
                QueryWrapper<Kind> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("plate_id", plate.getId());
                List<Kind> kinds = kindMapper.selectList(queryWrapper1);
                if (!kinds.isEmpty()) {
                    Kind kind = kinds.get(0);
                    Long kindId = Long.valueOf(kind.getId());
                    Task task = new Task();
                    task.setTypeId(kindId);
                    task.setName(taskName);
                    task.setWxNoticeFrom(text);
                    TM.insert(task);
                    return R.success();
                } else {
                    return R.error("没有该板块的种类信息，请添加:" + taskKind);
                }
            } else {
                return R.error("没有该板块的信息，请添加:" + taskKind);
            }

        } else {
            return R.error("没有匹配到板块名和任务名");
        }
    }
}
