package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.user.AuthDTO;
import com.benevides.sop_api.domain.user.LoginResponseDTO;
import com.benevides.sop_api.domain.user.RegisterDTO;
import com.benevides.sop_api.domain.user.User;
import com.benevides.sop_api.infra.GlobalErrorMessage;
import com.benevides.sop_api.infra.security.TokenService;
import com.benevides.sop_api.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = ".:Autenticação:.")
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(
            description = "Este método irá autenticar o usuário e retornar o token JWT"
    )
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getName(), user.getUsername()));
    }

    @PostMapping("/register")
    @Operation(
            description = "Este método irá cadastrar um novo usuário"
    )
    public ResponseEntity<GlobalErrorMessage> register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GlobalErrorMessage(HttpStatus.BAD_REQUEST, "Este email já está cadastrado"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(),data.login(),encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
