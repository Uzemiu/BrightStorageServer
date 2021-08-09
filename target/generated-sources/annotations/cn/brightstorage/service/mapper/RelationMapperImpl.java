package cn.brightstorage.service.mapper;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.vo.UserVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-07T21:17:42+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
@Component
public class RelationMapperImpl implements RelationMapper {

    @Autowired
    private UserVOMapper userVOMapper;

    @Override
    public Relation toEntity(RelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Relation relation = new Relation();

        relation.setOwner( userVOMapper.toEntity( dto.getOwner() ) );
        relation.setId( dto.getId() );
        relation.setName( dto.getName() );
        relation.setAvatar( dto.getAvatar() );
        relation.setMembers( userVOListToUserSet( dto.getMembers() ) );

        return relation;
    }

    @Override
    public RelationDTO toDto(Relation entity) {
        if ( entity == null ) {
            return null;
        }

        RelationDTO relationDTO = new RelationDTO();

        relationDTO.setId( entity.getId() );
        relationDTO.setName( entity.getName() );
        relationDTO.setAvatar( entity.getAvatar() );
        relationDTO.setOwner( userVOMapper.toDto( entity.getOwner() ) );
        relationDTO.setMembers( userVOMapper.toDto( entity.getMembers() ) );

        return relationDTO;
    }

    @Override
    public List<Relation> toEntity(List<RelationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Relation> list = new ArrayList<Relation>( dtoList.size() );
        for ( RelationDTO relationDTO : dtoList ) {
            list.add( toEntity( relationDTO ) );
        }

        return list;
    }

    @Override
    public List<RelationDTO> toDto(List<Relation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelationDTO> list = new ArrayList<RelationDTO>( entityList.size() );
        for ( Relation relation : entityList ) {
            list.add( toDto( relation ) );
        }

        return list;
    }

    @Override
    public List<RelationDTO> toDto(Set<Relation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelationDTO> list = new ArrayList<RelationDTO>( entityList.size() );
        for ( Relation relation : entityList ) {
            list.add( toDto( relation ) );
        }

        return list;
    }

    protected Set<User> userVOListToUserSet(List<UserVO> list) {
        if ( list == null ) {
            return null;
        }

        Set<User> set = new HashSet<User>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( UserVO userVO : list ) {
            set.add( userVOMapper.toEntity( userVO ) );
        }

        return set;
    }
}
