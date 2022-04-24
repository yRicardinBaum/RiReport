package rireport.ridev.com.br.Libs;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author yRicardinBaumDEV
 * @description Uma api de criar itens customizados!
 */

public class ItemBuilder {


    private Material material;
    private DyeColor color;
    private List<String> lores;
    private final HashMap<Enchantment, Integer> efeitos;
    private String name;
    private int quantity;
    private int damage;
    private boolean unbreakable;
    private ItemStack item;



    public ItemBuilder(Material material) {
        this.material = material;
        this.unbreakable = false;
        this.name = "";
        this.lores = new ArrayList<>();
        this.efeitos = new HashMap<>();
        this.quantity = 1;
        this.damage = -1;
        this.item = null;
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.unbreakable = false;
        this.name = "";
        this.lores = new ArrayList<>();
        this.efeitos = new HashMap<>();
        this.quantity = 1;
        this.damage = -1;
    }

    public ItemBuilder() {
        this.unbreakable = false;
        this.name = "";
        this.lores = new ArrayList<>();
        this.efeitos = new HashMap<>();
        this.quantity = 1;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }


    public DyeColor getColor() {
        return color;
    }

    public ItemBuilder setColor(DyeColor color) {
        this.color = color;
        return this;
    }

    public List<String> getLores() {
        return lores;
    }

    public ItemBuilder setLores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public HashMap<Enchantment, Integer> getEfeitos() {
        return efeitos;
    }

    public String getName() {
        return name;
    }

    public ItemBuilder setName(String name) {
        this.name = FancyText.colored(name);
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
    public ItemBuilder addLore(String lore) {
        this.lores.add(FancyText.colored(lore));
        return this;
    }

    public ItemBuilder addLores(String... lores) {
        this.lores = Arrays.asList(FancyText.colored(lores));
        return this;
    }


    public ItemBuilder addLores(List<String> lores) {
        for(String s : lores){
            this.lores.add(FancyText.colored(s));
        }
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public ItemBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }


    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack build() {
        ItemStack item;

        if (this.color != null) {
            if (this.item != null) {
                item = new ItemStack(this.item.getType(), this.item.getAmount(), this.color.getData());
                item.setItemMeta(this.item.getItemMeta());
            } else {
                item = new ItemStack(this.material, this.quantity, this.color.getData());
            }
            ItemMeta meta = item.getItemMeta();
            if (!efeitos.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> efeitos : this.efeitos.entrySet()) {
                    meta.addEnchant(efeitos.getKey(), efeitos.getValue(), true);
                }
            }
            meta.setDisplayName(this.name);
            if (!this.lores.isEmpty()) {
                meta.setLore(this.lores);
            }
            if (this.unbreakable) meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
        } else if (this.damage != -1) {

            if (this.item != null) {
                item = new ItemStack(this.item.getType(), this.item.getAmount(), (byte) this.damage);
                item.setItemMeta(this.item.getItemMeta());
            } else {
                item = new ItemStack(this.material, this.quantity, (byte) this.damage);
            }
            ItemMeta meta = item.getItemMeta();

            item.setAmount(this.quantity);
            meta.setDisplayName(this.name);
            if (!efeitos.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> efeitos : this.efeitos.entrySet()) {
                    meta.addEnchant(efeitos.getKey(), efeitos.getValue(), true);
                }
            }

            if (!this.lores.isEmpty()) {
                meta.setLore(this.lores);
            }
            if (this.unbreakable) meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
        } else {
            if (this.item != null) {
                item = this.item;

            } else {
                item = new ItemStack(this.material);

            }
            ItemMeta meta = item.getItemMeta();
            item.setAmount(this.quantity);
            meta.setDisplayName(this.name);
            if (!efeitos.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> efeitos : this.efeitos.entrySet()) {
                    meta.addEnchant(efeitos.getKey(), efeitos.getValue(), true);
                }
            }
            if (!this.lores.isEmpty()) {
                meta.setLore(this.lores);
            }
            if (this.unbreakable) meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
        }

        return item;
    }


}
