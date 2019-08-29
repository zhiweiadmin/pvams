package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.CompanyMapper;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.mapper.ProvinceMapper;
import com.goodpower.pvams.model.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UnitService {

    private static Integer COMPANY_USER = 0;

    private static Integer STATION_USER = 1;

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    PowerStationMapper powerStationMapper;

    @Autowired
    ProvinceMapper provinceMapper;

    public JSONObject add(Company company){
        Date date = new Date();
        company.setCreateDttm(date);
        company.setUpdateDttm(date);
        int i = companyMapper.insert(company);
        if(i > 0){
            Long companyId = company.getCompanyId();
            String provinceId = company.getProvinceId();
            Province province = provinceMapper.selectByPrimaryKey(Long.parseLong(provinceId));
            String cId = "c"+companyId;
            String pId = "p"+provinceId;
            //插入菜单表 省份
            TreeNode node = new TreeNode();
            node.setId(pId);
            node.setShowName(province.getProvinceName());
            node.setpId(null);
            menuService.add(node);
            TreeNode node1 = new TreeNode();
            node1.setId(cId);
            node1.setShowName(company.getCompanyName());
            node1.setpId(pId);
            menuService.add(node1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",cId);
            jsonObject.put("showName",company.getCompanyName());
            jsonObject.put("pId",pId);
            jsonObject.put("pShowName",province.getProvinceName());
            return jsonObject;
        }
        return new JSONObject();
    }

    public void update(Company company){
        Date date = new Date();
        company.setUpdateDttm(date);
        companyMapper.updateByPrimaryKeySelective(company);
    }

    public void delete(Long companyId){
        companyMapper.deleteByPrimaryKey(companyId);
    }

    public Company query(Long companyId){
        return companyMapper.selectByPrimaryKey(companyId);
    }

    public Long addPowerStation(PowerStation powerStation){
        powerStationMapper.insertSelective(powerStation);
        return powerStation.getStationId();
    }

    public void updatePowerStation(PowerStation powerStation){
        powerStationMapper.updateByPrimaryKey(powerStation);
    }

    public void deletePowerStation(Long stationId){
        powerStationMapper.deleteByPrimaryKey(stationId);
    }


    /**
     * unitType 1:公司  2:发电站
     * @param user
     */
    public void getUserByUnitId(User user){
        //unitMapper.getUserByUnitId(user.getUnitId(),user.getUnitType());
    }

    /**
     * 查询企业用户
     */
    public List<User> queryCompanyUser(Long companyId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("userType",COMPANY_USER);
        param.put("unitId",companyId);
        return userService.find(param);
    }

    /**
     * 查询电站用户
     */
    public List<User> queryStationUser(Long userId){
        User user = userService.findUserById(userId);
        Map<String,Object> param = Maps.newHashMap();
        param.put("userType",STATION_USER);
        param.put("unitId",user.getUnitId());
        return userService.find(param);
    }

}
