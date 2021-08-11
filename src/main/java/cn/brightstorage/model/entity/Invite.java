package cn.brightstorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
//@Entity
public class Invite extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relation_id", nullable = false)
    private Relation relation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description", length = 1023)
    @ColumnDefault("''")
    private String description;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(description == null){
            description = "";
        }
    }
}
