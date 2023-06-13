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

    //public static List<CustomWeapon> customItemList = new ArrayList<>();
    //public static Map<String, CustomWeapon> customItemsMap = new HashMap<>();

    public void init() {
        newWeapons();
    }

    public void newWeapons() {

        if (plugin.configManager.getWeaponsConfig().getConfigurationSection("Weapons.") == null) {
            plugin.getLogger().severe("Ścieżka do Weapons. jest nieprawidłowa!");
        }

        ConfigurationSection cfg = plugin.configManager.getWeaponsConfig().getConfigurationSection("Weapons.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG broni JEST NULLEM");
            return;
        }

        for (String bron : cfg.getKeys(false)) {

            Material itemMaterial = Material.getMaterial(cfg.getString(bron + ".ItemMaterialType").toUpperCase(Locale.ROOT));

            if (itemMaterial == null) {
                plugin.getLogger().severe(bron + " Setting default IRON_SWORD");
                itemMaterial = Material.IRON_SWORD;
            }

            double itemDamage = Double.parseDouble(cfg.getString(bron + ".Damage"));

            if (itemDamage <= -2) {
                plugin.getLogger().severe(bron + " Setting default damage to 1");
                itemDamage = 0;
            }

            String itemName = cfg.getString(bron + ".ItemName");

            if (itemName == null) {
                plugin.getLogger().severe(bron + " Setting default Name");
                itemName = "DefaultName";
            }

            String displayName = cfg.getString(bron + ".DisplayName");

            if (displayName == null) {
                plugin.getLogger().severe(bron + " Setting default displayName");
                displayName = "<red>Default Display Name";
            }

            String customType = cfg.getString(bron + ".CustomType");

            if (customType == null) {
                plugin.getLogger().severe(bron + " Setting default customType");
                customType = "sword";
            }

            int itemAmount = Integer.parseInt(cfg.getString(bron + ".ItemAmount"));

            if (itemAmount <= 0) {
                plugin.getLogger().severe(bron + " Setting default 1 itemAmount");
                itemAmount = 1;
            }

            int customModelData = Integer.parseInt(cfg.getString(bron + ".CustomModelData"));

            if (customModelData < 0) {
                plugin.getLogger().severe(bron + " Setting default 0 customModelData");
                customModelData = 0;
            }

            boolean unbreakable = cfg.getBoolean(bron + ".Unbreakable");

            ConfigurationSection enchantments = cfg.getConfigurationSection(bron + ".Enchants");

            String enchants = "";

            if (enchantments != null) {

                enchants = enchantments.getString(".Enchant");

            }

            ConfigurationSection cfgAttacks = cfg.getConfigurationSection(bron + ".Attacks");

            if (cfgAttacks == null) {
                plugin.getLogger().severe(bron + " cfgAttacks nie istnieje");
            }


            String firstAttack = cfgAttacks.getString(".FirstAttack");

            if (firstAttack == null) {
                plugin.getLogger().severe(bron + " Setting default FirstAttack");
                firstAttack = "Null";
            }

            String secondAttack = cfgAttacks.getString(".SecondAttack");

            if (secondAttack == null) {
                plugin.getLogger().severe(bron + " Setting default SecondAttack");
                secondAttack = "Null";
            }

            String attackSpeed = cfg.getString(bron + ".AttackSpeed");

            if (attackSpeed == null) {
                plugin.getLogger().severe(bron + " Setting basic attackSpeed");
                attackSpeed = "Basic";
            }

            ConfigurationSection cfgLore = cfg.getConfigurationSection(bron + ".Lore");

            if (cfgLore == null) {
                plugin.getLogger().severe(bron + " cfgLore nie istnieje");
            }

            List<Component> loreList = new ArrayList<>();
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

            //customItemList.add(customWeapon);
            //customItemsMap.put(itemName, customWeapon);
        }
    }

}
