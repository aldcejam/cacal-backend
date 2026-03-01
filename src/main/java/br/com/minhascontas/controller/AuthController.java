package br.com.minhascontas.controller;

import br.com.minhascontas.dto.auth.AuthRes;
import br.com.minhascontas.dto.auth.LoginReq;
import br.com.minhascontas.dto.auth.RegisterReq;
import br.com.minhascontas.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.cookie-secure:false}")
    private boolean cookieSecure;

    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(@Valid @RequestBody RegisterReq request) {
        AuthRes authRes = authService.register(request);
        return createCookieResponse(authRes);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRes> login(@Valid @RequestBody LoginReq request) {
        AuthRes authRes = authService.login(request);
        return createCookieResponse(authRes);
    }

    private ResponseEntity<AuthRes> createCookieResponse(AuthRes authRes) {
        long maxAgeInSeconds = jwtExpiration / 1000;

        ResponseCookie jwtCookie = ResponseCookie.from("token", authRes.getAccessToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(maxAgeInSeconds)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authRes);
    }
}
