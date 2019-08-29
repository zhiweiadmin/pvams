package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Menu;
import com.goodpower.pvams.model.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface MenuMapper {
    int insert(TreeNode record);

    int insertSelective(TreeNode record);

    List<TreeNode> queryMenuList();

    List<TreeNode> getChildList(String pId);
}