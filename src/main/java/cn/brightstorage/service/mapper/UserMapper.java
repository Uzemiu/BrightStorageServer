package cn.brightstorage.service.mapper;

import cn.brightstorage.model.dto.UserDTO;
import cn.brightstorage.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, User>{

}
