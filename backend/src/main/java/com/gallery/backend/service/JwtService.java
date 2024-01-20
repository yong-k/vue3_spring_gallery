package com.gallery.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);

    Claims getClaims(String token);

    // 요청왔을 때, 요청한 사용자가 올바른 사용자인지 여부 확인해야 한다.
    //--→ 인자로 받은 token이 유효한 토큰인지 확인하는 메서드
    boolean isValid(String token);

    // 토큰정보에서 사용자 id 값 가져옴
    int getId(String token);
}
