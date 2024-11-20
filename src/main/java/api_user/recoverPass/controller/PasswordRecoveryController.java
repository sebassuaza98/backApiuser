package api_user.recoverPass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_user.recoverPass.dto.PasswordResetRequest;
import api_user.recoverPass.exception.UserNotFoundException;
import api_user.recoverPass.service.PasswordRecoveryService;
import api_user.user.dto.ApiResponse;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @RequestMapping("/requestPasswordRecovery")
    public ResponseEntity<ApiResponse<String>> passwordRecovery(@RequestParam String email) {
        try {
            passwordRecoveryService.sendRecoveryEmail(email);
            ApiResponse<String> response = new ApiResponse<>("200", "Correo de recuperación enviado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            ApiResponse<String> response = new ApiResponse<>("404", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("400", "Solicitud incorrecta: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            ApiResponse<String> response = new ApiResponse<>("500", "Error al enviar el correo de recuperación");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {  
            ApiResponse<String> response = new ApiResponse<>("500", "Error inesperado : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/recoverPassword")
    public ResponseEntity<ApiResponse<String>> recoverPassword(@RequestBody PasswordResetRequest request) {
        try {
            passwordRecoveryService.resetPassword(request.getToken(), request.getNewPassword());
            ApiResponse<String> response = new ApiResponse<>("200", "Contraseña actualizada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("400", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
