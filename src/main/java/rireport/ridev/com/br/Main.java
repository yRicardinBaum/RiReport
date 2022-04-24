package rireport.ridev.com.br;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rireport.ridev.com.br.Commands.Commands;
import rireport.ridev.com.br.Events.ChatEvent;
import rireport.ridev.com.br.Events.Left;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Language.TypeLanguage;
import rireport.ridev.com.br.Libs.ModuleLogger;
import rireport.ridev.com.br.Permission.Permission;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.Starters.LanguageStarter;
import rireport.ridev.com.br.Starters.TypesStarter;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiReport");

    @Getter
    private static Main instance;

    @Override
    public void onLoad(){
        instance = this;
        LanguageStarter.of(this).init();
        Backend.loadBackend(ConfigLanguage.get(ConfigLanguage::database));
        TypesStarter.of(TypeLanguage.get(TypeLanguage::types)).init();
    }

    @Override
    public void onEnable() {
        Permission.loadPermissions();
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Left(), this);
        Commands.setupCommands();
        LOGGER.log(Level.FINE, "\\033[0;32m ______  _ ______                           _   \n" +
                "\\033[0;32m| ___ \\(_)| ___ \\                         | |  \n" +
                "\\033[0;32m| |_/ / _ | |_/ / ___  _ __    ___   _ __ | |_ \n" +
                "\\033[0;32m|    / | ||    / / _ \\| '_ \\  / _ \\ | '__|| __|\n" +
                "\\033[0;32m| |\\ \\ | || |\\ \\|  __/| |_) || (_) || |   | |_ \n" +
                "\\033[0;32m\\_| \\_||_|\\_| \\_|\\___|| .__/  \\___/ |_|    \\__|\n" +
                "\\033[0;32m                      | |                      \n" +
                "\\033[0;32m                      |_|                      \n" +
                "\\033[0;32m\n" +
                "\n" +
                "\\033[0;32m        PLUGIN HABILITADO            "
                );
    }

    @Override
    public void onDisable() {
        LOGGER.log(Level.FINE,
                "\\033[0;31m______  _ ______                           _   \n" +
                "\\033[0;31m| ___ \\(_)| ___ \\                         | |  \n" +
                "\\033[0;31m| |_/ / _ | |_/ / ___  _ __    ___   _ __ | |_ \n" +
                "\\033[0;31m|    / | ||    / / _ \\| '_ \\  / _ \\ | '__|| __|\n" +
                "\\033[0;31m| |\\ \\ | || |\\ \\|  __/| |_) || (_) || |   | |_ \n" +
                "\\033[0;31m\\_| \\_||_|\\_| \\_|\\___|| .__/  \\___/ |_|    \\__|\n" +
                "\\033[0;31m                      | |                      \n" +
                "\\033[0;31m                      |_|                      \n" +
                "\\033[0;31m\n" +
                "\\033[0;31m\n" +
                "\\033[0;31m        PLUGIN DESABILITADO            "
        );
    }
}
