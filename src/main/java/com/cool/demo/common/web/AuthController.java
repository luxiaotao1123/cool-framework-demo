package com.cool.demo.common.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cool.demo.common.CodeRes;
import com.cool.demo.common.model.PowerDto;
import com.cool.demo.common.utils.RandomValidateCodeUtil;
import com.cool.demo.system.entity.*;
import com.cool.demo.system.service.*;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by vincent on 2019-07-30
 */
@RestController
public class AuthController extends BaseController {

    @Value("${super.pwd}")
    private String superPwd;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping("/login.action")
    public R loginAction(String mobile, String password){
        if (mobile.equals("super") && password.equals(Cools.md5(superPwd))) {
            Map<String, Object> res = new HashMap<>();
            res.put("username", mobile);
            res.put("token", Cools.enToken(System.currentTimeMillis() + mobile, superPwd));
            return R.ok(res);
        }
        EntityWrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper.eq("mobile", mobile);
        User user = userService.selectOne(userWrapper);
        if (Cools.isEmpty(user)){
            return R.parse(CodeRes.USER_10001);
        }
        if (user.getStatus()!=1){
            return R.parse(CodeRes.USER_10002);
        }
        if (!Cools.md5(user.getPassword()).equals(password)){
            return R.parse(CodeRes.USER_10003);
        }
        String token = Cools.enToken(System.currentTimeMillis() + mobile, user.getPassword());
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

    @RequestMapping("/code.action")
    public void code(@RequestParam String sd, HttpServletResponse response) {
        RandomValidateCodeUtil.getRandcode(sd, response);
    }

    @RequestMapping("/code.do")
    public String codeDo(@RequestParam String sd) {
        String code = RandomValidateCodeUtil.code.get(sd);
        RandomValidateCodeUtil.code.remove(sd);
        return code;
    }

    @RequestMapping("/user/detail/auth")
    @ManagerAuth
    public R userDetail(){
        return R.ok(userService.selectById(getUserId()));
    }

    @RequestMapping("/menu/auth")
    @ManagerAuth
    public R menu(){
        // 获取所有一级菜单
        List<Resource> oneLevel = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 1).eq("status", 1).orderBy("sort"));
        User user = null;
        Wrapper<Resource> resourceWrapper;
        if (getUserId() == 9527) {
            resourceWrapper = new EntityWrapper<Resource>().eq("level", 2).eq("status", 1).orderBy("sort");
        } else {
            // 获取当前用户的所有二级菜单
            user = userService.selectById(getUserId());
            List<RoleResource> roleResources = roleResourceService.selectList(new EntityWrapper<RoleResource>().eq("role_id", user.getRoleId()));
            List<Long> resourceIds = new ArrayList<>();
            roleResources.forEach(roleResource -> resourceIds.add(roleResource.getResourceId()));
            if (resourceIds.isEmpty()){
                return R.ok();
            }
            resourceWrapper = new EntityWrapper<Resource>().in("id", resourceIds).eq("level", 2).eq("status", 1).orderBy("sort");
        }
        List<Resource> twoLevel = resourceService.selectList(resourceWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Resource menu : oneLevel) {
            Map<String, Object> map = new HashMap<>();
            List<Resource> subMenu = new ArrayList<>();
            Iterator<Resource> iterator = twoLevel.iterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (resource.getResourceId().equals(menu.getId())) {

                    // 是否拥有查看权限
                    if (getUserId() != 9527) {
                        Resource view = resourceService.selectOne(new EntityWrapper<Resource>().eq("resource_id", resource.getId()).like("code", "view"));
                        if (!Cools.isEmpty(view)){
                            RoleResource param = new RoleResource();
                            param.setResourceId(view.getId());
                            param.setRoleId(user.getId());
                            if (null == roleResourceService.selectOne(new EntityWrapper<>(param))){
                                continue;
                            }
                        }
                    }

                    subMenu.add(resource);
                    iterator.remove();
                }
            }
            if (subMenu.isEmpty()) {
                continue;
            }
            map.put("menuId", menu.getId());
            map.put("menuCode", menu.getCode());
            map.put("menu", menu.getName());
            map.put("subMenu", subMenu);
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping("/power/list/auth")
    @ManagerAuth
    public R powerList(){
        List<Resource> oneLevels = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 1).eq("status", 1).orderBy("sort"));
        List<Map> result = new ArrayList<>();
        // 一级
        for (Resource oneLevel : oneLevels){
            List<Map> twoLevelsList = new ArrayList<>();
            Map<String, Object> oneLevelMap = new HashMap<>();
            oneLevelMap.put("title", oneLevel.getName());
            oneLevelMap.put("id", oneLevel.getCode());
            oneLevelMap.put("spread", true);
            oneLevelMap.put("children", twoLevelsList);
            List<Resource> twoLevels = resourceService.selectList(new EntityWrapper<Resource>().eq("resource_id", oneLevel.getId()).eq("level", 2).eq("status", 1).orderBy("sort"));
            // 二级
            for (Resource twoLevel : twoLevels){
                Map<String, Object> twoLevelMap = new HashMap<>();
                twoLevelMap.put("title", twoLevel.getName());
                twoLevelMap.put("id", twoLevel.getCode());
                twoLevelMap.put("spread", false);

                List<Map> threeLevelsList = new ArrayList<>();
                twoLevelMap.put("children", threeLevelsList);
                // 三级
                List<Resource> threeLevels = resourceService.selectList(new EntityWrapper<Resource>().eq("resource_id", twoLevel.getId()).eq("level", 3).eq("status", 1).orderBy("sort"));
                for (Resource threeLevel : threeLevels){
                    Map<String, Object> threeLevelMap = new HashMap<>();
                    threeLevelMap.put("title", threeLevel.getName());
                    threeLevelMap.put("id", threeLevel.getCode());
                    threeLevelMap.put("checked", false);
                    threeLevelsList.add(threeLevelMap);
                }

                twoLevelsList.add(twoLevelMap);
            }
            result.add(oneLevelMap);
        }

        // 功能模块
        Map<String, Object> functions = new HashMap<>();
        functions.put("title", "指定功能");
        functions.put("id", "function");
        functions.put("spread", true);
        List<Map> funcs = new ArrayList<>();
        functions.put("children", funcs);
        List<Permission> permissions = permissionService.selectList(new EntityWrapper<Permission>().eq("status", 1));
        for (Permission permission : permissions) {
            Map<String, Object> func = new HashMap<>();
            func.put("title", permission.getName());
            func.put("id", permission.getAction());
            func.put("spread", true);
            funcs.add(func);
        }
        result.add(functions);

        return R.ok(result);
    }

    @RequestMapping(value = "/power/{roleId}/auth")
    @ManagerAuth
    public R get(@PathVariable("roleId") Long roleId) {
        List<String> result = new ArrayList<>();
        // 菜单
        List<RoleResource> roleResources = roleResourceService.selectList(new EntityWrapper<RoleResource>().eq("role_id", roleId));
        for (RoleResource roleResource : roleResources){
            Resource resource = resourceService.selectById(roleResource.getResourceId());
            if (!Cools.isEmpty(resource)){
                if (resource.getLevel() == 3){
                    result.add(resource.getCode());
                }
            }
        }
        // 功能
        List<RolePermission> rolePermissions = rolePermissionService.selectList(new EntityWrapper<RolePermission>().eq("role_id", roleId));
        for (RolePermission rolePermission : rolePermissions){
            Permission permission = permissionService.selectById(rolePermission.getPermissionId());
            if (!Cools.isEmpty(permission)){
                result.add(permission.getAction());
            }
        }
        return R.ok(result);
    }

    @RequestMapping("/power/auth")
    @ManagerAuth
    public R power(Long roleId, String powers){
        roleResourceService.delete(new EntityWrapper<RoleResource>().eq("role_id", roleId));
        rolePermissionService.delete(new EntityWrapper<RolePermission>().eq("role_id", roleId));
        if (!Cools.isEmpty(powers)){
            List<PowerDto> dtos = JSON.parseArray(powers, PowerDto.class);
            for (PowerDto dto : dtos) {
                Resource resource = resourceService.selectOne(new EntityWrapper<Resource>().eq("code", dto.getTwo()).eq("level", 2));
                if (!Cools.isEmpty(resource)) {
                    RoleResource roleResource = new RoleResource();
                    roleResource.setRoleId(roleId);
                    roleResource.setResourceId(resource.getId());
                    roleResourceService.insert(roleResource);
                } else {
                    Permission permission = permissionService.selectOne(new EntityWrapper<Permission>().eq("action", dto.getTwo()));
                    if (!Cools.isEmpty(permission)){
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setPermissionId(permission.getId());
                        rolePermissionService.insert(rolePermission);
                    }
                }
                for (String three : dto.getThree()){
                    Resource resource1 = resourceService.selectOne(new EntityWrapper<Resource>().eq("code", three).eq("level", 3));
                    if (!Cools.isEmpty(three)) {
                        RoleResource roleResource = new RoleResource();
                        roleResource.setRoleId(roleId);
                        roleResource.setResourceId(resource1.getId());
                        roleResourceService.insert(roleResource);
                    }
                }
            }
        }
        return R.ok();
    }

    @RequestMapping(value = "/power/menu/{resourceId}/auth")
    @ManagerAuth
    public R buttonResource(@PathVariable("resourceId") Long resourceId) {
        List<Resource> resources;
        if (getUserId() == 9527) {
            resources = resourceService.selectList(new EntityWrapper<Resource>().eq("level", 3).eq("resource_id", resourceId));
        } else {
            resources = roleResourceService.getMenuButtomResource(resourceId, getUserId());
        }
        for (Resource resource : resources) {
            resource.setCode(resource.getCode().split("#")[1]);
        }
        return R.ok(resources);
    }


}
