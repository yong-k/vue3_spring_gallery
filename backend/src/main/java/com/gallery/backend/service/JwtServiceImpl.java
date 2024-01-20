package com.gallery.backend.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtServiceImpl implements JwtService {

    private String secretKey = "jsaiojowe12343vjisaj#@!%@$@#$56418654fksdgjvwif#@!$@rheiuhjkdfghl345245416!@#$";

    // 입력받은 key와 value를 secretKey를 이용해서 만들어주는 메서드
    @Override
    public String getToken(String key, Object value) {
        Date expTime = new Date();  // 토큰의 유효시간
        expTime.setTime(expTime.getTime() + 1000 * 60 * 5); // 5분으로 설정
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);    // secretKey를 byte로 만듦
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", "JWT");
        headerMap.put("algorithm", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }
}
