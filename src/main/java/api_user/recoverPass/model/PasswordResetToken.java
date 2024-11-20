package api_user.recoverPass.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import api_user.user.model.UserModel;

@Entity
@Data
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean used = false;
}
