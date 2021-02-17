package cn.brightstorage.service.mapper;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.vo.UserVO;
import org.mapstruct.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RelationMapper extends BaseMapper<RelationDTO, Relation>{

    @Override
    @Mapping(source = "members", target = "members", qualifiedByName = "toMembers")
    RelationDTO toDto(Relation entity);

    @Named("toMembers")
    default List<UserVO> toMembers(Set<User> entity){
        return entity.stream().map(
                user -> new UserVO(user.getId(), user.getNickname(), user.getAvatar()))
                .collect(Collectors.toList());
    }
}
