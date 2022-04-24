package rireport.ridev.com.br.Commands.User;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Commands.Commands;
import rireport.ridev.com.br.Inventory.TypesInventory;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.Sound;
import rireport.ridev.com.br.Main;
import rireport.ridev.com.br.Permission.Permission;

public class Report extends Commands {
    public Report() {
        super("report", "reportar");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if(ConfigLanguage.get(ConfigLanguage::commands).getString("report.permission").isEmpty() || Permission.getInstance().hasPermission(p, ConfigLanguage.get(ConfigLanguage::commands).getString("report.permission"))) {
            if(args.length == 0) {
                p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("not-mencioned")));
                Sound.VILLAGER_NO.play(p, 10, 10);
            } else {
                Player player = Bukkit.getPlayerExact(args[0]);
                if(player != null){
                    if(player.equals(p)) {
                        p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("myself")));
                        Sound.VILLAGER_NO.play(p, 10, 10);
                    } else {
                        new TypesInventory().openInv(p, args[0]);
                        Sound.NOTE_PLING.play(p, 10, 10);
                    }
                } else {
                 p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("offline")));
                    Sound.VILLAGER_NO.play(p, 10, 10);
                }
            }
        } else {
            p.sendMessage(FancyText.colored("&6RiReport &7[" + Main.getInstance().getDescription().getVersion() + "] &f- &7Criado por &5yRicardinBaumDEV&7."));
            Sound.VILLAGER_NO.play(p, 10, 10);
        }
    }
}
