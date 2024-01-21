package com.gallery.backend.controller;

import com.gallery.backend.entity.Member;
import com.gallery.backend.repository.MemberRepository;
import com.gallery.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class  AccountController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse response) {

        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        /**
         * member 값이 null이 아니면,
         * id를 token화해서 → 해당 token을 cookie에 넣는다.
         * 그리고 그 cookie를 응답값인 response에 추가해준다.
         *
         * f12 > application > Cookies부분에서 확인하면 된다.
         * */
        if (member != null) {
            int id = member.getId();
            String token = jwtService.getToken("id", id);

            // return token;
            //--→ token을 클라이언트에게 줘서, 클라이언트 측에서 세션스토리지나 쿠키에 저장할 수도 있지만,
            //    보안상 서버사이드에서 하는 것이 더 안전하다.

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);   // 자바스크립트로는 접근하지 못하도록 처리
            cookie.setPath("/");

            response.addCookie(cookie);

            return new ResponseEntity<>(id, HttpStatus.OK);
            //--→ 응답값으로 id를 넘겨준다.
            //    frontend의 Login.vue에서 axios 결과 console.log(response.data) 하면 id값 나온다.
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 사용자들의 쿠키 값을 받는다.
    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
