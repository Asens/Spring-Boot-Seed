package cn.asens.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Asens on 2015/12/26
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(
            name="user_role",
            joinColumns=@JoinColumn(name="role_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> userSet;

    @ManyToMany(targetEntity = Permission.class,fetch = FetchType.EAGER)
    @JoinTable(
            name="permission_role",
            joinColumns=@JoinColumn(name="role_id"),
            inverseJoinColumns=@JoinColumn(name="permission_id")
    )
    private Set<Permission> permissionSet;

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Set<Permission> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName){
        this.roleName=roleName;
    }


}
