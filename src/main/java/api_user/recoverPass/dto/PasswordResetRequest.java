package api_user.recoverPass.dto;
import lombok.Data;

@Data
public class PasswordResetRequest {
    private String token;
    private String newPassword;

}
