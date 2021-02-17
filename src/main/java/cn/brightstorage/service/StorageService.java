package cn.brightstorage.service;

import cn.brightstorage.model.entity.Storage;
import cn.brightstorage.service.base.CrudService;

public interface StorageService extends CrudService<Storage, Long> {

    Storage create();
}
