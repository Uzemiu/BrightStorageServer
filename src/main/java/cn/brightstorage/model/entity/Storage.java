package cn.brightstorage.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Storage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long id;

    @Column(name = "owner", length = 36)
    private String owner;

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
    private Long parentId;

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


    private Long category;


}
