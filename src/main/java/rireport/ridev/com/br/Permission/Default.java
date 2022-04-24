package rireport.ridev.com.br.Permission;

import org.bukkit.entity.Player;
import rireport.ridev.com.br.Main;

public class Default extends Permission {
    @Override
    public boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void addPermission(Player player, String permission) {
        player.addAttachment(Main.getInstance()).setPermission(permission, true);
    }

    @Override
    public void removePermission(Player player, String permission) {
        player.addAttachment(Main.getInstance()).unsetPermission(permission);
    }
}
