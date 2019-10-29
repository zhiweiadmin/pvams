package com.goodpower.pvams.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultCode;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.UserService;
import com.goodpower.pvams.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResultMap login(@RequestBody User user){
        ResultMap result = new ResultMap();
        User userParam = userService.findUserByUsername(user.getUsername());
        if (userParam == null) {
            return result.fail().code(401).message("用户名错误");
        }else if (userParam.getPassword()!=null && !userParam.getPassword().equals(user.getPassword())) {
            return result.fail().code(401).message("密码错误");
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Token",JWTUtil.createToken(user.getUsername()));
            List<String> permissions = userService.getRolePermission(userParam);
            jsonObject.put("permissions",permissions);
            jsonObject.put("username",userParam.getUsername());
            jsonObject.put("realname",userParam.getRealname());
            jsonObject.put("userType",userParam.getUserType());
            jsonObject.put("userId",userParam.getUserId());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger.info("用户:"+user.getUsername()+" 登录于:"+sdf.format(date));
            return result.success().code(200).setData(jsonObject).message("登录成功");
        }
    }

    @RequestMapping("/unauthorized/{msgCode}")
    public ResultMap unauthorized(@PathVariable String msgCode){
        ResultMap result = new ResultMap();
        return result.fail().code(Integer.parseInt(msgCode)).message(ResultCode.CODEMAP.get(msgCode));
    }

    @GetMapping("/logout")
    public ResultMap logout(){
        ResultMap result = new ResultMap();
        return result.success().code(200).message("退出成功");
    }

}
