package com.goodpower.pvams.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.UserService;
import com.goodpower.pvams.util.JWTUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping("/queryUserInfo")
    public ResultMap queryUserInfo(Long userId){
        ResultMap resultMap = new ResultMap();
        try{
            if(userId == null){
                resultMap.fail().message("userId不能为空");
            }
            User user =  userService.findUserById(userId);
            return resultMap.success().setData(user);
        }catch (Exception e){
            logger.error("查询用户信息失败",e);
            return resultMap.fail().code(400).message("查询用户信息失败");
        }
    }

    @PostMapping("/add")
    //@RequiresPermissions(logical = Logical.OR, value = {"addUser"})
    public ResultMap add(@RequestBody User user){
        ResultMap resultMap = new ResultMap();
        try{
            if(user.getUserType() == null){
                return resultMap.fail().code(400).message("用戶类型不能为空");
            }
            if(StringUtils.isBlank(user.getUsername())){
                return resultMap.fail().code(400).message("用戶名不能为空");
            }
            if(!checkUserNameNew(user.getUsername())){
                return resultMap.fail().code(400).message("用戶名重复");
            }
            if(StringUtils.isBlank(user.getRealname())){
                return resultMap.fail().code(400).message("用戶真实姓名不能为空");
            }
            if(StringUtils.isBlank(user.getPassword())){
                return resultMap.fail().code(400).message("用戶密码不能为空");
            }
            if(StringUtils.isBlank(user.getMail())){
                return resultMap.fail().code(400).message("邮箱不能为空");
            }
            if(StringUtils.isBlank(user.getPhone())){
                return resultMap.fail().code(400).message("联系方式不能为空");
            }
            userService.add(user);
            return resultMap.success().code(200).message("添加用户成功");
        }catch (Exception e){
            logger.error("添加用户失败",e);
            return resultMap.fail().code(400).message("添加用户失败");
        }

    }

    @PostMapping("/update")
    //@RequiresPermissions(logical = Logical.OR, value = {"updateUser"})
    public ResultMap update(@RequestBody User user){
        ResultMap result = new ResultMap();
        try{
            userService.update(user);
            return result.success().code(200).message("更新用户成功");
        }catch (Exception e){
            logger.error("添加用户失败",e);
            return result.success().code(400).message("更新用户失败");
        }
    }

    @PostMapping("/delete")
    public ResultMap delete(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            JSONArray jsonArray = request.getJSONArray("idList");
            List<Long> idList = Lists.newArrayList();
            for(Object id : jsonArray){
                if(id != null && !"".equals(id)){
                    idList.add(Long.parseLong(id.toString()));
                }
            }
            userService.delete(idList);
            return result.success().code(200).message("删除用户成功");
        }catch (Exception e){
            logger.error("添加用户失败",e);
            return result.success().code(400).message("删除用户失败");
        }
    }

    static Map<Integer,String> roleMap = Maps.newHashMap();
    static {
        roleMap.put(1,"超级管理员");
        roleMap.put(2,"企业管理员");
        roleMap.put(3,"企业运维人员");
        roleMap.put(4,"企业普通用户");
        roleMap.put(5,"电站管理员");
        roleMap.put(6,"电站运维人员");
        roleMap.put(7,"电站普通用户");
    }

    @GetMapping("/query")
    public ResultMap query(@RequestParam Integer userType,
                           @RequestParam Long unitId,
                           @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                           @RequestParam(required = false,defaultValue = "20") Integer pageSize){
        ResultMap result = new ResultMap();
        try{
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("userType",userType);
            param.put("unitId",unitId);
            List<User> userList = userService.find(param);
            for(User user : userList){
                user.setRoleName(roleMap.get(user.getRole()));
            }
            Integer count = userService.getCount(param);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userList",userList);
            jsonObject.put("count",count);
            return result.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            logger.error("查询用户信息失败",e);
            return result.fail().message("查询失败");
        }
    }

    @GetMapping("/checkUserName")
    public ResultMap checkUserName(@RequestParam String username){
        ResultMap result = new ResultMap();
        boolean useable = false;
        try{
            List<User> userList = userService.checkUserName(username);
            if(userList.size() == 0){
                useable = true;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("useable",useable);
            return result.setData(jsonObject).success().message("校验成功");
        }catch (Exception e){
            logger.error("校验失败",e);
            return result.fail().message("校验失败");
        }
    }

    public ResultMap checkUserProp(User user){
        ResultMap resultMap = new ResultMap();
        if(user.getUserType() == null){
            return resultMap.fail().code(400).message("用戶类型不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())){
            return resultMap.fail().code(400).message("用戶名不能为空");
        }
        if(!checkUserNameNew(user.getUsername())){
            return resultMap.fail().code(400).message("用戶名重复");
        }
        if(StringUtils.isBlank(user.getRealname())){
            return resultMap.fail().code(400).message("用戶真实姓名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return resultMap.fail().code(400).message("用戶密码不能为空");
        }
        if(StringUtils.isBlank(user.getMail())){
            return resultMap.fail().code(400).message("邮箱不能为空");
        }
        if(StringUtils.isBlank(user.getPhone())){
            return resultMap.fail().code(400).message("联系方式不能为空");
        }
        return resultMap;
    }

    public boolean checkUserNameNew(String username){
        List<User> userList = userService.checkUserName(username);
        if(userList.size() == 0){
            return true;
        }
        return false;
    }

    @GetMapping("/getRole")
    public ResultMap getRole(HttpServletRequest request,Integer type, Integer userType){
        ResultMap result = new ResultMap();
        try{
            String token = request.getHeader("Token");
            String userName = JWTUtil.getUsername(token);
            User user = userService.findUserByUsername(userName);
            if(user == null){
                return result.fail().message("未找到该用户信息");
            }
            List<Map<String,Object>> dataList = userService.getAddRole(user.getRole(),userType);
            if(dataList == null ||dataList.isEmpty()){
                Map<String,Object> map = Maps.newHashMap();
                map.put("role",user.getRole());
                map.put("name",roleMap.get(user.getRole()));
                dataList.add(map);
            }
            JSONObject resultList = new JSONObject();
            resultList.put("resultList",dataList);
            return result.setData(resultList).success().message("查询成功");
        }catch (Exception e){
            logger.error("查询角色失败",e);
            return result.fail().message("查询角色失败");
        }
    }

}
