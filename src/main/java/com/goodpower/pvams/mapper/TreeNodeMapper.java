package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TreeNode;

public interface TreeNodeMapper {
    int insert(TreeNode record);

    int insertSelective(TreeNode record);
}