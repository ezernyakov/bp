package ru.bp.stub.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Converter<U, V> {
    Logger log = LoggerFactory.getLogger(Converter.class);

    U toEx(V event) throws Exception;

    V fromEx(U json) throws Exception;

    default U to(V event) {
        try {
            return toEx(event);
        } catch (Exception e) {
            log.error("Can not convert server event to JSON", e);
        }
        return null;
    }

    default V from(U json) {
        try {
            return fromEx(json);
        } catch (Exception e) {
            log.error("Can not convert JSON to server event", e);
        }
        return null;
    }
}
