package api_user.auth.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    String user;
    String password;
}
