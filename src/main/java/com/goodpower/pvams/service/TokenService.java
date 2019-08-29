package com.goodpower.pvams.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.goodpower.pvams.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    //有效时间1个小时
    private static final Long  EXPIRE_TIME = 20*60*1000L;

    public static final String TOKEN_SECRET = "goodpower_secret";

    public String getToken(User user) {
        // 设置过期时间
        Date expDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
        String token="";
        token= JWT.create().withHeader(header).withClaim("username",user.getUsername())
                .withClaim("password",user.getPassword())
                .withExpiresAt(expDate)
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;
    }

    public boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
