package cn.brightstorage.service;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.service.base.CrudService;

public interface RelationService extends CrudService<Relation, Long> {

    Relation create(RelationDTO relationDTO);

    void update(RelationDTO relationDTO);
}
