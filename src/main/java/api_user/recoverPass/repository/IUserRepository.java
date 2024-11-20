package api_user.recoverPass.repository;

import api_user.user.model.UserModel;

public interface IUserRepository {
    UserModel findByUserId(String userId);
}
