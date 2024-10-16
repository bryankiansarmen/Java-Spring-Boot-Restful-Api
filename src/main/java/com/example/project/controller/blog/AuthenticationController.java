package com.example.project.controller.blog;

import com.example.project.dto.request.blog.LoginUserDto;
import com.example.project.dto.request.blog.RefreshTokenDto;
import com.example.project.dto.request.blog.RegisterUserDto;
import com.example.project.dto.response.blog.LoginResponse;
import com.example.project.model.blog.User;
import com.example.project.service.blog.AuthenticationService;
import com.example.project.service.blog.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return ResponseEntity.ok(new LoginResponse(jwtToken, jwtService.getExpirationTime()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        String jwtToken = jwtService.refreshToken(refreshTokenDto.getToken());

        return ResponseEntity.ok(new LoginResponse(jwtToken, jwtService.getExpirationTime()));
    }
}
