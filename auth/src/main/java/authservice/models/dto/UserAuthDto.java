package authservice.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Date;

public class UserAuthDto {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime createTokenDateTime;
    private String role;
    private boolean status;

    public UserAuthDto() {
    }

    public UserAuthDto(Long id, String username, String password, LocalDateTime createTokenDateTime, String role, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTokenDateTime = createTokenDateTime;
        this.role = role;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTokenDateTime() {
        return createTokenDateTime;
    }

    public void setCreateTokenDateTime(LocalDateTime createTokenDateTime) {
        this.createTokenDateTime = createTokenDateTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
