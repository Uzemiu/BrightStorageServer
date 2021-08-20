package cn.brightstorage.service.mapper;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.StorageUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-14T12:44:58+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
@Component
public class StorageUnitMapperImpl implements StorageUnitMapper {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserVOMapper userVOMapper;

    @Override
    public StorageUnit toEntity(StorageUnitDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StorageUnit storageUnit = new StorageUnit();

        storageUnit.setOwner( userVOMapper.toEntity( dto.getOwner() ) );
        storageUnit.setId( dto.getId() );
        storageUnit.setType( dto.getType() );
        storageUnit.setName( dto.getName() );
        storageUnit.setAmount( dto.getAmount() );
        storageUnit.setParentId( dto.getParentId() );
        storageUnit.setAccess( dto.getAccess() );
        storageUnit.setImage( dto.getImage() );
        storageUnit.setDeleted( dto.getDeleted() );
        storageUnit.setExpireTime( dto.getExpireTime() );
        storageUnit.setNote( dto.getNote() );
        storageUnit.setCategories( categoryDTOSetToCategorySet( dto.getCategories() ) );

        return storageUnit;
    }

    @Override
    public StorageUnitDTO toDto(StorageUnit entity) {
        if ( entity == null ) {
            return null;
        }

        StorageUnitDTO storageUnitDTO = new StorageUnitDTO();

        storageUnitDTO.setId( entity.getId() );
        storageUnitDTO.setOwner( userVOMapper.toDto( entity.getOwner() ) );
        storageUnitDTO.setType( entity.getType() );
        storageUnitDTO.setName( entity.getName() );
        storageUnitDTO.setAmount( entity.getAmount() );
        storageUnitDTO.setParentId( entity.getParentId() );
        storageUnitDTO.setAccess( entity.getAccess() );
        storageUnitDTO.setImage( entity.getImage() );
        storageUnitDTO.setDeleted( entity.getDeleted() );
        storageUnitDTO.setExpireTime( entity.getExpireTime() );
        storageUnitDTO.setNote( entity.getNote() );
        storageUnitDTO.setCategories( categorySetToCategoryDTOSet( entity.getCategories() ) );

        return storageUnitDTO;
    }

    @Override
    public List<StorageUnit> toEntity(List<StorageUnitDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StorageUnit> list = new ArrayList<StorageUnit>( dtoList.size() );
        for ( StorageUnitDTO storageUnitDTO : dtoList ) {
            list.add( toEntity( storageUnitDTO ) );
        }

        return list;
    }

    @Override
    public List<StorageUnitDTO> toDto(List<StorageUnit> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StorageUnitDTO> list = new ArrayList<StorageUnitDTO>( entityList.size() );
        for ( StorageUnit storageUnit : entityList ) {
            list.add( toDto( storageUnit ) );
        }

        return list;
    }

    @Override
    public List<StorageUnitDTO> toDto(Set<StorageUnit> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StorageUnitDTO> list = new ArrayList<StorageUnitDTO>( entityList.size() );
        for ( StorageUnit storageUnit : entityList ) {
            list.add( toDto( storageUnit ) );
        }

        return list;
    }

    protected Set<Category> categoryDTOSetToCategorySet(Set<CategoryDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Category> set1 = new HashSet<Category>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CategoryDTO categoryDTO : set ) {
            set1.add( categoryMapper.toEntity( categoryDTO ) );
        }

        return set1;
    }

    protected Set<CategoryDTO> categorySetToCategoryDTOSet(Set<Category> set) {
        if ( set == null ) {
            return null;
        }

        Set<CategoryDTO> set1 = new HashSet<CategoryDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Category category : set ) {
            set1.add( categoryMapper.toDto( category ) );
        }

        return set1;
    }
}
