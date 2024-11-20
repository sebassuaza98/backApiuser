package api_user.user.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user") 
public class UserModel {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "name", nullable = false) 
    private String name;

    @Column(name = "lastName", nullable = false) 
    private String lastName;

    @Column(name = "password", nullable = false) 
    private String password;

    @Column(name = "phone", nullable = false, length = 15) 
    private String phone;

    @Column(name = "address", nullable = false) 
    private String address; 

    @Column(name = "email", nullable = false) 
    private String email;
    
    @Column(name = "date", nullable = false) 
    private LocalDateTime date;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "role", nullable = false) 
    private String role;

    private String passwordRecoveryToken;
    private LocalDateTime tokenExpirationDate;
    
    public String getPasswordRecoveryToken() {
        return passwordRecoveryToken;
    }

    public void setPasswordRecoveryToken(String passwordRecoveryToken) {
        this.passwordRecoveryToken = passwordRecoveryToken;
    }

    public LocalDateTime getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(LocalDateTime tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean  getState() {
        return state;
    }

    public void setState(Boolean  state) {
        this.state = state;
    }

}
