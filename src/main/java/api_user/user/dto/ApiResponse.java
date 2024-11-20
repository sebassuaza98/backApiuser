package api_user.user.dto;

public class ApiResponse<T> {
    private String status;
    private T data;
    private String details;

    // Constructor para respuestas exitosas (con data)
    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
        this.details = null;  // Details puede ser nulo si no es necesario
    }

    // Constructor para respuestas con error (con details)
    public ApiResponse(String status, String details) {
        this.status = status;
        this.details = details;
        this.data = null;  // Data puede ser nulo si hay un error
    }

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
