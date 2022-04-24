package rireport.ridev.com.br.Inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.impl.paged.PagedInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.supplier.InventoryItemSupplier;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.paged.PagedViewer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rireport.ridev.com.br.Language.ConfigLanguage;
import rireport.ridev.com.br.Libs.FancyText;
import rireport.ridev.com.br.Libs.ItemBuilder;
import rireport.ridev.com.br.Libs.SkullUtils;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

import java.util.LinkedList;
import java.util.List;

public class PlayerInfo extends PagedInventory {
    private final User us;
    public PlayerInfo(User toSee) {
        super("player.info", FancyText.colored("&7Informações de " + toSee.getUsername()), 9 * 6);
        this.us = toSee;
    }

    @Override
    protected void configureViewer(PagedViewer viewer) {
        ViewerConfigurationImpl.Paged configuration = viewer.getConfiguration();
        configuration.itemPageLimit(21);
    }

    @Override
    protected List<InventoryItemSupplier> createPageItems(@NotNull PagedViewer viewer) {
        LinkedList<InventoryItemSupplier> itens = new LinkedList<>();
        Player p = viewer.getPlayer();
        if(!this.us.getPuniments().isEmpty()) {
            for (Puniments pn : this.us.getPuniments()) {
                itens.add(() ->
                        InventoryItem.of(new ItemBuilder(SkullUtils.getSkull("a8f7f16235df227a6a198dc9a0149ffc66e9cff0e07fac23817c0b9e8126ccb9")).setName(FancyText.colored("&e" + pn.getReason())).addLore("").addLore("&7Autor da denuncia: " + pn.getAuthor()).addLore("").addLore("&e&lCLIQUE &epara remover denuncia!").build()).defaultCallback(r -> {
                            Backend.getBackend().removeReport(this.us, pn);
                            p.sendMessage(FancyText.colored(ConfigLanguage.get(ConfigLanguage::messages).getString("removed-user")));
                            this.updateInventory(p);
                        }));
            }
        }

        return itens;
    }
}
