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
            plugin.getLogger().severe("Path to Mobs. is wrong!");
        }

        ConfigurationSection cfg = plugin.configManager.getMobsConfig().getConfigurationSection("Mobs.");

        if (cfg == null) {
            plugin.getLogger().severe("Mobs configuration doesn't exist!");
            return;
        }

        for (String mob : cfg.getKeys(false)) {

            EntityType mobType = EntityType.ZOMBIE;
            String mobTypePath = cfg.getString(mob + ".EntityType");

            if (mobTypePath != null) {
                try {
                    mobType = EntityType.valueOf(mobTypePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default ZOMBIE");
                    mobType = EntityType.ZOMBIE;
                }
            }

            String mobName = "TestName1";
            String mobNamePath = cfg.getString(mob + ".MobName");

            if (mobNamePath != null) {
                try {
                    mobName = mobNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default MobName");
                    mobName = "TestName1";
                }
            }

            String displayName = "<red>Default Display Name";
            String displayNamePath = cfg.getString(mob + ".DisplayName");

            if (displayNamePath != null) {
                try {
                    displayName = displayNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default displayName to <red>Default Display Name");
                    displayName = "<red>Default Display Name";
                }
            }

            String customType = "";
            String customTypePath = cfg.getString(mob + ".CustomType");

            if (customTypePath != null) {
                try {
                    customType = customTypePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default customType to empty");
                    customType = "";
                }
            }

            double mobHealth = 0;
            String mobHealthPath = cfg.getString(mob + ".Health");

            if (mobHealthPath != null) {
                try {
                    mobHealth = Double.parseDouble(mobHealthPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default Health to 1");
                    mobHealth = 1;
                }
                if (mobHealth <= 0) {
                    plugin.getLogger().severe(mob + " Setting default Health to 1");
                    mobHealth = 1;
                }
            }

            int droppedExp = 0;
            String droppedExpPath = cfg.getString(mob + ".DropedEXP");


            if (droppedExpPath != null) {
                try {
                    droppedExp = Integer.parseInt(droppedExpPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default droppedExp to 0 from catch");
                    droppedExp = 0;
                }
                if (droppedExp < 0) {
                    plugin.getLogger().severe(mob + " Setting default droppedExp to 0 from if");
                    droppedExp = 0;
                }
            }

            boolean customNameVisible = true;
            String customNameVisiblePath = cfg.getString(mob + ".CustomNameVisible");

            if (customNameVisiblePath != null) {
                try {
                    customNameVisible = Boolean.parseBoolean(customNameVisiblePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default customNameVisible to true");
                    customNameVisible = true;
                }
            }

            boolean burnInDay = true;
            String burnInDayPath = cfg.getString(mob + ".BurnInDay");

            if (burnInDayPath != null) {
                try {
                    burnInDay = Boolean.parseBoolean(burnInDayPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default burnInDay to false");
                    burnInDay = false;
                }
            }

            boolean hasAI = true;
            String hasAIyPath = cfg.getString(mob + ".HasAI");

            if (hasAIyPath != null) {
                try {
                    hasAI = Boolean.parseBoolean(hasAIyPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default hasAI to false");
                    hasAI = false;
                }
            }

            boolean isBaby = true;
            String isBabyPath = cfg.getString(mob + ".IsBaby");

            if (isBabyPath != null) {
                try {
                    isBaby = Boolean.parseBoolean(isBabyPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default IsBaby to false");
                    isBaby = false;
                }
            }

            List<String> drop = new ArrayList<>();
            String dropPath = cfg.getString(mob + ".Drop");

            if (!cfg.getStringList(mob + ".Drop").isEmpty() || dropPath != null) {
                try {
                    drop = cfg.getStringList(mob + ".Drop");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default drop to null");
                    drop.add("None:100");
                }
            }

            if (cfg.getConfigurationSection(mob + ".Equipement") != null) {
                ConfigurationSection equipementSection = cfg.getConfigurationSection(mob + ".Equipement");

                if (equipementSection == null) {
                    plugin.getLogger().severe(mob + " doesn't exist!");
                }

                if (equipementSection != null) {

                    for (String section : equipementSection.getKeys(false)) {

                        if (section != null) {
                            ConfigurationSection eqPieceSection = equipementSection.getConfigurationSection(section);

                            if (eqPieceSection != null) {

                                Material itemType = Material.getMaterial(Objects.requireNonNull(eqPieceSection.getString("Item")).toUpperCase(Locale.ROOT));

                                if (Material.matchMaterial(String.valueOf(itemType)) == null) {
                                    itemType = Material.AIR;
                                }

                                assert itemType != null;
                                ItemStack item = new ItemStack(itemType, 1);

                                ItemMeta meta = item.getItemMeta();

                                if (meta != null) {
                                    if (eqPieceSection.getString("Enchants") != null) {
                                        String enchantment = eqPieceSection.getString("Enchants");

                                        if (enchantment != null) {
                                            if (enchantment.equals("None")) {
                                                meta.setUnbreakable(true);
                                            } else {
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
                                        }

                                    }
                                    item.setItemMeta(meta);
                                }

                                mobItems.put(section, item);
                            }
                        }
                    }
                }
            }

            boolean respNaturally = true;

            if (cfg.getString(mob + ".RandomRespawn") != null) {
                try {
                    respNaturally = cfg.getBoolean(mob + ".RandomRespawn");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default rendomRespawn to false");
                    respNaturally = false;
                }
            }

            List<String> respInstead = new ArrayList<>();

            if (!cfg.getStringList(mob + ".RespInstead").isEmpty() || cfg.getString(mob + ".RespInstead") != null) {
                try {
                    respInstead = cfg.getStringList(mob + ".RespInstead");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default respInstead to Default at 100%");
                    respInstead.add("Default:100");
                }
            }

            CustomMob customMob = new CustomMob(mobType, displayName, customType,
                    mobName, mobHealth, droppedExp,
                    customNameVisible, burnInDay, hasAI,
                    isBaby, respNaturally , mobItems,
                    drop, respInstead);

            customMobsList.add(customMob);
            customMobMap.put(mobName, customMob);
            mobsNames.add(mobName);
        }
    }

}
