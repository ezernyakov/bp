package ru.bp.websocket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import ru.bp.websocket.stubs.Converter;
import ru.bp.websocket.stubs.EventConverter;
import ru.bp.websocket.stubs.ServerEvent;
import ru.bp.websocket.stubs.ServerEventType;

public class ResponseReader {
    private static final Converter<String, ServerEvent> converter = EventConverter.instance();

    public <E> E deserializeEvent(ServerEventType eventType, Class<E> type, BlockingQueue<String> events ) {

        while (true) {
            try {
                String message = events.poll(10, TimeUnit.SECONDS);
                ServerEvent event = converter.from(message);
                if (message == null) {
                    throw new AssertionError("Timed out waiting for event " + eventType.getName());
                } else if (event.hasType(eventType)) {
                    return event.getPayloadAs(type);
                }

            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }
    }
}
