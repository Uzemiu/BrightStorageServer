package cn.brightstorage.service.mapper;

import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.vo.UserVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-22T13:29:21+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
@Component
public class UserVOMapperImpl implements UserVOMapper {

    @Override
    public User toEntity(UserVO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setId( dto.getId() );
        user.setNickname( dto.getNickname() );
        user.setAvatar( dto.getAvatar() );

        return user;
    }

    @Override
    public UserVO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( entity.getId() );
        userVO.setNickname( entity.getNickname() );
        userVO.setAvatar( entity.getAvatar() );

        return userVO;
    }

    @Override
    public List<User> toEntity(List<UserVO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserVO userVO : dtoList ) {
            list.add( toEntity( userVO ) );
        }

        return list;
    }

    @Override
    public List<UserVO> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserVO> list = new ArrayList<UserVO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public List<UserVO> toDto(Set<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserVO> list = new ArrayList<UserVO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
