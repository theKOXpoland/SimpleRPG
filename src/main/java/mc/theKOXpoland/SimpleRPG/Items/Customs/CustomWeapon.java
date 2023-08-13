<<<<<<< HEAD:src/main/java/mc/theKOXpoland/SimpleRPG/Items/Customs/CustomWeapon.java
package mc.theKOXpoland.SimpleRPG.Items.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomWeapon {

    private static MainFile plugin;
    public CustomWeapon(MainFile plugin) {
        CustomWeapon.plugin = plugin;
    }

    private Material itemMaterialType;
    private String itemName;
    private String displayName;
    private String firstAttack;
    private String secondAttack;
    private String attackSpeed;
    private String customType;
    private int itemAmount;
    private int customModelData;
    private int itemRange;
    private double damage;
    private boolean unbreakable;
    private boolean breakBlocks;
    private String enchants;
    private List<Component> lore;

    public static List<ItemStack> customWeaponsList = new ArrayList<>();
    public static List<String> weaponsNames = new ArrayList<>();
    public static final Map<String, ItemStack> customWeaponsMap = new HashMap<>();

    public CustomWeapon createWeapon() {
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
        NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;
        NamespacedKey KeyNBTFirstAttack = plugin.Key_NBT_First_Attack;
        NamespacedKey KeyNBTSecondAttack = plugin.Key_NBT_Second_Attack;

        meta.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , itemName);
        meta.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING , customType);
        meta.getPersistentDataContainer().set(KeyNBTFirstAttack, PersistentDataType.STRING , firstAttack);
        meta.getPersistentDataContainer().set(KeyNBTSecondAttack, PersistentDataType.STRING , secondAttack);

        meta.displayName(Util.mm(displayName));

        if (customModelData != 0) {
            meta.setCustomModelData(customModelData);
        }

        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);


        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
                "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        switch (attackSpeed) {
            case "UltraSlow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "VerySlow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Slow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -0.75, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Normal" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 0.25, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Fast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "VeryFast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "UltraFast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Basic" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        }

        meta.lore(lore);

        Item.setItemMeta(meta);

        customWeaponsList.add(Item);
        weaponsNames.add(itemName);
        customWeaponsMap.put(itemName, Item);

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
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomWeapon {

    private static MainFile plugin;
    public CustomWeapon(MainFile plugin) {
        CustomWeapon.plugin = plugin;
    }

    private Material itemMaterialType;
    private String itemName;
    private String displayName;
    private String firstAttack;
    private String secondAttack;
    private String attackSpeed;
    private String customType;
    private int itemAmount;
    private int customModelData;
    private double damage;
    private boolean unbreakable;
    private String enchants;
    private List<Component> lore;

    public static List<ItemStack> customWeaponsList = new ArrayList<>();
    public static List<String> weaponsNames = new ArrayList<>();
    public static final Map<String, ItemStack> customWeaponsMap = new HashMap<>();

    public CustomWeapon createWeapon() {
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
        NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;
        NamespacedKey KeyNBTFirstAttack = plugin.Key_NBT_First_Attack;
        NamespacedKey KeyNBTSecondAttack = plugin.Key_NBT_Second_Attack;

        meta.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , itemName);
        meta.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING , customType);
        meta.getPersistentDataContainer().set(KeyNBTFirstAttack, PersistentDataType.STRING , firstAttack);
        meta.getPersistentDataContainer().set(KeyNBTSecondAttack, PersistentDataType.STRING , secondAttack);

        meta.displayName(Util.mm(displayName));

        if (customModelData != 0) {
            meta.setCustomModelData(customModelData);
        }

        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);


        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),
                "generic.attackDamage", damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        switch (attackSpeed) {
            case "UltraSlow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "VerySlow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Slow" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", -0.75, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Normal" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 0.25, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Fast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "VeryFast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 3.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "UltraFast" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            case "Basic" -> meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(),
                    "generic.attackSpeed", 0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        }

        meta.lore(lore);

        Item.setItemMeta(meta);

        customWeaponsList.add(Item);
        weaponsNames.add(itemName);
        customWeaponsMap.put(itemName, Item);

        return null;
    }

}
>>>>>>> main:src/main/java/mc/theKOXpoland/SimpleRPG/Customs/CustomWeapon.java
