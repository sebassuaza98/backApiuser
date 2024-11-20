package api_user.util;

public class Constants {
    public static final String ERR_ = "ID de usuario inválido: ";
    public static final String ERR_TK = "Error de token de actualice: ";
    public static final String TK_EXP = "{\"error\": \"Token expirado\", \"details\": \"El token de autenticación ha expirado. Por favor, inicie sesión nuevamente.\"}";
    public static final String TK_ERR = "{\"error\": \"Error de autenticación\"";
    public static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEzE3Zmxu7BSGSJx72BSBXM";
    public static final long JWT_TIME_VALIDITY = 1000 * 60  * 15;
    public static final long JWT_TIME_REFRESH_VALIDATE = 1000 * 60  * 60 * 24;

}
