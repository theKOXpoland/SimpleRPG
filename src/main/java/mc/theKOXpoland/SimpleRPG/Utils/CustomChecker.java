package mc.theKOXpoland.SimpleRPG.Utils;

import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomArmor;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomItem;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Managers.Items.ArmorManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.ItemManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.WeaponManager;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Constructors.Mobs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Managers.Mobs.MobManager;
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
        String nbtName;
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

    public static void getCustomItemType(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (getCustomItem(item) != null) {
            CustomItem customItem = getCustomItem(item);

            /*Field[] fields = customItem.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(item);
                    System.out.println(field.getName() + ": " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }*/
        } else if (getCustomArmor(item) != null) {
            CustomArmor customArmor = getCustomArmor(item);

        } else if (getCustomWeapon(item) != null) {
            CustomWeapon customWeapon = getCustomWeapon(item);

        } else {
            System.out.println("Nope");
        }
    }

    public static CustomWeapon getCustomWeapon(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        String nbtName;
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

        String nbtName;
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

    public static CustomArmor getCustomArmor(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        String nbtName;
        CustomArmor customArmor;

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            nbtName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);
            for (String key : ArmorManager.customArmorMap.keySet()) {
                if (nbtName != null && nbtName.contains(key)) {
                    customArmor = ArmorManager.customArmorMap.get(key);
                    return customArmor;
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
        String nbtName;

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

        String nbtName;
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

        String nbtType;

        nbtType = meta.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);

        return nbtType;
    }

}
