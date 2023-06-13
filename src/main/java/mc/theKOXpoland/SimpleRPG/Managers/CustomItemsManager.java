package mc.theKOXpoland.SimpleRPG.Managers;

import mc.theKOXpoland.SimpleRPG.Customs.CustomArmors;
import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemsManager {

    public static List<ItemStack> customItemsList = new ArrayList<>();
    public static List<String> customItemsNames = new ArrayList<>();
    public static final Map<String, ItemStack> customItemsMap = new HashMap<>();

    public static List<ItemStack> getItemsStackList() {
        customItemsList.addAll(CustomWeapon.customWeaponsList);
        customItemsList.addAll(CustomArmors.customArmorsList);

        return customItemsList;
    }
    public static List<String> getItemsNamesList() {
        customItemsNames.addAll(CustomWeapon.weaponsNames);
        customItemsNames.addAll(CustomArmors.armorsName);

        return customItemsNames;
    }
    public static Map<String, ItemStack> getCustomItemsMap() {
        customItemsMap.putAll(CustomWeapon.customWeaponsMap);
        customItemsMap.putAll(CustomArmors.customArmorsMap);

        return customItemsMap;
    }
}
