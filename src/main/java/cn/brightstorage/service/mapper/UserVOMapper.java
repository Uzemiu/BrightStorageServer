package cn.brightstorage.service.mapper;

import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserVOMapper extends BaseMapper<UserVO, User>{

}
