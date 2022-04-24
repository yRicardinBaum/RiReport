package rireport.ridev.com.br.Language;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;
@Getter
@Accessors(fluent = true)
@ConfigFile("config.yml")
public class ConfigLanguage implements ConfigurationInjectable {

    @Getter
    private static final ConfigLanguage instance = new ConfigLanguage();

    @ConfigField("database")
    private ConfigurationSection database;

    @ConfigField("commands")
    private ConfigurationSection commands;

    @ConfigField("messages")
    private ConfigurationSection messages;


    public static <T> T get(Function<ConfigLanguage, T> function) {
        return function.apply(instance);
    }
}
