package ru.bp.configuration;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.MapConfiguration;

public class FallbackSystemConfiguration extends MapConfiguration {

    private Map<String, String> PROPERTY_MAPPING;

    public FallbackSystemConfiguration() {
        super(System.getProperties());
        initializePropertyMapping();
        convertProperties();
    }

    private void initializePropertyMapping() {
        PROPERTY_MAPPING = new HashMap<>();

        PROPERTY_MAPPING.put("server_host_ip", "server.host.ip");
        PROPERTY_MAPPING.put("os_type", "os.type");
    }

    private void convertProperties() {
        for (Map.Entry<String, String> entry : PROPERTY_MAPPING.entrySet()) {
            String propertyValue = (String) map.get(entry.getKey());
            if (propertyValue != null) {
                map.put(entry.getValue(), propertyValue);
                map.remove(entry.getKey());
            }
        }
    }

}