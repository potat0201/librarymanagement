package library.model;

public abstract class BaseEntity {
    protected long id; // Thuộc tính chung

    public BaseEntity() {}

    public BaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract String toString();
}