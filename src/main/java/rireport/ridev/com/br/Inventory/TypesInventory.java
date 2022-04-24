package rireport.ridev.com.br.Inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rireport.ridev.com.br.Data.TypesData;
import rireport.ridev.com.br.Events.ChatEvent;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.*;
import rireport.ridev.com.br.Permission.Permission;
import rireport.ridev.com.br.Puniments.Puniment;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TypesInventory extends PagedInventory {
    public TypesInventory() {
        super("inv.type", FancyText.colored("&7Reports Types"), 9 * 6);
    }
    private String player;

    public void openInv(Player player, String denuncied) {
        this.player = denuncied;
        this.openInventory(player);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Player p = viewer.getPlayer();
            InventoryItem semArena = InventoryItem.of(
                    new ItemBuilder().setMaterial(Material.EXP_BOTTLE).setName("&cReport Customizado").addLores("&7Caso algum desses reports acima", "&7Não resolva seu problema", "&7Clique aqui para enviar um com suas palavras!").build()).defaultCallback(a ->{
                        User us = Backend.getBackend().getUser(this.player);
                        boolean hasReported = false;
                        if(us != null) {
                            for (Puniments pns : us.getPuniments()) {
                                if (pns.getAuthor().equals(p.getName())) {
                                    p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("has-reported")));
                                    Sound.VILLAGER_NO.play(p, 10, 10);
                                    hasReported = true;
                                    break;
                                }
                            }
                        }
                if(!hasReported) {
                    ChatEvent.getCustomReport().put(p, player);
                    Sound.VILLAGER_YES.play(p, 10, 10);
                    p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("custom-report")));
                    p.closeInventory();
                }
            });
            editor.setItem(40, semArena);
    }
    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        Player p = viewer.getPlayer();
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        for(Puniment types : TypesData.getPuniments()) {
            itens.add(() -> InventoryItem.of(new ItemBuilder(SkullUtils.getSkull("a8f7f16235df227a6a198dc9a0149ffc66e9cff0e07fac23817c0b9e8126ccb9")).setName("&a" + types.getReason()).addLore("&7Clique aqui para denunciar o usuário").addLore("&7Por &e" + types.getReason().toUpperCase()).build()).defaultCallback(r -> {
                if (this.player != null) {
                    User us = Backend.getBackend().getUser(this.player);
                    if (us != null) {
                        boolean hasReported = false;
                        for (Puniments pns : us.getPuniments()) {
                            if (pns.getAuthor().equals(p.getName())) {
                                p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("has-reported")));
                                Sound.VILLAGER_NO.play(p, 10, 10);
                                hasReported = true;
                                break;
                            }
                        }
                        if (!hasReported) {
                            Puniments pn = new Puniments();
                            pn.setAuthor(p.getName());
                            pn.setReason(types.getReason());
                            Backend.getBackend().addReport(us, pn);
                            p.closeInventory();
                            Sound.NOTE_PLING.play(p, 10, 10);
                            p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("reported")));
                            for (Player pls : Bukkit.getOnlinePlayers()) {
                                if (Permission.getInstance().hasPermission(pls, ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission"))) {
                                    Sound.NOTE_PLING.play(p, 10, 10);
                                    MineReflect.sendTitle(pls, FancyText.colored("&3ATENÇÃO!"), FancyText.colored("&eNovo report!"), 10, 10, 10);
                                    JSONMessage message = new JSONMessage("\n\n\n\n\n\n&aNovo report!\n\n");
                                    message.addExtra(new JSONMessage.ChatExtra("&7Autor: &a" + p.getName() + "\n"));
                                    message.addExtra(new JSONMessage.ChatExtra("\n&7Denunciado: &c" + p.getName() + "\n"));
                                    message.addExtra(new JSONMessage.ChatExtra("&e&lCLIQUE AQUI ").addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/playerinfo " + us.getUsername()));
                                    message.addExtra(new JSONMessage.ChatExtra("&epara verificar reports"));
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                            "tellraw " + pls.getName() + " " + message);
                                }
                            }
                        }
                    } else {
                        User newUser = new User();
                        Puniments pn = new Puniments();
                        pn.setAuthor(p.getName());
                        pn.setReason(types.getReason());
                        newUser.setUsername(this.player);
                        ArrayList<Puniments> puniments = new ArrayList<>();
                        puniments.add(pn);
                        newUser.setPuniments(puniments);
                        Backend.getBackend().insertUser(newUser);
                        p.closeInventory();
                        Sound.NOTE_PLING.play(p, 10, 10);
                        p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("reported")));
                        for (Player pls : Bukkit.getOnlinePlayers()) {
                            if (Permission.getInstance().hasPermission(pls, ConfigLanguage.get(ConfigLanguage::commands).getString("reports.permission"))) {
                                MineReflect.sendTitle(pls, FancyText.colored("&3ATENÇÃO!"), FancyText.colored("&eNovo report!"), 10, 10, 10);
                                JSONMessage message = new JSONMessage("\n\n\n\n\n\n&aNovo report!\n\n");
                                message.addExtra(new JSONMessage.ChatExtra("&7Autor: &a" + p.getName() + "\n"));
                                message.addExtra(new JSONMessage.ChatExtra("\n&7Denunciado: &c" + newUser.getUsername() + "\n"));
                                message.addExtra(new JSONMessage.ChatExtra("&e&lCLIQUE AQUI ").addClickEvent(JSONMessage.ClickEventType.RUN_COMMAND, "/playerinfo " + newUser .getUsername()));
                                message.addExtra(new JSONMessage.ChatExtra("&epara verificar reports"));
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "tellraw " + pls.getName() + " " + message);
                            }
                        }
                    }
                } else {
                    p.sendMessage(FancyText.colored("&cOcorreu um erro ao tentar reportar este usuário, verifique se ele está realmente online!"));
                    p.closeInventory();
                }
            }));
        }
        return itens;
    }
}
