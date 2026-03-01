package br.com.minhascontas.dto.auth;

import br.com.minhascontas.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {

    private UUID id;
    private String name;
    private String email;
    private String role;
    private String avatarUrl;

    public static UserRes fromEntity(Usuario usuario) {
        UserRes res = new UserRes();
        res.setId(usuario.getId());
        res.setName(usuario.getName());
        res.setEmail(usuario.getEmail());
        res.setRole(usuario.getRole());
        res.setAvatarUrl(usuario.getAvatarUrl());
        return res;
    }
}
