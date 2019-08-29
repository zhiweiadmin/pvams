package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.CityMapper;
import com.goodpower.pvams.mapper.CountyMapper;
import com.goodpower.pvams.mapper.ProvinceMapper;
import com.goodpower.pvams.model.City;
import com.goodpower.pvams.model.County;
import com.goodpower.pvams.model.Province;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CommonService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CountyMapper countyMapper;

    public static Integer getMonthQuarter(Integer month) {
        Integer quarter = null;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        }
        if (month >= 4 && month <= 6) {
            quarter = 2;
        }
        if (month >= 7 && month <= 9) {
            quarter = 3;
        }
        if (month >= 10 && month <= 12) {
            quarter = 4;
        }
        return quarter;
    }

    public List<Province> getProvince(){
        return provinceMapper.selectByFields(null);
    }

    public List<City> getCity(Long provinceId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("provinceId",provinceId);
        return cityMapper.selectByFields(param);
    }

    public List<County> getCounty(Long cityId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("cityId",cityId);
        return countyMapper.selectByFields(param);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
