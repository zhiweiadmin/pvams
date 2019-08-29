package com.goodpower.pvams.controller.index;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.MenuService;
import com.goodpower.pvams.service.UserService;
import com.goodpower.pvams.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    /**
     * 0:企业用户
     * 1:电站用户
     */
    @PostMapping("/getMenu")
    public ResultMap getMenu(){
        ResultMap resultMap = new ResultMap();
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isBlank(token)){
            return resultMap.fail().message("token为空").code(401);
        }
        String username = JWTUtil.getUsername(token);
        User user = userService.findUserByUsername(username);
        //超级管理员
        try{
            if(user.getRole() == 1){
                JSONObject jsonObject = menuService.getTreeNode(null);
                resultMap.setData(jsonObject).success().message("查询成功").code(200);
            }else{
                String menuId;
                JSONObject data = new JSONObject();
                if(user.getUserType() == 0){
                    menuId = "c"+user.getUnitId();
                    data = menuService.getTreeNode(menuId);
                }else if(user.getUserType() == 1){
                    menuId = "s"+user.getUnitId();
                    data = menuService.getTreeNode(menuId);
                }
                logger.error("菜单列表:"+data.toJSONString());
                resultMap.setData(data).success().message("查询成功").code(200);
            }
        }catch (Exception e){
            logger.error("查询失败",e);
            resultMap.fail().message("查询失败").code(400);
        }
        return resultMap;
    }

}
