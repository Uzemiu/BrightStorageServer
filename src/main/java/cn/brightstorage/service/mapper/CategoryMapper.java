package cn.brightstorage.service.mapper;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.StorageUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper extends BaseMapper<CategoryDTO, Category> {

    @Override
    @Mapping(source = "storageUnits", target = "count", qualifiedByName = "toCount")
    CategoryDTO toDto(Category entity);

    @Named("toCount")
    default Long toCount(Set<StorageUnit> storageUnits){
        return -1L;
    }
}
