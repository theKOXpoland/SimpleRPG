package mc.theKOXpoland.SimpleRPG.Managers.Items;

import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomArmor;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomItem;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemsManager {

    public static List<ItemStack> allCustomItemsList = new ArrayList<>();
    public static List<String> allCustomItemsNames = new ArrayList<>();
    public static final Map<String, ItemStack> allCustomItemsMap = new HashMap<>();

    public static List<ItemStack> getAllCustomItemsStackList() {
        allCustomItemsList.addAll(CustomWeapon.customWeaponsList);
        allCustomItemsList.addAll(CustomArmor.customArmorsList);
        allCustomItemsList.addAll(CustomItem.customItemList);

        return allCustomItemsList;
    }

    public static List<String> getAllCustomItemsNamesList() {
        return allCustomItemsNames;
    }

    public static Map<String, ItemStack> getAllCustomItemsMap() {
        return allCustomItemsMap;
    }

    public static void loadAllCustomItemsNamesList() {
        allCustomItemsNames.addAll(CustomWeapon.weaponsNames);
        allCustomItemsNames.addAll(CustomArmor.armorsName);
        allCustomItemsNames.addAll(CustomItem.itemsName);
    }
    public static void loadAllCustomItemsMap() {
        allCustomItemsMap.putAll(CustomWeapon.customWeaponsMap);
        allCustomItemsMap.putAll(CustomArmor.customArmorsMap);
        allCustomItemsMap.putAll(CustomItem.customItemMap);
    }

    public static void clearAllCustomLists() {
        allCustomItemsList.clear();
        allCustomItemsNames.clear();
    }

    public static void clearAllCustomMaps() {
        allCustomItemsMap.clear();
    }
}
