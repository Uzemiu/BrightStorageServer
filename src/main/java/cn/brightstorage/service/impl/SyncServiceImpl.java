package cn.brightstorage.service.impl;

import cn.brightstorage.constant.OperationConstant;
import cn.brightstorage.exception.BadRequestException;
import cn.brightstorage.model.entity.OperationLog;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.OperationLogRepository;
import cn.brightstorage.repository.StorageUnitRepository;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.SyncService;
import cn.brightstorage.service.base.AbstractCrudService;
import cn.brightstorage.utils.SecurityUtil;
import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service("syncService")
public class SyncServiceImpl extends AbstractCrudService<OperationLog, Long> implements SyncService {

    private final OperationLogRepository operationLogRepository;

    private final StorageUnitService storageUnitService;

    private final ObjectMapper objectMapper;

    protected SyncServiceImpl(OperationLogRepository repository,
                              StorageUnitService storageUnitService) {
        super(repository);
        this.operationLogRepository = repository;
        this.storageUnitService = storageUnitService;
        this.objectMapper = new ObjectMapper();
    }

    @Transactional
    @Override
    public List<OperationLog> push(List<OperationLog> operationLogs) {
        // 在客户端进行合并

        User currentUser = SecurityUtil.getCurrentUser();
        List<OperationLog> toCreate = new ArrayList<>();

        Long lastVersion = operationLogRepository.getLatestVersionByOwner(currentUser);
        if(lastVersion == null){
            lastVersion = 1L;
        }

        try {
            for (OperationLog operationLog : operationLogs) {
                Long remoteId = operationLog.getRemoteId();

                switch (operationLog.getOperationType()) {
                case OperationConstant.CREATE:

                    StorageUnit storageUnit = objectMapper.readValue(operationLog.getData(), StorageUnit.class);
                    Long localParentId = storageUnit.getLocalParentId();

                    if(storageUnit.getParentId() == null && localParentId != null){
                        // 同步父节点
                        // 父节点是新建的，从之前新建的记录中寻找父节点
                        for (OperationLog created : toCreate) {
                            if(created.getLocalId().equals(localParentId)){
                                storageUnit.setParentId(created.getRemoteId());
                                break;
                            }
                        }
                    }
                    storageUnitService.create(storageUnit);

                    operationLog.setRemoteId(storageUnit.getId());
                    toCreate.add(operationLog); // 记录新建的项目，用于返回服务器存储的ID
                    break;
                case OperationConstant.UPDATE:
                    StorageUnit toUpdate = storageUnitService.getById(remoteId).orElse(null);
                    if(toUpdate != null){
                        // 有可能进行更新的时候因为父节点被删而级联删除
                        Map values = objectMapper.readValue(operationLog.getData(), Map.class);
                        BeanUtil.fillBeanWithMap(values, toUpdate, true);
                        // OperationLog应该已经合并，不在update中同步父节点
                        storageUnitService.update(toUpdate);
                    }
                    break;
                case OperationConstant.DELETE:
                    storageUnitService.deleteById(remoteId);
                    break;
                default:
                    break;
                }

                operationLog.setVersion(lastVersion + 1); // 作为新版本
            }

            operationLogRepository.saveAll(operationLogs);

            return toCreate;
        } catch (JsonProcessingException e) {
            log.error("SyncService#push: 解析Json异常",e);
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<OperationLog> pull(Long version) {
        User currentUser = SecurityUtil.getCurrentUser();
        return mergeOperationLog(operationLogRepository.getByOwnerAndVersionGreaterThan(currentUser, version));
    }

    @Override
    public List<OperationLog> sync(List<OperationLog> operationLogs, Long version) {


        return null;
    }

    /**
     * 服务端合并操作<br/>
     *
     * 对localId(push)/remoteId(pull)相同的操作：
     * 1. 创建之后的更新合并为单次创建
     * 2. 忽略删除之外所有操作
     * 3. 多个更新合并为一次更新
     * @param operationLogs
     * @return
     */
    private List<OperationLog> mergeOperationLog(List<OperationLog> operationLogs){
        Map<Long, Map> toModify = new HashMap<>(); // 新建或更新
        Set<Long> toDelete = new HashSet<>();
        for (OperationLog operationLog : operationLogs) {
            Long id = operationLog.getRemoteId();
        }
        return operationLogs;
    }
}
