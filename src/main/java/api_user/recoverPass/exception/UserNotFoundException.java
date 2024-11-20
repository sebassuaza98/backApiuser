package api_user.recoverPass.exception;


public class UserNotFoundException extends RuntimeException {
    
    // Constructor que recibe un mensaje personalizado
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructor que recibe el mensaje y la causa (opcional)
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
