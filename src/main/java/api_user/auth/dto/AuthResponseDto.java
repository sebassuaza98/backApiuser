package api_user.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    String token;
    String refreshToken;
}
