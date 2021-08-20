package cn.brightstorage.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class StorageUnit extends OwnershipEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_unit_id")
    private Long id;

    /**
     * 0: 物品
     * 1: 容器
     */
    @Column(name = "type")
    @ColumnDefault("0")
    private Integer type;

    @Column(name = "name")
    @ColumnDefault("''")
    private String name;

    @Column(name = "amount")
    @ColumnDefault("1")
    private Integer amount;

    @Column(name = "parent_id")
    @ColumnDefault("0")
    private Long parentId;

    @Transient
    private Long localParentId;

    /**
     * true: 向关系成员公开
     * false: 私有
     */
    @Column(name = "access")
    @ColumnDefault("0")
    private Boolean access;

    @Column(name = "image",length = 1023)
    @ColumnDefault("''")
    private String image;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean deleted;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "note", length = 1023)
    @ColumnDefault("''")
    private String note;

    @Column(name = "last_access_time")
    private Date lastAccessTime;

    @ManyToMany
    @JoinTable(name = "storage_unit_category",
            joinColumns = {@JoinColumn(name = "storage_unit_id",referencedColumnName = "storage_unit_id")},
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "none"),
            inverseJoinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "category_id")},
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "none"))
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "shared_relation_id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "none"))
    private Relation sharedRelation;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(type == null){
            type = 0;
        }
        if(name == null){
            name = "";
        }
        if(amount == null){
            amount = 1;
        }
        if(parentId == null){
            parentId = 0L;
        }
        if(access == null){
            access = false;
        }
        if(image == null){
            image = "";
        }
        if(deleted == null){
            deleted = false;
        }
        if(note == null){
            note = "";
        }
        if(lastAccessTime == null){
            lastAccessTime = new Date();
        }
    }

    @Override
    protected void preUpdate() {
        super.preUpdate();
        lastAccessTime = new Date();
    }
}
