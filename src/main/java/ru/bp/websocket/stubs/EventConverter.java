package ru.bp.websocket.stubs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class EventConverter implements Converter<String, ServerEvent> {

    private static EventConverter instance;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static EventConverter instance() {
        if (instance == null) {
            instance = new EventConverter();
        }
        return instance;
    }

    @Override
    public String toEx(ServerEvent event) throws JsonProcessingException {
        //stub
        return mapper.writeValueAsString(event);
    }

    @Override
    public ServerEvent fromEx(String json) throws IOException {
        //stub
        return mapper.readValue(json, ServerEventImpl.class);
    }

    @Override
    public String to(ServerEvent event) {
        return null;
    }

    @Override
    public ServerEvent from(String json) {
        return null;
    }
}
