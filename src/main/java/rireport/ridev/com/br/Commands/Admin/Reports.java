package rireport.ridev.com.br.Commands.Admin;

import org.bukkit.entity.Player;
import rireport.ridev.com.br.Commands.Commands;
import rireport.ridev.com.br.Inventory.ReportsInventory;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.Sound;
import rireport.ridev.com.br.Main;
import rireport.ridev.com.br.Permission.Permission;

public class Reports extends Commands {
    public Reports() {
        super("reports", "reportlist");
    }

    @Override
    public void perform(Player p, String label, String[] args) {
        if(ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission").isEmpty() || Permission.getInstance().hasPermission(p, ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission"))) {
            new ReportsInventory().openInventory(p);
        } else {
            p.sendMessage(FancyText.colored("&6RiReport &7[" + Main.getInstance().getDescription().getVersion() + "] &f- &7Criado por &5yRicardinBaumDEV&7."));
            Sound.VILLAGER_NO.play(p, 10, 10);
        }
    }
}
