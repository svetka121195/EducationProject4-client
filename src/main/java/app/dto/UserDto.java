package app.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Светлана on 25.08.2018.
 */
public class UserDto implements Serializable {

    private long id;

    private String name;

    private String login;

    private String password;

    private Set<RoleDto> roles;

    public UserDto() {
    }

    /*public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = processRoles(user.getRoles());
    }

    private Set<RoleDto> processRoles(Set<Role> roles){
        HashSet<RoleDto> list = new HashSet<>();
        for (Role role: roles){
            list.add(new RoleDto(role));
        }
        return list;
    }*/

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
}
