package rireport.ridev.com.br.Starters;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Language.TypeLanguage;
import rireport.ridev.com.br.Libs.ModuleLogger;

import java.util.logging.Level;

@Data(staticConstructor = "of")
public class LanguageStarter {
    public static ModuleLogger LOGGER = new ModuleLogger("RiReport Language Starter");
    private final JavaPlugin plugin;

    public void init() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);
        try {
            configurationInjector.saveDefaultConfiguration(
                    plugin,
                    "config.yml",
                    "types.yml"
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao tentar carregar os arquivos de configuração!", e);
        } finally {

            configurationInjector.injectConfiguration(
                    ConfigLanguage.instance(),
                    TypeLanguage.instance()
            );
            LOGGER.log(Level.FINE, "Arquivos de configuração carregados com sucesso!");
        }
    }
}
