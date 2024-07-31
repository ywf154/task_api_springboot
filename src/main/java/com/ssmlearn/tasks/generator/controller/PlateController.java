package com.ssmlearn.tasks.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ssmlearn.tasks.generator.mapper.KindMapper;
import com.ssmlearn.tasks.generator.mapper.PlateMapper;
import com.ssmlearn.tasks.generator.pojo.Kind;
import com.ssmlearn.tasks.generator.pojo.Plate;
import com.ssmlearn.tasks.generator.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/plate")
public class PlateController {
    @Autowired
    private PlateMapper plateMapper;
    @Autowired
    private KindMapper kindMapper;


    @GetMapping()
    public R<List<Plate>> plateList(){
        List<Plate> plates = plateMapper.selectList(null);
        for (Plate plate:plates) {
            List<Kind> kinds = kindMapper.selectList(new LambdaQueryWrapper<Kind>()
                    .eq(Kind::getPlateId, plate.getId()));
            plate.setKinds(kinds);
        }
        return R.success(plates);
    }

    @PostMapping()
    public R addPlate(@RequestBody @Validated Plate plate){
        plateMapper.insert(plate);
        return R.success();
    }

    @PutMapping()
    public R updatePlate(@RequestBody @Validated Plate plate){
        if(plate.getId()==null){
            return R.error("id为空");
        }
        plateMapper.updateById(plate);
        return R.success();
    }

    @DeleteMapping()
    public R deletePlate(Integer id){
        if(plateMapper.selectById(id)==null){
            return R.error("板块不存在");
        }

        // 先删除关联的 Kind 实体
        List<Kind> kinds = kindMapper.selectList(new LambdaQueryWrapper<Kind>()
                .eq(Kind::getPlateId, id));
        if (kinds != null && !kinds.isEmpty()) {
            for (Kind kind : kinds) {
                kindMapper.deleteById(kind.getId());
            }
        }

        // 再删除 Plate 实体
        plateMapper.deleteById(id);

        return R.success();
    }
}
