package cn.brightstorage.model.entity;

import cn.brightstorage.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class OwnershipEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(owner == null){
            owner = SecurityUtil.getCurrentUser();
        }
    }

    @Override
    protected void preUpdate() {
        super.preUpdate();
        if(owner == null){
            owner = SecurityUtil.getCurrentUser();
        }
    }
}
