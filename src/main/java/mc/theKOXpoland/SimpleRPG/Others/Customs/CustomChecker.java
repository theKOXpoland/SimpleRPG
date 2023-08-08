package mc.theKOXpoland.SimpleRPG.Others.Customs;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomItem;
import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Items.Managers.ItemManager;
import mc.theKOXpoland.SimpleRPG.Mobs.Managers.MobManager;
import mc.theKOXpoland.SimpleRPG.Items.Managers.WeaponManager;
import mc.theKOXpoland.SimpleRPG.Mobs.Customs.CustomMob;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomChecker {

    private static MainFile plugin;
    public CustomChecker(MainFile plugin) {
        CustomChecker.plugin = plugin;
    }

    public static CustomMob getCustomMob(Entity entity) {
        String nbtName = "";
        CustomMob customMob;

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = entity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : MobManager.customMobMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    customMob = MobManager.customMobMap.get(key);
                    return customMob;
                }
            }
        }
        return null;
    }

    public static CustomWeapon getCustomWeapon(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        String nbtName = "";
        CustomWeapon customWeapon;

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : WeaponManager.customWeaponMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    customWeapon = WeaponManager.customWeaponMap.get(key);
                    return customWeapon;
                }
            }
        }
        return null;
    }

    public static CustomItem getCustomItem(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        String nbtName = "";
        CustomItem customItem;

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : ItemManager.customItemMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    customItem = ItemManager.customItemMap.get(key);
                    return customItem;
                }
            }
        }
        return null;
    }

    public static String mobHasName(Entity entity) {
        String nbtName = "";

        nbtName = entity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

        return nbtName;
    }

    public static boolean mobNamesEquals(Entity entity) {
        String nbtName = "";

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = entity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : MobManager.customMobMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String mobHasType(Entity entity) {
        String nbtType = "";

        nbtType = entity.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);

        return nbtType;
    }

    public static String itemHasName(ItemMeta meta) {

        if (meta == null) {
            return null;
        }

        String nbtName = "";

        nbtName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

        return nbtName;
    }

    public static boolean itemNamesEquals(ItemStack item) {
        String nbtName = "";

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : WeaponManager.customWeaponMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String itemHasType(ItemMeta meta) {

        if (meta == null) {
            return null;
        }

        String nbtType = "";

        nbtType = meta.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);

        return nbtType;
    }

}
