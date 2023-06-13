package mc.theKOXpoland.SimpleRPG.Managers;

import mc.theKOXpoland.SimpleRPG.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MobsManager {

    private static MainFile plugin;
    public MobsManager(MainFile plugin) {
        MobsManager.plugin = plugin;
    }

    public static List<CustomMob> customMobsList = new ArrayList<>();
    public static Location mobLocalisation;
    public static Map<String, ItemStack> mobItems = new HashMap<>();
    public static Map<String, CustomMob> customMobMap = new HashMap<>();
    public static List<String> mobsNames = new ArrayList<>();

    public void init() {
        newMobs();
    }

    public static void mobLocation(Location location) {
        mobLocalisation = location;
    }

    private static void newMobs() {

        if (plugin.configManager.getMobsConfig().getConfigurationSection("Mobs.") == null) {
            plugin.getLogger().severe("Ścieżka do Mobs. jest nieprawidłowa!");
        }

        ConfigurationSection cfg = plugin.configManager.getMobsConfig().getConfigurationSection("Mobs.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG mobów JEST NULLEM");
            return;
        }

        for (String mob : cfg.getKeys(false)) {

            EntityType mobType = EntityType.valueOf(cfg.getString(mob + ".EntityType"));

            if (mobType == null) {
                plugin.getLogger().severe(mob + " Setting default ZOMBIE");
                mobType = EntityType.ZOMBIE;
            }

            String mobName = cfg.getString(mob + ".MobName");

            if (mobName == null) {
                plugin.getLogger().severe(mob + " Setting default MobName");
                mobName = "TestName1";
            }

            String displayName = cfg.getString(mob + ".DisplayName");

            if (displayName == null) {
                plugin.getLogger().severe(mob + " Setting default displayName");
                displayName = "<red>Default Display Name";
            }

            String customType = cfg.getString(mob + ".CustomType");

            if (customType == null) {
                plugin.getLogger().severe(mob + " Setting default customType");
                customType = "";
            }

            double mobHealth = Double.parseDouble(cfg.getString(mob + ".Health"));

            if (mobHealth <= 0) {
                plugin.getLogger().severe(mob + " Setting default Health to 1");
                mobHealth = 1;
            }

            int droppedExp = cfg.getInt(mob + ".DropedEXP");

            if (droppedExp > 0) {
                plugin.getLogger().severe(mob + " Setting default droppedExp");
                droppedExp = 0;
            }

            boolean customNameVisible = cfg.getBoolean(mob + ".CustomNameVisible");

            boolean burnInDay = cfg.getBoolean(mob + ".BurnInDay");

            boolean hasAI = cfg.getBoolean(mob + ".HasAI");

            boolean isBaby = cfg.getBoolean(mob + ".isBany");

            List<String> drop = cfg.getStringList(mob + ".Drop");

            ConfigurationSection equipementSection = cfg.getConfigurationSection(mob + ".Equipement");

            if (equipementSection == null) {
                plugin.getLogger().severe(mob + " cfgEq nie istnieje");
            }

            if (equipementSection != null) {

                for (String section : equipementSection.getKeys(false)) {

                    if (section != null) {
                        ConfigurationSection eqPieceSection = equipementSection.getConfigurationSection(section);

                            if (eqPieceSection != null) {

                                Material itemType = Material.getMaterial(eqPieceSection.getString("Item").toUpperCase(Locale.ROOT));

                                if (Material.matchMaterial(String.valueOf(itemType)) == null) {
                                    itemType = Material.AIR;
                                }

                                ItemStack item = new ItemStack(itemType, 1);

                                ItemMeta meta = item.getItemMeta();

                                if (meta != null) {
                                    String enchantment = eqPieceSection.getString("Enchants");

                                    if (enchantment.equals("None") || enchantment == null) {
                                        meta.setUnbreakable(true);
                                    } else
                                    if (!enchantment.equals("None") || enchantment != null) {
                                        String[] splitedEnchants = enchantment.split(",");
                                        for (String ench : splitedEnchants) {
                                            String[] splitted = ench.split(":");
                                            meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase())), Integer.parseInt(splitted[1]), false);
                                            //Add anty wrong enchant system
                                            meta.setUnbreakable(true);
                                        }
                                    }

                                    item.setItemMeta(meta);
                                }

                                mobItems.put(section, item);
                        }
                    }
                }
            }

            CustomMob customMob = new CustomMob(mobType, displayName, customType, mobName, mobHealth, droppedExp, customNameVisible, burnInDay, hasAI, isBaby, mobItems, drop);

            customMobsList.add(customMob);
            customMobMap.put(mobName, customMob);
            mobsNames.add(mobName);
        }
    }

}
