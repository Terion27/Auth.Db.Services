package dbservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Table("users")
@Builder(toBuilder = true)
public class User {

    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private String telephone;
    @Column("registration_date")
    private LocalDateTime registrationDate;
    @Column("create_token_date_time")
    private LocalDateTime createTokenDateTime;
    private boolean status;
    private boolean visibility;
    private String role;

    public User() {
    }

    public User(Long id, String username, String password, String nickname, String firstName, String lastName,
                String telephone, LocalDateTime registrationDate, LocalDateTime createTokenDateTime, boolean status,
                boolean visibility, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.registrationDate = registrationDate;
        this.createTokenDateTime = createTokenDateTime;
        this.status = status;
        this.visibility = visibility;
        this.role = role;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getCreateTokenDateTime() {
        return createTokenDateTime;
    }

    public void setCreateTokenDateTime(LocalDateTime createTokenDateTime) {
        this.createTokenDateTime = createTokenDateTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
