package cn.brightstorage.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/ws/{rid}")
public class WebSocketServer {

    private static final Map<Long, Set<WebSocketServer>> clients = new ConcurrentHashMap<>();

    private Session session;

    private Long rid;

    @OnOpen
    public void onOpen(Session session, @PathParam("rid") Long rid){
        log.info("connected, rid:{}, id:{}", rid, session.getId());
        this.session = session;
        this.rid = rid;
    }

    @OnClose
    public void onClose(Session session) {
        log.info("Disconnected, rid:{}, id:{}", rid, session.getId());
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("Received, rid:{}, id:{}, message:{}", rid, session.getId(), message);
    }

}
