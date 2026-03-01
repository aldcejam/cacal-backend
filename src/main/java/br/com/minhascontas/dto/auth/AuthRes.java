package br.com.minhascontas.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRes {

    @JsonProperty("access_token")
    private String accessToken;

    private UserRes user;
}
