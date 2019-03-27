package ru.bp.websocket.stubs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * серверное событие
 */
public class ServerEventImpl implements ServerEvent {

    private Object eventPayload;
    private String eventTypeName;

    public ServerEventImpl(ServerEventType type, Object pld) {
        //stub
    }

    @Override
    public boolean hasType(ServerEventType type) {
        // stub
        return false;
    }

    @Override
    public <T> T getPayloadAs(Class<T> type) {
        // stub
        return type.cast(eventPayload);
    }

    @JsonCreator
    private ServerEventImpl(@JsonProperty("type") String typeName, @JsonProperty("payload") Object payload) {
        //stub
        eventTypeName = typeName;
        eventPayload = payload;
    }

    public String getType() {
        return eventTypeName;
    }

    public Object getPayload() {
        return eventPayload;
    }

    @JsonIgnore
    public static ServerEventImpl buildServerEvent(ServerEventType type, Object pld) {
        return new ServerEventImpl(type, pld);
    }
}
