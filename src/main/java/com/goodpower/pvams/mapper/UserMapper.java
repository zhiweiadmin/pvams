package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map<String,Object>> getRolePermission(String username);

    List<Map<String,Object>> getRolePermissionByRole(@Param("role") Integer role);

    List<User> selectByFields(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    void batchDelete(@Param("list") List<Long> userIdList);
}