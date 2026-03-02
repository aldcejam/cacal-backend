package br.com.myaccounts.dto.auth;
import br.com.myaccounts.model.User;

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

    public static UserRes fromEntity(User user) {
        UserRes res = new UserRes();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());
        res.setAvatarUrl(user.getAvatarUrl());
        return res;
    }
}
