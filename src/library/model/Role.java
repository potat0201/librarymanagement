package library.model;

public class Role {
    private long id;
    private String name;

    public Role() {}

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return name;
    }

    public void setRoleName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "'}";
    }
}
