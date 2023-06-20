package mc.theKOXpoland.SimpleRPG.Managers;

import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
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

    public void init() {
        newWeapons();
    }

    public void newWeapons() {

        if (plugin.configManager.getWeaponsConfig().getConfigurationSection("Weapons.") == null) {
            plugin.getLogger().severe("Path to Weapons. is invalid!");
        }

        ConfigurationSection cfg = plugin.configManager.getWeaponsConfig().getConfigurationSection("Weapons.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG Weapons is NUll");
            return;
        }

        for (String bron : cfg.getKeys(false)) {

            Material itemMaterial = Material.IRON_SWORD;
            String itemMaterialPath = cfg.getString(bron + ".ItemMaterialType");

            if (itemMaterialPath != null) {
                try {
                    itemMaterial = Material.getMaterial(itemMaterialPath.toUpperCase());
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default itemMaterial to IRON_SWORD");
                    itemMaterial = Material.IRON_SWORD;
                }
            }

            double itemDamage = 0;
            String itemDamagePath = cfg.getString(bron + ".Damage");

            if (itemDamagePath != null) {
                try {
                    itemDamage = Double.parseDouble(itemDamagePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default damage to 1");
                    itemDamage = 0;
                }
                if (itemDamage <= -2) {
                    plugin.getLogger().severe(bron + " Setting default damage to 1");
                    itemDamage = 0;
                }
            }

            String itemName = "DefaultName";
            String itemNamePath = cfg.getString(bron + ".ItemName");

            if (itemNamePath != null) {
                try {
                    itemName = itemNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default Name");
                    itemName = "DefaultName";
                }
            }

            String displayName = "<red>Default Display Name";
            String displayNamePath = cfg.getString(bron + ".DisplayName");

            if (displayNamePath != null) {
                try {
                    displayName = displayNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default displayName to <red>Default Display Name");
                    displayName = "<red>Default Display Name";
                }
            }

            String customType = "sword";
            String customTypePath = cfg.getString(bron + ".CustomType");

            if (customTypePath != null) {
                try {
                    customType = customTypePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default customType to sword");
                    customType = "sword";
                }
            }

            int itemAmount = 1;
            String itemAmountPath = cfg.getString(bron + ".ItemAmount");

            if (itemAmountPath != null) {
                try {
                    itemAmount = Integer.parseInt(itemAmountPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default 1 itemAmount");
                    itemAmount = 1;
                }
                if (itemAmount <= 0) {
                    plugin.getLogger().severe(bron + " Setting default 1 itemAmount");
                    itemAmount = 1;
                }
            }

            int customModelData = 0;
            String customModelDataPath = cfg.getString(bron + ".CustomModelData");

            if (customModelDataPath != null) {
                try {
                    customModelData = Integer.parseInt(customModelDataPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default 0 customModelData");
                    customModelData = 0;
                }
                if (customModelData < 0) {
                    plugin.getLogger().severe(bron + " Setting default 0 customModelData");
                    customModelData = 0;
                }
            }

            boolean unbreakable = true;
            String unbreakablePath = cfg.getString(bron + ".Unbreakable");

            if (unbreakablePath != null) {
                try {
                    unbreakable = Boolean.parseBoolean(unbreakablePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default unbreakable to true");
                    unbreakable = true;
                }
            }

            ConfigurationSection enchantments = cfg.getConfigurationSection(bron + ".Enchants");

            String enchants = "None";

            if (enchantments != null) {
                try {
                    enchants = enchantments.getString(".Enchant");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default enchants to None");
                    enchants = "None";
                }
            }

            ConfigurationSection cfgAttacks = cfg.getConfigurationSection(bron + ".Attacks");

            if (cfgAttacks == null) {
                plugin.getLogger().severe(bron + " cfgAttacks nie istnieje");
            }

            assert cfgAttacks != null;

            String firstAttack = "Null";
            String firstAttackPath = cfgAttacks.getString(".FirstAttack");

            if (firstAttackPath != null) {
                try {
                    firstAttack = firstAttackPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default firstAttack to Null");
                    firstAttack = "Null";
                }
            }

            String secondAttack = "Null";
            String secondAttackPath = cfgAttacks.getString(".SecondAttack");

            if (secondAttackPath != null) {
                try {
                    secondAttack = secondAttackPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting default secondAttack to Null");
                    secondAttack = "Null";
                }
            }

            String attackSpeed = "Basic";
            String attackSpeedPath = cfg.getString(bron + ".AttackSpeed");

            if (attackSpeedPath != null) {
                try {
                    attackSpeed = attackSpeedPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(bron + " Setting basic attackSpeed to Basic");
                    attackSpeed = "Basic";
                }
            }

            ConfigurationSection cfgLore = cfg.getConfigurationSection(bron + ".Lore");

            List<Component> loreList = new ArrayList<>();

            if (cfgLore == null) {
                plugin.getLogger().severe(bron + " cfgLore does NOT exist");
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
                            itemDamage,
                            unbreakable,
                            enchants,
                            loreList).createWeapon();
        }
    }

}
