package cn.asens.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Asens on 2015/12/26
 */
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "permission_name")
    private String permissionName;
    @Column
    private String introduction;
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
            name="permission_role",
            joinColumns=@JoinColumn(name="permission_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private Set<Role> roleSet;

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName){
        this.permissionName=permissionName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
