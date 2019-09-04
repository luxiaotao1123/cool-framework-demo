package com.cool.demo.common.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.common.CodeRes;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.ResourceService;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by vincent on 2019-07-30
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/login.action")
    public R loginAction(String username, String password){
        EntityWrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper.eq("mobile", username);
        User user = userService.selectOne(userWrapper);
        if (Cools.isEmpty(user)){
            return R.parse(CodeRes.USER_10001);
        }
        if (user.getStatus()!=1){
            return R.parse(CodeRes.USER_10002);
        }
        if (!user.getPassword().equals(password)){
            return R.parse(CodeRes.USER_10003);
        }
        String token = Cools.enToken(System.currentTimeMillis() + username, password);
        userLoginService.delete(new EntityWrapper<UserLogin>().eq("user_id", user.getId()));
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(user.getId());
        userLogin.setToken(token);
        userLoginService.insert(userLogin);
        return R.ok(token);
    }

    @RequestMapping("/menu/auth")
    public R menu(){
        List<Resource> oneLevel = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 1).eq("status", 1));
        List<Resource> twoLevel = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 2).eq("status", 1));
        Map<String, String> pNames = new HashMap<>();
        oneLevel.forEach(resource -> pNames.put(resource.getCode(), resource.getName()));
        List<Map<String, Object>> result = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (Resource resource : twoLevel){
            String pCode = resource.getPcode();
            if (set.contains(pCode)){
                for (Map<String, Object> map : result){
                    if (map.get("menu").equals(pNames.get(pCode))){
                        @SuppressWarnings("unchecked")
                        List<Resource> subMenu = (List<Resource>) map.get("subMenu");
                        subMenu.add(resource);
                    }
                }
            } else {
                set.add(pCode);
                Map<String, Object> map = new HashMap<>();
                List<Resource> subMenu = new ArrayList<>();
                subMenu.add(resource);
                map.put("menu", pNames.get(resource.getPcode()));
                map.put("subMenu", subMenu);
                result.add(map);
            }
        }
        return R.ok(result);
    }

}
