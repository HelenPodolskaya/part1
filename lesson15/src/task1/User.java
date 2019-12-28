package task1;

import java.time.LocalDate;

public class User {
    public Integer id;
    public String name;
    public LocalDate birthday;
    public Integer login_id;
    public String city;
    public String email;
    public String description;

    public User(Integer id, String name, LocalDate birthday, Integer login_id, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_id = login_id;
        this.city = city;
        this.email = email;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
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
