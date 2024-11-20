package api_user.recoverPass.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import api_user.recoverPass.model.PasswordResetToken;
import api_user.user.model.UserModel;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByTokenAndUsed(String token, boolean used);
    Optional<PasswordResetToken> findByUser(UserModel user);
}
