package me.u8092.brawl.match;

import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;

public class Kit {
    public String name;
    public List<ItemStack> armor;
    public ItemStack mainSlot;
    //public ItemStack secondarySlot;
    public List<ItemStack> additionalItems;
    public List<Component> lore;

    public Kit(String name,
               List<ItemStack> armor,
               ItemStack mainSlot,
               List<ItemStack> additionalItems) {
        this.name = name;
        this.armor = armor;
        this.mainSlot = mainSlot;
        //this.secondarySlot = secondarySlot;
        this.additionalItems = additionalItems;
    }

    public String getName() { return name; }
    public List<ItemStack> getArmor() { return armor; }
    public ItemStack getMainSlot() { return mainSlot; }
    //public ItemStack getSecondarySlot() { return secondarySlot; }
    public List<ItemStack> getAdditionalItems() { return additionalItems; }
}
