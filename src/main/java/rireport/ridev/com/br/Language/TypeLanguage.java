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
@ConfigFile("types.yml")
public class TypeLanguage implements ConfigurationInjectable {

    @Getter
    private static final TypeLanguage instance = new TypeLanguage();


    @ConfigField("types")
    private ConfigurationSection types;

    public static <T> T get(Function<TypeLanguage, T> function) {
        return function.apply(instance);
    }
}
