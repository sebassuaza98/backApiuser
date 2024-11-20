package api_user.recoverPass.service;

import api_user.recoverPass.exception.InvalidTokenException;
import api_user.recoverPass.exception.ResetTokenAlreadyUsedException;
import api_user.recoverPass.exception.UserNotFoundException;
import api_user.recoverPass.model.PasswordResetToken;
import api_user.recoverPass.repository.PasswordResetTokenRepository;
import api_user.user.model.UserModel;
import api_user.user.repository.UserRepositori;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordRecoveryService {

    private final UserRepositori userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRecoveryService(UserRepositori userRepository, PasswordResetTokenRepository passwordResetTokenRepository,
                                   JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

public void sendRecoveryEmail(String email) throws MessagingException {
    Optional<UserModel> userOpt = userRepository.findByEmail(email);
    if (userOpt.isPresent()) {
        UserModel user = userOpt.get();
    
        Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepository.findByUser(user);
        if (existingTokenOpt.isPresent()) {
            PasswordResetToken existingToken = existingTokenOpt.get();

            if (!existingToken.isUsed() && existingToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                existingToken.setUsed(true); 
                passwordResetTokenRepository.save(existingToken);
            } else {
                passwordResetTokenRepository.delete(existingToken); 
            }
        }
        String token = generateToken(user); 
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); 
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetToken.setUsed(false); 
        
        try {
            passwordResetTokenRepository.save(passwordResetToken); 
        } catch (DataIntegrityViolationException e) {
            throw new InvalidTokenException("El token de recuperación no es válido o ya existe uno activo.");
        }
        String resetLink = "http://localhost:4200/passs?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Recuperación de Contraseña");
            helper.setText("Para restablecer su contraseña, haga clic en el siguiente enlace: " + resetLink, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Error al enviar el correo de recuperación: " + e.getMessage(), e);
        }
    } else {
        throw new UserNotFoundException("No se encontró el correo electrónico registrado.");
    }
}

    
    
    
    

    public void resetPassword(String token, String newPassword) {
        // Buscar el token en la base de datos
        Optional<PasswordResetToken> tokenOpt = passwordResetTokenRepository.findByToken(token);
        
        if (tokenOpt.isEmpty()) {
            throw new IllegalArgumentException("Token inválido");
        }

        PasswordResetToken resetToken = tokenOpt.get();

        // Verificar si el token ha expirado o ha sido usado
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El token ha expirado.");
        }else if (resetToken.isUsed()) {
            throw new ResetTokenAlreadyUsedException("Este enlace ya ha sido utilizado.");
        }
        UserModel user = resetToken.getUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
        userRepository.save(user);
    }

    private String generateToken(UserModel user) {
        return user.getUserId() + "_" + System.currentTimeMillis();
    }
}
