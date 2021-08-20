package cn.brightstorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.beans.PropertyDescriptor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class OperationLog extends OwnershipEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_log_id")
    private Long id;

    @Column(name = "operation_type")
    private Integer operationType;

    @Column(name = "data", length = 8192)
    private String data;

    /**
     * 服务器上StorageUnit的ID
     */
    @Column(name = "remote_id")
    private Long remoteId;

    /**
     * 客户端StorageUnit的ID
     */
    @Column(name = "local_id")
    private Long localId;

    @Column(name = "version")
    private Long version;

    @Override
    protected void prePersist() {
        super.prePersist();
        if(data == null){
            data = "";
        }
        if(version == null){
            version = 0L;
        }
    }

}
