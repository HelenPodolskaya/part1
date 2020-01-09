package task1;

public class Role {
    private Integer id;
    private RolesEnum name;
    private String description;

    public Role() {
    }

    public Role(Integer id, RolesEnum name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role(RolesEnum name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public RolesEnum getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Роль ");
        sb.append("\nid: ");
        sb.append(id);
        sb.append("\nИмя: ");
        sb.append(name);
        sb.append("\nОписание: ");
        sb.append(description);
        return sb.toString();
    }
}
