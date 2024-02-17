package me.u8092.brawl.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {
    public static ItemStack item(Component name, List<Component> lore, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack potion(Component name, PotionEffectType potionEffectType, int duration, int amplifier) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier);
        potionMeta.addCustomEffect(potionEffect, true);

        potion.setItemMeta(potionMeta);
        return potion;
    }

    public static ItemStack enchantedItem(Component name, List<Component> lore, Material material, Enchantment enchantment, int enchantmentLevel) {
        ItemStack enchantedItem = new ItemStack(material);
        ItemMeta enchantedItemMeta = enchantedItem.getItemMeta();
        enchantedItemMeta.displayName(name);
        enchantedItemMeta.lore(lore);
        enchantedItemMeta.addEnchant(enchantment, enchantmentLevel, true);

        enchantedItem.setItemMeta(enchantedItemMeta);

        return enchantedItem;
    }

    public static List<ItemStack> enchantedArmor(Component name, List<Component> lore, ArmorType armorType, Enchantment enchantment, int enchantmentLevel) {
        List<ItemStack> armor = new ArrayList<>();

        Material helmet = Material.getMaterial(armorType + "_HELMET");
        Material chestplate = Material.getMaterial(armorType + "_CHESTPLATE");
        Material leggings = Material.getMaterial(armorType + "_LEGGINGS");
        Material boots = Material.getMaterial(armorType + "_BOOTS");

        armor.add(0, enchantedItem(name, lore, helmet, enchantment, enchantmentLevel));
        armor.add(1, enchantedItem(name, lore, chestplate, enchantment, enchantmentLevel));
        armor.add(2, enchantedItem(name, lore, leggings, enchantment, enchantmentLevel));
        armor.add(3, enchantedItem(name, lore, boots, enchantment, enchantmentLevel));

        return armor;
    }
}
