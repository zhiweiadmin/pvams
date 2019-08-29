package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.UserMapper;
import com.goodpower.pvams.model.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findUserById(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    public List<User> find(Map<String,Object> map){
        return userMapper.selectByFields(map);
    }

    public Integer getCount(Map<String,Object> map){
        return userMapper.getCount(map);
    }

    public User findUserByUsername(String username){
        return userMapper.selectByUsername(username);}

    public List<String> getRolePermission(User user){
        List<String>  permissions = Lists.newArrayList();
        List<Map<String,Object>> permissionList = Lists.newArrayList();
        if(user.getRole() == 1){
            permissionList = userMapper.getRolePermissionByRole(null);
        }else{
            Integer role = user.getRole();
            permissionList = userMapper.getRolePermissionByRole(role);
        }

        for(Map<String,Object> map : permissionList){
            permissions.add((String)map.get("permission"));
        }
        return permissions;
    }

    public void delete(List<Long> userIdList){
        if(userIdList != null && !userIdList.isEmpty()){
            userMapper.batchDelete(userIdList);
        }
    }

    public void add(User user){
        Date createDate = new Date();
        user.setCreateDttm(createDate);
        user.setUpdateDttm(createDate);
        userMapper.insert(user);
    }

    public void update(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> checkUserName(String username){
        Map<String,Object> param = Maps.newHashMap();
        param.put("username",username);
        return userMapper.selectByFields(param);
    }

}
