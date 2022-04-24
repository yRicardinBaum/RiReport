package rireport.ridev.com.br.Permission;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPerms extends Permission {

    public net.luckperms.api.LuckPerms api;

    public LuckPerms() {
        RegisteredServiceProvider<net.luckperms.api.LuckPerms> provider = Bukkit.getServicesManager().getRegistration(net.luckperms.api.LuckPerms.class);
        if (provider != null) {
            this.api = provider.getProvider();
        }
    }

    @Override
    public boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void addPermission(Player player, String permission) {
        User us = this.api.getPlayerAdapter(Player.class).getUser(player);
        us.data().add(Node.builder(permission).build());
        this.api.getUserManager().saveUser(us);
    }

    @Override
    public void removePermission(Player player, String permission) {
        User us = this.api.getPlayerAdapter(Player.class).getUser(player);
        us.data().remove(Node.builder(permission).build());
        this.api.getUserManager().saveUser(us);
    }
}
