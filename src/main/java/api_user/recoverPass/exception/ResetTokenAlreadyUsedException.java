package api_user.recoverPass.exception;

public class ResetTokenAlreadyUsedException extends RuntimeException {
    public ResetTokenAlreadyUsedException(String message) {
        super(message);
    }
}

