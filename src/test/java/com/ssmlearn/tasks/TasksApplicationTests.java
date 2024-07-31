package com.ssmlearn.tasks;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssmlearn.tasks.generator.controller.PlateController;
import com.ssmlearn.tasks.generator.mapper.KindMapper;
import com.ssmlearn.tasks.generator.mapper.PlateMapper;
import com.ssmlearn.tasks.generator.mapper.TaskMapper;
import com.ssmlearn.tasks.generator.pojo.Kind;
import com.ssmlearn.tasks.generator.pojo.Plate;
import com.ssmlearn.tasks.generator.pojo.R;
import com.ssmlearn.tasks.generator.pojo.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class TasksApplicationTests {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    void contextLoads() {
        Task task = taskMapper.selectById(1);
        System.out.println("task = " + task);
    }

}

@SpringBootTest
class PlatesApplicationTests {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private PlateMapper plateMapper;
    @Autowired
    private KindMapper kindMapper;

    @Test
    void contextLoads() {
        String notice = "【教学运行】期末教学检查整改情况通报\n" +
                "各二级学院：\n" +
                "　　请查收以上通报，并继续加强教师培训与教学监督，进一步扎实开展各项教研活动，不断提高教学质量与教学规范性。\n" +
                "教务处  2024年7月10日";

        Pattern pattern = Pattern.compile("【(.*?)】(.*?)\\n");
        Matcher matcher = pattern.matcher(notice);
        if (matcher.find()) {
            String taskKind = matcher.group(1);
            String taskName = matcher.group(2);
            QueryWrapper<Plate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", taskKind);
            Plate plate = plateMapper.selectOne(queryWrapper);
            if (plate!=null) {
                QueryWrapper<Kind> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("plate_id", plate.getId());
                List<Kind> kinds = kindMapper.selectList(queryWrapper1);
                if (!kinds.isEmpty()) {
                    Kind kind = kinds.get(0);
                    Long kindId = Long.valueOf(kind.getId());
                    Task task = new Task();
                    task.setTypeId(kindId);
                    task.setName(taskName);
                    System.out.println(taskName);
                    System.out.println(taskKind);
                } else {
                    System.out.println("没有该板块的信息，请添加:" + taskKind);
                }
            } else {
                System.out.println("没有该板块的信息，请添加:" + taskName);
            }

        } else {
            System.out.println("没有匹配到板块名和任务名");
        }
    }
}