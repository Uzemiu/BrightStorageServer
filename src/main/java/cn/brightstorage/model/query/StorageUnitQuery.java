package cn.brightstorage.model.query;

import cn.brightstorage.model.entity.StorageUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class StorageUnitQuery extends BaseQuery<StorageUnit>{

    private Long id;

    private Integer type;

    private String name;

    private Integer amount;

    private Long parentId;

    private Boolean access;

    private Boolean deleted;

    private Date expireTime;

    private String note;

    @Override
    public Specification<StorageUnit> toSpecification() {
        Specification<StorageUnit> specification = super.toSpecification();
        return specification.and((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(deleted != null){
                predicates.add(criteriaBuilder.equal(root.get("deleted"),deleted));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
