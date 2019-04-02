package ru.bp.configuration;

import java.io.File;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {
    private static final String DEFAULT_PROPS_FILE_NAME = "testing.properties";
    private static ClassLoader classLoader = Config.class.getClassLoader();
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private static CombinedConfiguration configuration = loadConfigurations();

    public static CombinedConfiguration getConfiguration() {
        return configuration;
    }

    private static CombinedConfiguration loadConfigurations() {
        File homeTestingProperties = new File(System.getProperty("user.home") + File.separator + DEFAULT_PROPS_FILE_NAME);

        Parameters params = new Parameters();

        // Конфигурация в testing.properties внутри проекта
        FileBasedConfigurationBuilder<FileBasedConfiguration> defaultProperties =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setURL(classLoader.getResource(DEFAULT_PROPS_FILE_NAME)));

        // Конфигурация в testing.properties в $HOME пользователя
        FileBasedConfigurationBuilder<FileBasedConfiguration> homeProperties =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFile(homeTestingProperties));


        CombinedConfiguration configuration = new CombinedConfiguration();
        try {
            // Подгружаем системные переменные (максимальный приоритет)
            configuration.addConfiguration(new FallbackSystemConfiguration());

            // Подгружаем из файлов (раньше загружен - настройки более приоритетны)
            configuration.addConfiguration(homeProperties.getConfiguration());
            configuration.addConfiguration(defaultProperties.getConfiguration());
        } catch (ConfigurationException e) {
            log.error("Cannot load configuration", e);

            // Не смогли прочитать конфигурацияю - показали ошибку и завершаем выполнение тестов
            System.exit(1);
        }
        return configuration;
    }
}