package mc.theKOXpoland.SimpleRPG.Managers;

import mc.theKOXpoland.SimpleRPG.Customs.CustomArmors;
import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            plugin.getLogger().severe("Ścieżka do Armors. jest nieprawidłowa!");
        }

        ConfigurationSection cfg = plugin.configManager.getArmorsConfg().getConfigurationSection("Armors.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG broni JEST NULLEM");
            return;
        }

        for (String armor : cfg.getKeys(false)) {

            Material itemMaterial = Material.getMaterial(cfg.getString(armor + ".ItemMaterialType").toUpperCase(Locale.ROOT));

            if (itemMaterial == null) {
                plugin.getLogger().severe(armor + " Setting default IRON_BOOTS");
                itemMaterial = Material.IRON_BOOTS;
            }

            String itemName = cfg.getString(armor + ".ItemName");

            if (itemName == null) {
                plugin.getLogger().severe(armor + " Setting default Name");
                itemName = "DefaultName";
            }

            String displayName = cfg.getString(armor + ".DisplayName");

            if (displayName == null) {
                plugin.getLogger().severe(armor + " Setting default displayName");
                displayName = "<red>Default Display Name";
            }

            int itemAmount = Integer.parseInt(cfg.getString(armor + ".ItemAmount"));

            if (itemAmount <= 0) {
                plugin.getLogger().severe(armor + " Setting default 1 itemAmount");
                itemAmount = 1;
            }

            boolean unbreakable = cfg.getBoolean(armor + ".Unbreakable");

            ConfigurationSection enchantments = cfg.getConfigurationSection(armor + ".Enchants");

            String enchants = "";

            if (enchantments != null) {

                enchants = enchantments.getString(".Enchant");

            }


            ConfigurationSection cfgLore = cfg.getConfigurationSection(armor + ".Lore");

            if (cfgLore == null) {
                plugin.getLogger().severe(armor + " cfgLore nie istnieje");
            }

            List<Component> loreList = new ArrayList<>();
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
