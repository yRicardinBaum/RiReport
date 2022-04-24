package rireport.ridev.com.br.Events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.JSONMessage;
import rireport.ridev.com.br.Libs.MineReflect;
import rireport.ridev.com.br.Libs.Sound;
import rireport.ridev.com.br.Permission.Permission;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatEvent implements Listener {
    @Getter
    private static final HashMap<Player, String> customReport = new HashMap<>();
    @EventHandler(priority = EventPriority.MONITOR)
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(customReport.containsKey(p)) {
            if (customReport.get(p) != null) {
                e.setCancelled(true);
                User toAddReport = Backend.getBackend().getUser(customReport.get(p));
                if (toAddReport != null) {
                    Puniments pu = new Puniments();
                    pu.setAuthor(p.getName());
                    pu.setReason(e.getMessage());
                    Backend.getBackend().addReport(toAddReport, pu);
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("reported")));
                    for (Player pls : Bukkit.getOnlinePlayers()) {
                        if (Permission.getInstance().hasPermission(pls, ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission"))) {
                            Sound.NOTE_PLING.play(p, 10, 10);
                            MineReflect.sendTitle(pls, FancyText.colored("&3ATENÇÃO!"), FancyText.colored("&eNovo report!"), 20, 20, 10);
                            JSONMessage message = new JSONMessage("\n\n\n\n\n\n&aNovo report!\n\n");
                            message.addExtra(new JSONMessage.ChatExtra("&7Autor: &a" + p.getName() + "\n"));
                            message.addExtra(new JSONMessage.ChatExtra("\n&7Denunciado: &c" + customReport.get(p)+ "\n"));
                            message.addExtra(new JSONMessage.ChatExtra("&e&lCLIQUE AQUI ").addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/playerinfo " + toAddReport.getUsername()));
                            message.addExtra(new JSONMessage.ChatExtra("&epara verificar esse report"));
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    "tellraw " + pls.getName() + " " + message);
                        }
                    }
                    customReport.remove(p);
                } else {
                    toAddReport = new User();
                    toAddReport.setUsername(customReport.get(p));
                    ArrayList<Puniments> pn = new ArrayList<>();
                    Puniments pu = new Puniments();
                    pu.setAuthor(p.getName());
                    pu.setReason(e.getMessage());
                    pn.add(pu);
                    toAddReport.setPuniments(pn);
                    Backend.getBackend().insertUser(toAddReport);
                    Sound.NOTE_PLING.play(p, 10, 10);
                    p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("reported")));
                    for (Player pls : Bukkit.getOnlinePlayers()) {
                        if (Permission.getInstance().hasPermission(pls, ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission"))) {
                            MineReflect.sendTitle(pls, FancyText.colored("&3ATENÇÃO!"), FancyText.colored("&eNovo report!"), 10, 10, 10);
                            JSONMessage message = new JSONMessage("\n\n\n\n\n\n&aNovo report!\n\n");
                            message.addExtra(new JSONMessage.ChatExtra("&7Autor: &a" + p.getName() + "\n"));
                            message.addExtra(new JSONMessage.ChatExtra("\n&7Denunciado: &c" + customReport.get(p) + "\n"));
                            message.addExtra(new JSONMessage.ChatExtra("&e&lCLIQUE AQUI ").addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/playerinfo " + toAddReport.getUsername()));
                            message.addExtra(new JSONMessage.ChatExtra("&epara verificar reports"));
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/tellraw " + pls.getName() + " " + message);
                            pls.sendMessage(FancyText.colored(message.toString()));
                        }
                    }
                    customReport.remove(p);
                }
            } else {
                p.sendMessage(FancyText.colored("&cOcorreu um erro ao tentar reportar este usuário, verifique se ele está realmente online!"));
            }
        }
    }
}
