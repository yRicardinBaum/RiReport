package rireport.ridev.com.br.Permission;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Libs.ModuleLogger;


public abstract class Permission {
    public static final ModuleLogger LOGGER = new ModuleLogger("RiReport Permission");
    @Getter
    private static Permission instance;


    public abstract boolean hasPermission(Player player, String permission);

    public abstract void addPermission(Player player, String permission);

    public abstract void removePermission(Player player, String permission);


    public static void loadPermissions() {
            if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
                instance = new LuckPerms();
                LOGGER.severe("Luck perms encontrado...");
            } else {
                instance = new Default();
                LOGGER.severe("Luck perms não foi encontrado no servidor! Usando api do Bukkit");
            }
    }

}
