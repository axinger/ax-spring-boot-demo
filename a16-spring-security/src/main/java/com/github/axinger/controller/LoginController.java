package com.github.axinger.controller;

import com.github.axinger.dto.LoginResponse;
import com.github.axinger.service.JwtTokenProvider;
import com.github.axinger.service.MyAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MyAuthenticationProvider authenticationProvider;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
            Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            log.info("登录成功={}", authentication);
            // 生成 Token
            String token = jwtTokenProvider.generateToken(authentication);
            // 返回 Token 和权限集合
            return ResponseEntity.ok(new LoginResponse(token, list));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("账号或者密码不对");
        }
    }
}
