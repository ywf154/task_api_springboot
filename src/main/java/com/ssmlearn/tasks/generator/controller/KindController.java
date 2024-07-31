package com.ssmlearn.tasks.generator.controller;

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
@RequestMapping("/kind")
public class KindController {
    @Autowired
    private KindMapper kindMapper;
    @Autowired
    private PlateMapper plateMapper;


//    @GetMapping()
//    public R<List<Kind>> kindList(){
//        List<Kind> Lk = kindMapper.selectList(null);
//        return R.success(Lk);
//    }

    @GetMapping()
    public R<List<Kind>> kindList() {
        List<Kind> kinds = kindMapper.selectList(null);
        for (Kind kind : kinds) {
            Plate plate = plateMapper.selectById(kind.getPlateId());
            kind.setPlate(plate);
        }
        return R.success(kinds);
    }

    @PostMapping()
    public R addKind(@RequestBody @Validated Kind kind){
        kindMapper.insert(kind);
        return R.success();
    }

    @PutMapping()
    public R updateKind(@RequestBody @Validated Kind kind){
        if(kind.getId()==null){
            return R.error("id为空");
        }
        kindMapper.updateById(kind);
        return R.success();
    }

    @DeleteMapping()
    public R deleteKind(Integer id){
        if(kindMapper.selectById(id)==null){
            return R.error("种类不存在");
        }
        kindMapper.deleteById(id);
        return R.success();
    }

}
