package library.model;

public class UserAccount {
    private long id;
    private String username;
    private String password;
    private String email;
    private String status;
    private Role role;

    public UserAccount() {}

    public UserAccount(long id, String username, String password,
                       String email, String status, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    // Getter – Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return "UserAccount{id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", status='" + status + '\'' +
               ", role=" + (role != null ? role.getRoleName() : "null") +
               '}';
    }

    public void setPasswordHash(String pass) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPasswordHash'");
    }
}
