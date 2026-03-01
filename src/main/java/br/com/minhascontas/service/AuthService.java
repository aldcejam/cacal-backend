package br.com.minhascontas.service;

import br.com.minhascontas.dto.auth.AuthRes;
import br.com.minhascontas.dto.auth.LoginReq;
import br.com.minhascontas.dto.auth.RegisterReq;
import br.com.minhascontas.dto.auth.UserRes;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.repository.UsuarioRepository;
import br.com.minhascontas.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthRes register(RegisterReq request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setName(request.getName());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRole("USER");

        usuarioRepository.save(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        return new AuthRes(token, UserRes.fromEntity(usuario));
    }

    public AuthRes login(LoginReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthRes(token, UserRes.fromEntity(usuario));
    }
}
