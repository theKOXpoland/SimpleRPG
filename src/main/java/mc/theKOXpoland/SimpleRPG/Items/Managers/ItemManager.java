package mc.theKOXpoland.SimpleRPG.Items.Managers;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomItem;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {
    private static MainFile plugin;
    public ItemManager(MainFile plugin) {
        ItemManager.plugin = plugin;
    }

    public static List<CustomItem> customItemList = new ArrayList<>();
    public static Map<String, CustomItem> customItemMap = new HashMap<>();

    public void init() {
        newItem();
    }

    public void newItem() {

        if (plugin.configManager.getItemConfig().getConfigurationSection("Items.") == null) {
            plugin.getLogger().severe("Path to Items. is invalid!");
            return;
        }

        ConfigurationSection cfg = plugin.configManager.getItemConfig().getConfigurationSection("Items.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG items is null");
            return;
        }

        for (String items : cfg.getKeys(false)) {

            Material itemMaterial = Material.PAPER;
            String itemMaterialPath = cfg.getString(items + ".ItemMaterialType");

            if (itemMaterialPath != null) {
                try {
                    itemMaterial = Material.getMaterial(itemMaterialPath.toUpperCase());
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default PAPER");
                }
            }

            String itemName = "DefaultName";
            String itemNamePath = cfg.getString(items + ".ItemName");

            if (itemNamePath != null) {
                try {
                    itemName = itemNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default itemName to DefaultName");
                    itemName = "DefaultName";
                }
            }

            String displayName = "<red>Default Display Name";
            String displayNamePath = cfg.getString(items + ".DisplayName");

            if (displayNamePath != null) {
                try {
                    displayName = displayNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default displayName to <red>Default Display Name");
                    displayName = "<red>Default Display Name";
                }
            }

            int itemAmount = 1;
            String itemAmountPath = cfg.getString(items + ".ItemAmount");

            if (itemAmountPath != null) {
                try {
                    itemAmount = Integer.parseInt(itemAmountPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default itemAmount to 1");
                }
                if (itemAmount <= 0) {
                    plugin.getLogger().severe(items + " Setting default itemAmount to 1");
                    itemAmount = 1;
                }
            }

            int customModelData = 0;
            String customModelDataPath = cfg.getString(items + ".CustomModelData");

            if (customModelDataPath != null) {
                try {
                    customModelData = Integer.parseInt(customModelDataPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default 0 customModelData");
                }
                if (customModelData < 0) {
                    plugin.getLogger().severe(items + " Setting default 0 customModelData");
                    customModelData = 0;
                }
            }

            boolean unbreakable = true;
            String unbreakablePath = cfg.getString(items + ".Unbreakable");

            if (unbreakablePath != null) {
                try {
                    unbreakable = Boolean.parseBoolean(unbreakablePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default unbreakable to true");
                }
            }

            ConfigurationSection enchantments = cfg.getConfigurationSection(items + ".Enchants");

            String enchants = "None";

            if (enchantments != null) {
                try {
                    enchants = enchantments.getString(".Enchant");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default enchants to None");
                    enchants = "None";
                }
            }


            ConfigurationSection cfgLore = cfg.getConfigurationSection(items + ".Lore");

            List<Component> loreList = new ArrayList<>();

            if (cfgLore == null) {
                plugin.getLogger().severe(items + " cfgLore doesn't exist!");
                loreList.add(Util.mm("Basic lore cause cfgLore for armor is invalid!"));
            }

            if (cfgLore != null) {
                for (String lorePart : cfgLore.getStringList(".LoreText")) {
                    loreList.add(Util.mm(lorePart));
                }
            }

            boolean breakblocks = true;
            String breakblocksPath = cfg.getString(items + ".BreakBlocks");

            if (breakblocksPath != null) {
                try {
                    breakblocks = Boolean.parseBoolean(breakblocksPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(items + " Setting default breakblocks to false");
                }
            }

            CustomItem customItem = new CustomItem
                    (itemMaterial,
                            itemName,
                            displayName,
                            itemAmount,
                            customModelData,
                            unbreakable,
                            breakblocks,
                            enchants,
                            loreList).createItem();

            customItemList.add(customItem);
            customItemMap.put(itemName, customItem);
        }
    }
}
