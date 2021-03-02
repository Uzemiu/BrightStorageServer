package cn.brightstorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Relation extends OwnershipEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar", length = 1023)
    @ColumnDefault("''")
    private String avatar;

    @ManyToMany
    @JoinTable(name = "user_relation",
            joinColumns = @JoinColumn(name = "relation_id",referencedColumnName = "relation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"))
    private Set<User> members;

    @Override
    protected void prePersist() {
        super.preUpdate();
        if(avatar == null){
            avatar = "";
        }
    }

    @Override
    protected void preUpdate() {
        super.preUpdate();
    }
}
