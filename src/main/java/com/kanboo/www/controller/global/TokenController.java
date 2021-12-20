package com.kanboo.www.controller.global;

import com.kanboo.www.security.JwtSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final JwtSecurityService jwtSecurityService;

    @PostMapping("/check")
    public ResponseEntity<?> tokenCheck(String token) {
        String exeToken = jwtSecurityService.getToken(token);
        if(exeToken != null || !exeToken.isEmpty()) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.ok(token);
    }
}
