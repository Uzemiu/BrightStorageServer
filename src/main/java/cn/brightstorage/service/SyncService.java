package cn.brightstorage.service;

import cn.brightstorage.model.entity.OperationLog;
import cn.brightstorage.service.base.CrudService;

import java.util.List;

public interface SyncService extends CrudService<OperationLog, Long> {

    /**
     * 将客户端存储单元修改记录推送到服务器上<br/>
     * 如果是新建记录，会记录远程服务器存储ID并返回
     * @param operationLogs 客户端操作记录
     * @return 包含服务器存储ID的新建记录
     */
    List<OperationLog> push(List<OperationLog> operationLogs);

    List<OperationLog> pull(Long version);

    List<OperationLog> sync(List<OperationLog> operationLogs, Long version);
}
