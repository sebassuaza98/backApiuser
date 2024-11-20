package api_user.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private String identificacion;
    private String name;
    private String lastName;
    private String password;
    private String phone;
    private String address;
    private String email;
    private String role;

}
