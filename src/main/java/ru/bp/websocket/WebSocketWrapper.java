package ru.bp.websocket;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bp.websocket.stubs.Converter;
import ru.bp.websocket.stubs.EventConverter;
import ru.bp.websocket.stubs.ServerEvent;
import ru.bp.websocket.stubs.ServerEventFactory;
import ru.bp.websocket.stubs.ServerEventType;
import ru.bp.websocket.stubs.ServerEventTypeFactory;


public class WebSocketWrapper extends WebSocketListener {

    private static final Logger log = LoggerFactory.getLogger(WebSocketWrapper.class);
    private static final Converter<String, ServerEvent> converter = EventConverter.instance();
        private static final ResponseReader READER = new ResponseReader();
        private static final BlockingQueue<String> events = new LinkedBlockingQueue<>();

        private static WebSocket socket;
        private static final String url = "<url>";

        @Before(value = "@WebSockets")
        public void initConnection() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        log.info("Initializing connection to socket");
        socket = client.newWebSocket(request, this);
    }

        @After(value = "@WebSockets")
        public void closeConnection() {
        logout();
        log.info("closing connection with socket");
        socket.close(1000, "Normal shutdown");
        socket.cancel();
    }

        private void logout() {
        ServerEvent event = ServerEventFactory.userLogout(new UserLogoutPld());
        send(event);
        receive(ServerEventTypeFactory.formLoginShow(), FormLoginShowPld.class);

        event = ServerEventFactory.startCfgGet();
        send(event);
        receive(ServerEventTypeFactory.startCfgPut(), StartCfgPutPld.class);
    }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
        events.add(text);
        System.out.println("Socket onMessage: " + text);
    }

        public <T> T receive(ServerEventType type, Class<T> clazz) {
        return READER.deserializeEvent(type, clazz, events);
    }

        public void send(ServerEvent event) {
        String json = converter.to(event);
        socket.send(json);
    }
}
