package me.u8092.brawl.match;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitHandler {
    private static List<Kit> kits = new ArrayList<>();

    public static List<Kit> getKits() { return kits; }

    public static Kit getKitByName(String name) {
        for(Kit kit : kits) {
            if(kit.getName().equals(name)) {
                return kit;
            }
        }

        return null;
    }

    public static void registerKit(Kit kit) {
        if(kits.contains(kit)) return;

        //MinecraftServer.getGlobalEventHandler().call(new KitRegisterEvent(kit));
        kits.add(kit);
    }

    public static void unregisterKit(Kit kit) {
        if(!kits.contains(kit)) return;

        //MinecraftServer.getGlobalEventHandler().call(new KitUnregisterEvent(kit));
        kits.remove(kit);
    }

    public static void applyKit(Player player, String registeredKitName) {
        Kit kit = getKitByName(registeredKitName);
        if(kit == null) return;

        player.getInventory().setHelmet(kit.getArmor().get(0));
        player.getInventory().setChestplate(kit.getArmor().get(1));
        player.getInventory().setLeggings(kit.getArmor().get(2));
        player.getInventory().setBoots(kit.getArmor().get(3));

        player.getInventory().setItemInMainHand(kit.getMainSlot());

        for(ItemStack itemStack : kit.additionalItems) {
            player.getInventory().addItem(itemStack);
        }
    }
}
