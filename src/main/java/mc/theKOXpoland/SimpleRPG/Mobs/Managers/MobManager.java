package mc.theKOXpoland.SimpleRPG.Mobs.Managers;

import mc.theKOXpoland.SimpleRPG.Mobs.Customs.CustomMob;
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

public class MobManager {

    private static MainFile plugin;
    public MobManager(MainFile plugin) {
        MobManager.plugin = plugin;
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

        if (plugin.configManager.getMobConfig().getConfigurationSection("Mobs.") == null) {
            plugin.getLogger().severe("Path to Mobs. is wrong!");
            return;
        }

        ConfigurationSection cfg = plugin.configManager.getMobConfig().getConfigurationSection("Mobs.");

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
                    plugin.getLogger().severe(mob + " Setting default mobType to Zombie");
                }
            }

            String mobName = "TestName";
            String mobNamePath = cfg.getString(mob + ".MobName");

            if (mobNamePath != null) {
                try {
                    mobName = mobNamePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default mobName to TestName");
                    mobName = "TestName";
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

            String customType = "Neutral";
            String customTypePath = cfg.getString(mob + ".CustomType");

            if (customTypePath != null) {
                try {
                    customType = customTypePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default customType to Neutral");
                    customType = "Neutral";
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

            boolean isGlowing = true;
            String isGlowingPath = cfg.getString(mob + ".IsGlowing");

            if (isGlowingPath != null) {
                try {
                    isGlowing = Boolean.parseBoolean(isGlowingPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default isGlowing to false");
                    isGlowing = false;
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
                    plugin.getLogger().severe(mob + " equipementSection doesn't exist!");
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

            List<String> biomeResp = new ArrayList<>();

            if (!cfg.getStringList(mob + ".BiomeResp").isEmpty() || cfg.getString(mob + ".BiomeResp") != null) {
                try {
                    biomeResp = cfg.getStringList(mob + ".BiomeResp");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default respInstead to all biomes");
                    biomeResp.add("all".toUpperCase());
                }
            }

            List<String> worldResp = new ArrayList<>();

            if (!cfg.getStringList(mob + ".WorldResp").isEmpty() || cfg.getString(mob + ".WorldResp") != null) {
                try {
                    worldResp = cfg.getStringList(mob + ".WorldResp");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default worldResp to all worlds");
                    worldResp.add("all".toUpperCase());
                }
            }

                ConfigurationSection statSection = cfg.getConfigurationSection(mob + ".Stats");

                if (statSection == null) {
                    plugin.getLogger().severe(mob + " statSection doesn't exist!");
                }

                assert statSection != null;

                double mobSpeed = 1;
                String mobSpeedPath = statSection.getString(".Speed");

                if (mobSpeedPath != null) {
                    try {
                        mobSpeed = Double.parseDouble(mobSpeedPath);
                    } catch (IllegalArgumentException exp) {
                        plugin.getLogger().severe(mob + " Setting default Speed to 1");
                    }
                    if (mobSpeed <= 0) {
                        plugin.getLogger().severe(mob + " Setting default Speed to 1");
                        mobSpeed = 1;
                    }
                }

                double mobDamage = 1;
                String mobDamagePath = statSection.getString(".Damage");

                if (mobDamagePath != null) {
                    try {
                        mobDamage = Double.parseDouble(mobDamagePath);
                    } catch (IllegalArgumentException exp) {
                        plugin.getLogger().severe(mob + " Setting default Damage to 1");
                    }
                    if (mobDamage <= 0) {
                        plugin.getLogger().severe(mob + " Setting default Damage to 1");
                        mobDamage = 1;
                    }
                }

            boolean silent = true;

            if (cfg.getString(mob + ".isSilent") != null) {
                try {
                    silent = cfg.getBoolean(mob + ".isSilent");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default isSilent to true");
                }
            }

            boolean shouldTarget = true;

            if (cfg.getString(mob + ".shouldTarget") != null) {
                try {
                    shouldTarget = cfg.getBoolean(mob + ".isSilent");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default shouldTarget to true");
                }
            }

            boolean shouldDamage = true;

            if (cfg.getString(mob + ".shouldDamage") != null) {
                try {
                    shouldDamage = cfg.getBoolean(mob + ".shouldDamage");
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default shouldDamage to true");
                }
            }

            String customEntityType = "Entity";
            String customEntityTypePath = cfg.getString(mob + ".CustomEntityType");

            if (customEntityTypePath != null) {
                try {
                    customEntityType = customEntityTypePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(mob + " Setting default customEntityType to Entity");
                    customEntityType = "Entity";
                }
            }

            UUID randomUUID = UUID.randomUUID();

            CustomMob customMob = new CustomMob(
                    randomUUID,
                    mobType,
                    displayName,
                    customType,
                    mobName,
                    customEntityType,
                    mobHealth,
                    mobSpeed,
                    mobDamage,
                    droppedExp,
                    customNameVisible,
                    burnInDay,
                    hasAI,
                    isBaby,
                    isGlowing,
                    respNaturally,
                    silent,
                    shouldTarget,
                    shouldDamage,
                    mobItems,
                    drop,
                    respInstead,
                    biomeResp,
                    worldResp);

            customMobsList.add(customMob);
            customMobMap.put(mobName, customMob);
            mobsNames.add(mobName);
        }
    }

}
