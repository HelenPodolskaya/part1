package task1;

import java.sql.Date;

public class User {
    private Integer id;
    private String name;

    public User(String name, Date birthday, String city, String email, String description) {
        this.name = name;
        this.birthday = birthday;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    private Date birthday;
    private Integer login_id;
    private String city;
    private String email;
    private String description;

    public User(Integer id, String name, Date birthday, Integer login_id, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_id = login_id;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    public User() {
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Integer getLogin_id() {
        return login_id;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Пользователь ");
        sb.append("id: ");
        sb.append(id);
        sb.append("\nИмя: ");
        sb.append(name);
        sb.append("\nДата рождения: ");
        sb.append(birthday);
        sb.append("\nlogin_id: ");
        sb.append(login_id);
        sb.append("\nГород: ");
        sb.append(city);
        sb.append("\nemail: ");
        sb.append(email);
        sb.append("\nОписание: ");
        sb.append(description);
        return sb.toString();
    }
}
