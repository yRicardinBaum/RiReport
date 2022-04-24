package rireport.ridev.com.br.Commands;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Commands.Admin.PlayerInfo;
import rireport.ridev.com.br.Commands.Admin.Reports;
import rireport.ridev.com.br.Commands.User.Report;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.ModuleLogger;


import java.util.Arrays;
import java.util.logging.Level;

public abstract class Commands extends Command {

    public static final ModuleLogger LOGGER = new ModuleLogger("RiPunish Commands");

    public Commands(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));

        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            simpleCommandMap.register(this.getName(), "RiKitPvP", this);
        } catch (ReflectiveOperationException ex) {
           LOGGER.log(Level.SEVERE, "Não foi possivel registrar o comando: ", ex);
        }
    }

    public abstract void perform(Player p, String label, String[] args);

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) this.perform((Player) sender, commandLabel, args);
        return true;
    }

    public static void setupCommands() {
        if(ConfigLanguage.get(ConfigLanguage::commands).get("report.enable") != null && ConfigLanguage.get(ConfigLanguage::commands).getBoolean("report.enable")) {
            new Report();
        }
        if(ConfigLanguage.get(ConfigLanguage::commands).get("reports.enable") != null && ConfigLanguage.get(ConfigLanguage::commands).getBoolean("reports.enable")) {
            new Reports();
        }

        if(ConfigLanguage.get(ConfigLanguage::commands).get("playerinfo.enable") != null && ConfigLanguage.get(ConfigLanguage::commands).getBoolean("playerinfo.enable")) {
            new PlayerInfo();
        }
    }

}
