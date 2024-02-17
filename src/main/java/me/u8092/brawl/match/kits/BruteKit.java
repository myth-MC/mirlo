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

public class BruteKit {
    public BruteKit() {
        List<ItemStack> BRUTE_ARMOR = ItemCreator.enchantedArmor(
                text("Bruto"),
                Collections.singletonList(text("")),
                ArmorType.IRON,
                Enchantment.PROTECTION_ENVIRONMENTAL,
                1
        );

        ItemStack BRUTE_SWORD = ItemCreator.enchantedItem(
                text("Bruto"),
                Collections.singletonList(text("")),
                Material.STONE_SWORD,
                Enchantment.DAMAGE_ALL,
                1
        );

        List<ItemStack> BRUTE_ITEMS = new ArrayList<>();
        BRUTE_ITEMS.add(new ItemStack(Material.BOW));
        BRUTE_ITEMS.add(new ItemStack(Material.ARROW, 16));
        BRUTE_ITEMS.add(ItemCreator.potion(
                text("Bruto"),
                PotionEffectType.DAMAGE_RESISTANCE,
                22,
                2
        ));

        KitHandler.registerKit(new Kit("brute", BRUTE_ARMOR, BRUTE_SWORD, BRUTE_ITEMS));
    }
}
