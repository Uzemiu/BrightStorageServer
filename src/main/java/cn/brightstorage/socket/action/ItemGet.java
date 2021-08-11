package cn.brightstorage.socket.action;

import cn.brightstorage.service.StorageUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ItemGet implements Function<Object, Object> {

    private final StorageUnitService storageUnitService;

    @Override
    public Object apply(Object o) {

        return null;
    }
}
