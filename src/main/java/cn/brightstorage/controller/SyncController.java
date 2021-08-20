package cn.brightstorage.controller;

import cn.brightstorage.model.entity.OperationLog;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;

    @PostMapping("/push")
    public BaseResponse<?> push(@RequestBody List<OperationLog> operationLogs){
        return BaseResponse.ok("ok", syncService.push(operationLogs));
    }

    @PostMapping("/pull")
    public BaseResponse<?> pull(@RequestBody Long version){
        return BaseResponse.ok("ok", syncService.pull(version));
    }
}
