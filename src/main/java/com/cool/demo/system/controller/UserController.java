package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.MapConvert;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String index(){
        return "user/user";
    }

    @RequestMapping("/user_detail")
    public String detail(){
        return "user/user_detail";
    }

    @RequestMapping(value = "/user/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(userService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/user/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  User user){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.setEntity(user);
        return R.ok(userService.selectPage(new Page<>(curr, limit), wrapper));
    }

    @RequestMapping(value = "/user/edit/auth")
    @ResponseBody
    public R edit(User user) {
        if (Cools.isEmpty(user)){
            return R.error();
        }
        if (null == user.getId()){
            userService.insert(user);
        } else {
            userService.updateById(user);
        }
        return R.ok();
    }

    @RequestMapping(value = "/user/add/auth")
    @ResponseBody
    public R add(User user) {
        userService.insert(user);
        return R.ok();
    }

	@RequestMapping(value = "/user/update/auth")
    @ResponseBody
    public R update(User user){
        if (Cools.isEmpty(user) || null==user.getId()){
            return R.error();
        }
        userService.updateById(user);
        return R.ok();
    }

    @RequestMapping(value = "/user/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/user/export/auth", method = RequestMethod.POST)
    @ResponseBody
    public R export(@RequestBody JSONObject param) throws InvocationTargetException, IllegalAccessException {
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
//        ListIterator<String> fieldsIterator = fields.listIterator();
//        while (fieldsIterator.hasNext()){
//            String field = fieldsIterator.next();
//            if (field.endsWith("$")){
//                fieldsIterator.set(field.substring(0, field.length() - 1));
//            }
//        }
//
//        EntityWrapper<User> wrapper = new EntityWrapper<>();
//        wrapper.setEntity(JSON.parseObject(param.getJSONObject("user").toJSONString(), User.class));
//        List<Map<String, Object>> list = userService.selectMaps(wrapper);
//        List<List<Object>> result = new ArrayList<>();
//        for (Map<String, Object> map : list){
//            JSONObject jsonObject = new JSONObject(map);
//            User user = JSON.toJavaObject(jsonObject, User.class);
//            Map map1 = JSON.parseObject(JSON.toJSONString(user), Map.class);
//            Iterator<Map.Entry<String, Object>> iterator = map1.entrySet().iterator();
//            List<Object> node = new ArrayList<>();
//            while (iterator.hasNext()){
//                Map.Entry<String, Object> entry = iterator.next();
//                if (!fields.contains(entry.getKey())){
//                    iterator.remove();
//                    continue;
//                }
//                node.add(entry.getValue());
//            }
//            result.add(node);
//        }
//        return R.ok(result);

//        EntityWrapper<User> wrapper = new EntityWrapper<>();
//        wrapper.setEntity(JSON.parseObject(param.getJSONObject("user").toJSONString(), User.class));
//        List<User> list = userService.selectList(wrapper);
//        List<List<Object>> result = new ArrayList<>();
//        for (User user : list){
//            LinkedHashMap map = JSON.parseObject(JSON.toJSONString(user), LinkedHashMap.class);
//            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
//            List<Object> node = new ArrayList<>();
//            while (iterator.hasNext()){
//                Map.Entry<String, Object> entry = iterator.next();
//                if (!fields.contains(entry.getKey())){
//                    iterator.remove();
//                    continue;
//                }
//                node.add(entry.getValue());
//            }
//            result.add(node);
//        }
//        return R.ok(result);


        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.setEntity(JSON.parseObject(param.getJSONObject("user").toJSONString(), User.class));
        List<User> list = userService.selectList(wrapper);
        List<List<Object>> result = new ArrayList<>();
        Method[] methods = list.get(0).getClass().getMethods();
        for (User user : list){
            List<Object> node = new ArrayList<>();
            for (String field : fields){
                for (Method method : methods) {
                    if (("get" + field).toLowerCase().equals(method.getName().toLowerCase())) {
                        Object val = method.invoke(user);
                        node.add(val);
                        break;
                    }
                }
            }
            result.add(node);
        }
        return R.ok(result);
    }

}
