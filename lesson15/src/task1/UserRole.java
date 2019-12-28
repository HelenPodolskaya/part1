package task1;

public class UserRole {
    public Integer id;
    public Integer user_id;
    public Integer role_id;

    public UserRole(Integer id, Integer user_id, Integer role_id) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователи и роли ");
        sb.append("\nid: ");
        sb.append(id);
        sb.append("\nuser_id: ");
        sb.append(user_id);
        sb.append("\nrole_id: ");
        sb.append(role_id);
        return sb.toString();
    }
}