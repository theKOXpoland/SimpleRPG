<<<<<<< HEAD:src/main/java/mc/theKOXpoland/SimpleRPG/Items/Customs/CustomArmor.java
package mc.theKOXpoland.SimpleRPG.Items.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomArmor {

    private static MainFile plugin;
    public CustomArmor(MainFile plugin) {
        CustomArmor.plugin = plugin;
    }

    private Material itemMaterialType;
    private String itemName;
    private String displayName;
    private int itemAmount;
    private int customModelData;
    private boolean unbreakable;
    private String enchants;
    private List<Component> lore;

    public static List<ItemStack> customArmorsList = new ArrayList<>();
    public static List<String> armorsName = new ArrayList<>();
    public static final Map<String, ItemStack> customArmorsMap = new HashMap<>();

    public CustomArmor createArmor() {
        ItemStack Item = new ItemStack(itemMaterialType, itemAmount);
        ItemMeta meta = Item.getItemMeta();

        if (enchants.isEmpty() || enchants.equals("None")) {
            meta.setUnbreakable(true);
        } else {
            String enchantment = enchants;

            String[] splitedEnchants = enchantment.split(",");
            for (String ench : splitedEnchants) {
                try {
                    String[] splitted = ench.split(":");
                    if (Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase())) != null) {
                        meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase()))),
                                Integer.parseInt(splitted[1]), false);
                    }
                    meta.setUnbreakable(true);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(ench + " is wrong!");
                    meta.setUnbreakable(true);
                }
            }
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;

        meta.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , itemName);

        meta.displayName(Util.mm(displayName));

        if (customModelData != 0) {
            meta.setCustomModelData(customModelData);
        }

        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        meta.lore(lore);

        Item.setItemMeta(meta);

        customArmorsList.add(Item);
        armorsName.add(itemName);
        customArmorsMap.put(itemName, Item);

        return this;
    }
}
=======
package mc.theKOXpoland.SimpleRPG.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomArmors {

    private static MainFile plugin;
    public CustomArmors(MainFile plugin) {
        CustomArmors.plugin = plugin;
    }

    private Material itemMaterialType;
    private String itemName;
    private String displayName;
    private int itemAmount;
    private boolean unbreakable;
    private String enchants;
    private List<Component> lore;

    public static List<ItemStack> customArmorsList = new ArrayList<>();
    public static List<String> armorsName = new ArrayList<>();
    public static final Map<String, ItemStack> customArmorsMap = new HashMap<>();

    public CustomArmors createArmor() {
        ItemStack Item = new ItemStack(itemMaterialType, itemAmount);
        ItemMeta meta = Item.getItemMeta();

        if (enchants.isEmpty() || enchants.equals("None")) {
            meta.setUnbreakable(true);
        } else {
            String enchantment = enchants;

            String[] splitedEnchants = enchantment.split(",");
            for (String ench : splitedEnchants) {
                try {
                    String[] splitted = ench.split(":");
                    if (Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase())) != null) {
                        meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase()))),
                                Integer.parseInt(splitted[1]), false);
                    }
                    meta.setUnbreakable(true);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(ench + " is wrong!");
                    meta.setUnbreakable(true);
                }
            }
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;

        meta.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , itemName);

        meta.displayName(Util.mm(displayName));

        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        meta.lore(lore);

        Item.setItemMeta(meta);

        customArmorsList.add(Item);
        armorsName.add(itemName);
        customArmorsMap.put(itemName, Item);

        return null;
    }
}
>>>>>>> main:src/main/java/mc/theKOXpoland/SimpleRPG/Customs/CustomArmors.java
