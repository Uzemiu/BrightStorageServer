package cn.brightstorage.model.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO<DTO> {

    private List<DTO> content;

    private Integer total;

    public PageDTO(Page<DTO> page){
        content = page.getContent();
        total = page.getTotalPages();
    }
}
