package api_user.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api_user.user.model.UserModel;

public interface UserRepositori extends JpaRepository<UserModel, String> {
    UserModel findByName(String name);
    boolean existsByUserId(String userId);
    List<UserModel> findByStateAndRole(boolean state, String role);
    List<UserModel> findByState(boolean state);
    List<UserModel> findByRole(String role);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUserId(String userId);
}
