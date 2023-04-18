package authservice.models.dto;

public class ValidToken {

    private String token = "";
    private String username = "";

    public ValidToken() {
    }

    public ValidToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

