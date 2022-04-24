package rireport.ridev.com.br.Inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.ItemBuilder;
import rireport.ridev.com.br.Libs.SkullUtils;
import rireport.ridev.com.br.Libs.Sound;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

import java.util.LinkedList;
import java.util.List;

public class ReportsInventory extends PagedInventory {
    public ReportsInventory() {
        super("inv.reports", FancyText.colored("&7Reports"), 9 * 6);
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(28);
    }


    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        Player p = viewer.getPlayer();
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
    for(User us : Backend.getBackend().getAllUsers()) {
        if(!us.getPuniments().isEmpty()) {
            itens.add(() -> {
                ItemBuilder it = new ItemBuilder(SkullUtils.getHead(us.getUsername()));
                it.setName("&a" + us.getUsername());
                it.addLore("&c&lDIREITO &7para teletransportar").addLore("&a&lESQUERDO &7para ver informações do jogador").addLore("&r").addLore("&eDenuncias: ").addLore("&r");
                for (Puniments s : us.getPuniments()) {
                    it.addLore(FancyText.colored("&e ● &7" + s.getReason()));
                }
                return InventoryItem.of(it.build()).callback(ClickType.LEFT, r -> {
                    new PlayerInfo(us).openInventory(p);
                    Sound.VILLAGER_YES.play(p, 10, 10);
                }).callback(ClickType.RIGHT, r -> {
                    Player toTp = Bukkit.getPlayerExact(us.getUsername());
                    if (toTp != null) {
                        p.teleport(toTp);
                        p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("teleported")));
                        Sound.VILLAGER_YES.play(p, 10, 10);
                    } else {
                        p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("offline")));
                        Sound.VILLAGER_NO.play(p, 10, 10);
                    }
                });
            });
        }
    }
        return itens;
    }
}
