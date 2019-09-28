package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.MenuMapper;
import com.goodpower.pvams.model.Menu;
import com.goodpower.pvams.model.TreeNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    public JSONObject getTreeNode(String id){
        List<TreeNode> rootMenu = menuMapper.queryMenuList();
        // 最后的结果
        List<TreeNode> menuList = Lists.newArrayList();
        for(TreeNode rootNode : rootMenu){
            if(StringUtils.isNotBlank(id)){
                if(id.equals(rootNode.getId())){
                    menuList.add(rootNode);
                    break;
                }
            }else{
                if(rootNode.getpId() == null){
                    menuList.add(rootNode);
                }
            }
        }
        List<TreeNode> menuListNew = Lists.newArrayList();
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TreeNode menu : menuList) {
            menu.setChildNodeList(getChild(menu.getId(), rootMenu));
            TreeNode parentNode = getParent(menu,rootMenu);
            menuListNew.add(parentNode);
        }
        JSONObject data = new JSONObject();
        data.put("menuList",menuListNew);
        return data;
    }

//    public void getTreeNode2(String id){
//        List<TreeNode> menuList = Lists.newArrayList();
//        List<TreeNode> rootMenu = menuMapper.queryMenuList();
//        // 最后的结果
//        TreeNode currentNode = null;
//        for(TreeNode node : rootMenu){
//            if(id.equals(node.getId())){
//                currentNode = node;
//                break;
//            }
//        }
//        assert currentNode != null;
//        currentNode.setChildNodeList(getChild(currentNode.getId(), rootMenu));
//        TreeNode parentNode = getParent(currentNode,rootMenu);
//        menuList.add(parentNode);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("menuList",menuList);
//        System.out.println(jsonObject.toJSONString());
//    }

    private List<TreeNode> getChild(String id, List<TreeNode> rootMenu){
        // 子菜单
        List<TreeNode> childList = new ArrayList<>();
        for (TreeNode menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getpId())) {
                if (menu.getpId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TreeNode menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isNotBlank(menu.getpId())) {
                // 递归
                menu.setChildNodeList(getChild(menu.getId(), rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    private TreeNode getParent(TreeNode currentNode, List<TreeNode> rootMenu){
        if(StringUtils.isBlank(currentNode.getpId())){
            return currentNode;
        }
        // 子菜单
        List<TreeNode> childList = new ArrayList<>();
        TreeNode parentNode = null;
        for (TreeNode menu : rootMenu) {
            if (StringUtils.isNotBlank(menu.getId())) {
                String pid = currentNode.getpId();
                String menuId = menu.getId();
                if(pid.equals(menuId)){
                    parentNode = menu;
                    break;
                }
            }
        }
        if(parentNode != null){
            childList.add(currentNode);
            parentNode.setChildNodeList(childList);
            currentNode = parentNode;
            return getParent(currentNode,rootMenu);
        }
        return currentNode;
    }

    public int add(TreeNode treeNode){
        return menuMapper.insert(treeNode);
    }

    public void updateCompanyName(Object id,String cName){
        Map<String,Object> param = Maps.newHashMap();
        param.put("id","c"+id);
        param.put("showName",cName);
        menuMapper.updateCompanyName(param);
    }

    public void updateStationName(Object id,String cName){
        Map<String,Object> param = Maps.newHashMap();
        param.put("id","s"+id);
        param.put("showName",cName);
        menuMapper.updateCompanyName(param);
    }

}
