package cn.brightstorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class User extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy="org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", length = 36)
    private String id;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "nickname",length = 255)
    private String nickname;

    @Column(name = "email",length = 255)
    @ColumnDefault("''")
    private String email;

    @Column(name = "phone",length = 32,unique = true,nullable = false)
    private String phone;

    @Column(name = "avatar",length = 1023)
    @ColumnDefault("''")
    private String avatar;

    @ManyToMany(mappedBy = "members")
    private Set<Relation> relations;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(email == null){
            email = "";
        }
        if(avatar == null){
            avatar = "";
        }
        if(nickname == null){
            nickname = "智存用户-" + phone;
        }
    }
}
