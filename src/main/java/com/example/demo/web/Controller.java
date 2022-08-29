package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/json")
    public static HashMap<String, Object> success(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");     //正确码
        res.put("msg", "");                             //备注消息
        res.put("data", data);                          //数据
        return res;
    }

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @RequestMapping(value = "/getUsers1",method = RequestMethod.GET)
//    @ResponseBody
//    /*
//     * List 里的对象是Map对象，而Map对象是
//     * 由一个String类型的键和Object类型的值组成
//     * */
//    public List<Map<String,Object>> getUsers(){
//        String sql="select * from user";//SQL查询语句
//        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
//        return list;
//    }
}
