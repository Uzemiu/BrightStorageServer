package cn.brightstorage.config;

import cn.brightstorage.model.support.UploadFileOption;
import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "bright-storage.file")
public class UploadFileConfig {

    private String root;

    private String virtual;

    private Map<String, UploadFileOption> options;

    public UploadFileOption get(String name){
        return options.get(name);
    }

    public void setRoot(String[] root){
        this.root = ArrayUtil.join(root, File.separator);
    }
}
