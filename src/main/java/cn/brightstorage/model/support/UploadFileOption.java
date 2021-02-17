package cn.brightstorage.model.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileOption {

    /**
     * 相对上传文件根目录下的路径
     * root: .zhicun/uploads
     * path: [img]
     * 对应 .zhicun/uploads/img
     */
    private String[] path;

    /**
     * 是否压缩原图
     */
    private Boolean compress;

    /**
     * 是否生成缩略图
     */
    private Boolean thumbnail;

    /**
     * 如果生成缩略图，缩略图的宽度
     */
    private int width;

    /**
     * 如果生成缩略图，缩略图的高度
     */
    private int height;

}
