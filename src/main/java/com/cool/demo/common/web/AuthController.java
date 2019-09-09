package com.cool.demo.common.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.common.BaseController;
import com.cool.demo.common.CodeRes;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.entity.RoleResource;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.ResourceService;
import com.cool.demo.system.service.RoleResourceService;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by vincent on 2019-07-30
 */
@RestController
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;

    @RequestMapping("/login.action")
    public R loginAction(String mobile, String password){
        EntityWrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper.eq("mobile", mobile);
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
        String token = Cools.enToken(System.currentTimeMillis() + mobile, password);
        userLoginService.delete(new EntityWrapper<UserLogin>().eq("user_id", user.getId()));
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(user.getId());
        userLogin.setToken(token);
        userLoginService.insert(userLogin);
        Map<String, Object> res = new HashMap<>();
        res.put("username", user.getUsername());
        res.put("token", token);
        return R.ok(res);
    }

    @RequestMapping("/menu/auth")
    public R menu(){
        User user = userService.selectById(getUserId());
        List<Resource> oneLevel = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 1).eq("status", 1));
        List<RoleResource> roleResources = roleResourceService.selectList(new EntityWrapper<RoleResource>().eq("role_id", user.getRoleId()));
        List<Long> resourceIds = new ArrayList<>();
        roleResources.forEach(roleResource -> resourceIds.add(roleResource.getResourceId()));
        if (resourceIds.isEmpty()){
            return R.ok();
        }
        List<Resource> twoLevel = resourceService.selectList(new EntityWrapper<Resource>().in("id", resourceIds).eq("level", 2).eq("status", 1));
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

    @RequestMapping("/power/list/auth")
    public R powerList(){
        List<Resource> oneLevels = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 1).eq("status", 1));
        List<Map> result = new ArrayList<>();
        for (Resource oneLevel : oneLevels){
            Map<String, Object> oneLevelMap = new HashMap<>();
            oneLevelMap.put("title", oneLevel.getName());
            oneLevelMap.put("id", oneLevel.getCode());
            oneLevelMap.put("spread", true);
            List<Resource> twoLevels = resourceService.selectList(new EntityWrapper<Resource>().eq("pcode", oneLevel.getCode()).eq("status", 1));
            List<Map> twoLevelsList = new ArrayList<>();
            oneLevelMap.put("children", twoLevelsList);
            for (Resource twoLevel : twoLevels){
                Map<String, Object> twoLevelMap = new HashMap<>();
                twoLevelMap.put("title", twoLevel.getName());
                twoLevelMap.put("id", twoLevel.getCode());
                oneLevelMap.put("spread", true);
                twoLevelsList.add(twoLevelMap);
            }
            result.add(oneLevelMap);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/power/{roleId}/auth")
    public R get(@PathVariable("roleId") Long roleId) {
        List<RoleResource> roleResources = roleResourceService.selectList(new EntityWrapper<RoleResource>().eq("role_id", roleId));
        List<String> result = new ArrayList<>();
        for (RoleResource roleResource : roleResources){
            Resource resource = resourceService.selectById(roleResource.getResourceId());
            if (resource.getLevel() == 2){
                result.add(resource.getCode());
            }
        }
        return R.ok(result);
    }

    @RequestMapping("/power/auth")
    public R power(Long roleId, String[] powers){
        roleResourceService.delete(new EntityWrapper<RoleResource>().eq("role_id", roleId));
        if (!Cools.isEmpty(powers)){
            for (String power : powers) {
                Resource resource = resourceService.selectOne(new EntityWrapper<Resource>().eq("code", power));
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resource.getId());
                roleResourceService.insert(roleResource);
            }
        }
        return R.ok();
    }

}