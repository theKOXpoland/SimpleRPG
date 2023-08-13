package mc.theKOXpoland.SimpleRPG.Managers;

import mc.theKOXpoland.SimpleRPG.Customs.CustomArmors;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ArmorsManager {

    private static MainFile plugin;
    public ArmorsManager(MainFile plugin) {
        ArmorsManager.plugin = plugin;
    }

    public void init() {
        newArmors();
    }

    public void newArmors() {

        if (plugin.configManager.getArmorsConfg().getConfigurationSection("Armors.") == null) {
            plugin.getLogger().severe("Path to Armors. is invalid!");
        }

        ConfigurationSection cfg = plugin.configManager.getArmorsConfg().getConfigurationSection("Armors.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG armors is null");
            return;
        }

        for (String armor : cfg.getKeys(false)) {

            Material itemMaterial = Material.IRON_BOOTS;
            String itemMaterialPath = cfg.getString(armor + ".ItemMaterialType");

            if (itemMaterialPath != null) {
                try {
                    itemMaterial = Material.getMaterial(itemMaterialPath.toUpperCase());
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default IRON_BOOTS");
                    itemMaterial = Material.IRON_BOOTS;
                }
            }

            String itemName = "DefaultName";
            String itemNamePath = cfg.getString(armor + ".ItemName");

            if (itemNamePath != null) {
                try {
                    itemName = itemNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default itemName to DefaultName");
                    itemName = "DefaultName";
                }
            }

            String displayName = "<red>Default Display Name";
            String displayNamePath = cfg.getString(armor + ".DisplayName");

            if (displayNamePath != null) {
                try {
                    displayName = displayNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default displayName to <red>Default Display Name");
                    displayName = "<red>Default Display Name";
                }
            }

            int itemAmount = 1;
            String itemAmountPath = cfg.getString(armor + ".ItemAmount");

            if (itemAmountPath != null) {
                try {
                    itemAmount = Integer.parseInt(itemAmountPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default itemAmount to 1");
                    itemAmount = 1;
                }
                if (itemAmount <= 0) {
                    plugin.getLogger().severe(armor + " Setting default itemAmount to 1");
                    itemAmount = 1;
                }
            }

            boolean unbreakable = true;
            String unbreakablePath = cfg.getString(armor + ".Unbreakable");

            if (unbreakablePath != null) {
                try {
                    unbreakable = Boolean.parseBoolean(unbreakablePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default unbreakable to true");
                    unbreakable = true;
                }
            }

            ConfigurationSection enchantments = cfg.getConfigurationSection(armor + ".Enchants");

            String enchants = "None";

            if (enchantments != null) {
                try {
                    enchants = enchantments.getString(".Enchant");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(armor + " Setting default enchants to None");
                    enchants = "None";
                }
            }


            ConfigurationSection cfgLore = cfg.getConfigurationSection(armor + ".Lore");

            List<Component> loreList = new ArrayList<>();

            if (cfgLore == null) {
                plugin.getLogger().severe(armor + " cfgLore nie istnieje");
                loreList.add(Util.mm("Basic lore cause cfgLore for armor is invalid!"));
            }

            if (cfgLore != null) {
                for (String lorePart : cfgLore.getStringList(".LoreText")) {
                    loreList.add(Util.mm(lorePart));
                }
            }

            CustomArmors customArmors = new CustomArmors
                    (itemMaterial,
                            itemName,
                            displayName,
                            itemAmount,
                            unbreakable,
                            enchants,
                            loreList).createArmor();

            //customItemList.add(customWeapon);
            //customItemsMap.put(itemName, customWeapon);
        }
    }
}
