package ru.bp.stub.server;


public interface ServerEvent {

    /**
     * Признак наличия указанного типа у этого события
     *
     * @param type - тип события
     */
    boolean hasType(ServerEventType type);

    /**
     * Полезная "нагрузка" события (данные)
     */
    <T> T getPayloadAs(Class<T> type);
}
