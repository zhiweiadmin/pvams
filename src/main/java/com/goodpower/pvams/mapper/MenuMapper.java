package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Menu;
import com.goodpower.pvams.model.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface MenuMapper {
    int insert(TreeNode record);

    void updateCompanyName(Map<String,Object> param);

    int insertSelective(TreeNode record);

    List<TreeNode> queryMenuList();

    List<TreeNode> getChildList(String pId);
}