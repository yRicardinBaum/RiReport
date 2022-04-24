package rireport.ridev.com.br.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Left implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void left(PlayerQuitEvent e) {
        Player p =e.getPlayer();
        ChatEvent.getCustomReport().remove(p);
    }
}
