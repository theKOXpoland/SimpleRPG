package mc.theKOXpoland.SimpleRPG.Managers.Items;

import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class WeaponManager {

    private static MainFile plugin;
    public WeaponManager(MainFile plugin) {
        WeaponManager.plugin = plugin;
    }

    public static List<CustomWeapon> customWeaponList = new ArrayList<>();
    public static Map<String, CustomWeapon> customWeaponMap = new HashMap<>();

    public void init() {
        newWeapons();
    }

    public void newWeapons() {

        if (plugin.configManager.getWeaponConfig().getConfigurationSection("Weapons.") == null) {
            plugin.getLogger().severe("Path to Weapons. is invalid!");
            return;
        }

        ConfigurationSection cfg = plugin.configManager.getWeaponConfig().getConfigurationSection("Weapons.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG Weapons is NUll");
            return;
        }

        for (String weapon : cfg.getKeys(false)) {

            Material itemMaterial = Material.IRON_SWORD;
            String itemMaterialPath = cfg.getString(weapon + ".ItemMaterialType");

            if (itemMaterialPath != null) {
                try {
                    itemMaterial = Material.getMaterial(itemMaterialPath.toUpperCase());
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default itemMaterial to IRON_SWORD");
                }
            }

            String itemName = "DefaultName";
            String itemNamePath = cfg.getString(weapon + ".ItemName");

            if (itemNamePath != null) {
                try {
                    itemName = itemNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default Name");
                    itemName = "DefaultName";
                }
            }

            String displayName = "<red>Default Display Name";
            String displayNamePath = cfg.getString(weapon + ".DisplayName");

            if (displayNamePath != null) {
                try {
                    displayName = displayNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default displayName to <red>Default Display Name");
                    displayName = "<red>Default Display Name";
                }
            }

            String customType = "sword";
            String customTypePath = cfg.getString(weapon + ".CustomType");

            if (customTypePath != null) {
                try {
                    customType = customTypePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default customType to sword");
                    customType = "sword";
                }
            }

            int itemAmount = 1;
            String itemAmountPath = cfg.getString(weapon + ".ItemAmount");

            if (itemAmountPath != null) {
                try {
                    itemAmount = Integer.parseInt(itemAmountPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default 1 itemAmount");
                }
                if (itemAmount <= 0) {
                    plugin.getLogger().severe(weapon + " Setting default 1 itemAmount");
                    itemAmount = 1;
                }
            }

            int customModelData = 0;
            String customModelDataPath = cfg.getString(weapon + ".CustomModelData");

            if (customModelDataPath != null) {
                try {
                    customModelData = Integer.parseInt(customModelDataPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default 0 customModelData");
                }
                if (customModelData < 0) {
                    plugin.getLogger().severe(weapon + " Setting default 0 customModelData");
                    customModelData = 0;
                }
            }

            boolean unbreakable = true;
            String unbreakablePath = cfg.getString(weapon + ".Unbreakable");

            if (unbreakablePath != null) {
                try {
                    unbreakable = Boolean.parseBoolean(unbreakablePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default unbreakable to true");
                }
            }

            ConfigurationSection enchantments = cfg.getConfigurationSection(weapon + ".Enchants");

            String enchants = "None";

            if (enchantments != null) {
                try {
                    enchants = enchantments.getString(".Enchant");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default enchants to None");
                    enchants = "None";
                }
            }

            ConfigurationSection cfgAttacks = cfg.getConfigurationSection(weapon + ".Attacks");

            if (cfgAttacks == null) {
                plugin.getLogger().severe(weapon + " cfgAttacks nie istnieje");
            }

            assert cfgAttacks != null;

            String firstAttack = "Null";
            String firstAttackPath = cfgAttacks.getString(".FirstAttack");

            if (firstAttackPath != null) {
                try {
                    firstAttack = firstAttackPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default firstAttack to Null");
                    firstAttack = "Null";
                }
            }

            String secondAttack = "Null";
            String secondAttackPath = cfgAttacks.getString(".SecondAttack");

            if (secondAttackPath != null) {
                try {
                    secondAttack = secondAttackPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default secondAttack to Null");
                    secondAttack = "Null";
                }
            }

            String attackSpeed = "Basic";
            String attackSpeedPath = cfg.getString(weapon + ".AttackSpeed");

            if (attackSpeedPath != null) {
                try {
                    attackSpeed = attackSpeedPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting basic attackSpeed to Basic");
                    attackSpeed = "Basic";
                }
            }

            boolean breakblocks = true;
            String breakblocksPath = cfg.getString(weapon + ".BreakBlocks");

            if (breakblocksPath != null) {
                try {
                    breakblocks = Boolean.parseBoolean(breakblocksPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default breakblocks to false");
                }
            }

            ConfigurationSection statSection = cfg.getConfigurationSection(weapon + ".Stats");

            if (statSection == null) {
                plugin.getLogger().severe(weapon + " statSection doesn't exist!");
            }

            assert statSection != null;

            double itemDamage = 0;
            String itemDamagePath = statSection.getString(".Damage");

            if (itemDamagePath != null) {
                try {
                    itemDamage = Double.parseDouble(itemDamagePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default itemDamage to 1");
                    itemDamage = 0;
                }
                if (itemDamage <= -2) {
                    plugin.getLogger().severe(weapon + " Setting default itemDamage to 1");
                    itemDamage = 0;
                }
            }

            int itemRange = 0;
            String itemRangePath = statSection.getString(".Range");

            if (itemRangePath != null) {
                try {
                    itemRange = Integer.parseInt(itemRangePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(weapon + " Setting default itemRange to 0");
                }
                if (itemRange <= -1) {
                    plugin.getLogger().severe(weapon + " Setting default itemRange to 0");
                }
            }

            ConfigurationSection cfgLore = cfg.getConfigurationSection(weapon + ".Lore");

            List<Component> loreList = new ArrayList<>();

            if (cfgLore == null) {
                plugin.getLogger().severe(weapon + " cfgLore doesn't exist!");
                loreList.add(Util.mm("Basic lore cause cfgLore for weapon is invalid!"));
            }

            if (cfgLore != null) {
                for (String lorePart : cfgLore.getStringList(".LoreText")) {
                    String edited = lorePart.replace("%damage%", "<red>" + (1 + itemDamage))
                            .replace("%first_attack%", "<white>" + firstAttack)
                            .replace("%second_attack%", "<white>" + secondAttack)
                            .replace("%attackSpeed%", "<white>" + attackSpeed);
                    loreList.add(Util.mm(edited));
                }
            }

            CustomWeapon customWeapon = new CustomWeapon
                    (itemMaterial,
                            itemName,
                            displayName,
                            firstAttack,
                            secondAttack,
                            attackSpeed,
                            customType,
                            itemAmount,
                            customModelData,
                            itemRange,
                            itemDamage,
                            unbreakable,
                            breakblocks,
                            enchants,
                            loreList).createWeapon();

            customWeaponList.add(customWeapon);
            customWeaponMap.put(itemName, customWeapon);
        }
    }

}
