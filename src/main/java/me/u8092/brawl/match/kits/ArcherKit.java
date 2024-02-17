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

public class ArcherKit {
    public ArcherKit() {
        List<ItemStack> ARCHER_ARMOR = ItemCreator.enchantedArmor(
                text("Arquero"),
                Collections.singletonList(text("")),
                ArmorType.IRON,
                Enchantment.PROTECTION_ENVIRONMENTAL,
                1
        );

        ItemStack ARCHER_BOW = ItemCreator.enchantedItem(
                text("Arquero"),
                Collections.singletonList(text("")),
                Material.BOW,
                Enchantment.ARROW_DAMAGE,
                1
        );

        List<ItemStack> ARCHER_ITEMS = new ArrayList<>();
        ARCHER_ITEMS.add(new ItemStack(Material.ARROW, 256));
        ARCHER_ITEMS.add(new ItemStack(Material.IRON_SWORD));
        ARCHER_ITEMS.add(ItemCreator.potion(
                text("Arquero"),
                PotionEffectType.REGENERATION,
                22,
                2
        ));

        KitHandler.registerKit(new Kit("archer", ARCHER_ARMOR, ARCHER_BOW, ARCHER_ITEMS));
    }
}
