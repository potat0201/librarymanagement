package library.model;

public class UserAccount extends BaseEntity {
    private String username;
    private String passwordHash;
    private String email;
    private String status;
    private int roleId;
    private String roleName;

    public UserAccount() {
        super();
    }

    public UserAccount(long id, String username, String passwordHash,
                       String email, String status, int roleId, String roleName) {
        super(id);
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.status = status;
        this.roleId = roleId;
        this.roleName = roleName;
    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    @Override
    public String toString() {
        return username + " (" + roleName + ")";
    }
}