package rireport.ridev.com.br.Commands.Admin;

import org.bukkit.entity.Player;
import rireport.ridev.com.br.Commands.Commands;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.Sound;
import rireport.ridev.com.br.Main;
import rireport.ridev.com.br.Permission.Permission;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

public class PlayerInfo extends Commands {

    public PlayerInfo() {
        super("playerinfo", "reportinfo");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if(ConfigLanguage.get(ConfigLanguage::commands).getString("playerinfo.permission").isEmpty() || Permission.getInstance().hasPermission(p, ConfigLanguage.get(ConfigLanguage::commands).getString("playerinfo.permission"))) {
            if(args.length == 0){
                p.sendMessage(FancyText.colored("&c/playerinfo (player)"));
                Sound.VILLAGER_NO.play(p, 10, 10);
            } else {
                User us = Backend.getBackend().getUser(args[0]);
                if(us != null) {
                    if(us.getPuniments().isEmpty()) {
                        p.sendMessage(FancyText.colored("&cEste usuário não pussui denuncias!"));
                        Sound.VILLAGER_NO.play(p, 10, 10);
                    } else {
                        Sound.NOTE_PLING.play(p, 10, 10);
                        new rireport.ridev.com.br.Inventory.PlayerInfo(us).openInventory(p);
                    }
                } else {
                    Sound.VILLAGER_NO.play(p, 10, 10);
                    p.sendMessage(FancyText.colored("&cO usuário citado não existe em meu banco de dados!"));
                }

            }
        } else {
            p.sendMessage(FancyText.colored("&6RiReport &7[" + Main.getInstance().getDescription().getVersion() + "] &f- &7Criado por &5yRicardinBaumDEV&7."));
            Sound.VILLAGER_NO.play(p, 10, 10);
        }
    }
}
