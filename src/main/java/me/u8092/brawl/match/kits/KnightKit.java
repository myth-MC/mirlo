package me.u8092.brawl.match.kits;

import me.u8092.brawl.items.ArmorType;
import me.u8092.brawl.items.ItemCreator;
import me.u8092.brawl.match.Kit;
import me.u8092.brawl.match.KitHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class KnightKit {
    public KnightKit() {
        List<ItemStack> KNIGHT_ARMOR = ItemCreator.enchantedArmor(
                text("Guerrero"),
                Collections.singletonList(text("")),
                ArmorType.IRON,
                Enchantment.PROTECTION_PROJECTILE,
                1
        );

        ItemStack KNIGHT_SWORD = ItemCreator.enchantedItem(
                text("Guerrero"),
                Collections.singletonList(text("")),
                Material.IRON_SWORD,
                Enchantment.DAMAGE_ALL,
                1
        );

        List<ItemStack> KNIGHT_ITEMS = new ArrayList<>();
        KNIGHT_ITEMS.add(new ItemStack(Material.BOW));
        KNIGHT_ITEMS.add(new ItemStack(Material.ARROW, 16));
        KNIGHT_ITEMS.add(ItemCreator.potion(
                text("Guerrero"),
                PotionEffectType.HEAL,
                1,
                2
        ));

        KitHandler.registerKit(new Kit("knight", KNIGHT_ARMOR, KNIGHT_SWORD, KNIGHT_ITEMS));
    }
}
