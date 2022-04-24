package rireport.ridev.com.br.Libs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;

public class SkullUtils {

    public static ModuleLogger LOGGER = new ModuleLogger("RiPunish SkullGetter");

    public static ItemStack getHead(final Player jogador) {
        final ItemStack kCabeca = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta mCabeca = (SkullMeta) kCabeca.getItemMeta();
        mCabeca.setOwner(jogador.getName());
        mCabeca.setDisplayName(jogador.getName());
        kCabeca.setItemMeta(mCabeca);
        return kCabeca;
    }

    public static ItemStack getHead(final String jogador) {
        final ItemStack kCabeca = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta mCabeca = (SkullMeta) kCabeca.getItemMeta();
        mCabeca.setOwner(jogador);
        mCabeca.setDisplayName(jogador);
        kCabeca.setItemMeta(mCabeca);
        return kCabeca;
    }

    public static ItemStack getSkull(String link) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures",
                new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + "http://textures.minecraft.net/texture/" + link + "\"}}}")));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao tentar pegar Skull!", ex);
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
