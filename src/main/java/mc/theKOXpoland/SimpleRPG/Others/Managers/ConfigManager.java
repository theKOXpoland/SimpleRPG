package mc.theKOXpoland.SimpleRPG.Others.Managers;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {

    MainFile plugin;
    public ConfigManager(MainFile plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration weaponConfig;
    private FileConfiguration chestConfig;
    private FileConfiguration mobConfig;
    private FileConfiguration armorConfig;
    private FileConfiguration itemConfig;
    private FileConfiguration skillConfig;
    private File weaponConfigFile;
    private File chestConfigFile;
    private File mobConfigFIle;
    private File armorConfigFile;
    private File itemConfigFile;
    private File skillConfigFile;

    public void reloadConfig() {
        if (weaponConfigFile == null) {
            weaponConfigFile = new File(plugin.getDataFolder(), "WeaponConfig.yml");
        }
        if (chestConfigFile == null) {
            chestConfigFile = new File(plugin.getDataFolder(), "ChestConfig.yml");
        }
        if (mobConfigFIle == null) {
            mobConfigFIle = new File(plugin.getDataFolder(), "MobConfig.yml");
        }
        if (armorConfigFile == null) {
            armorConfigFile = new File(plugin.getDataFolder(), "ArmorConfig.yml");
        }
        if (itemConfigFile == null) {
            itemConfigFile = new File(plugin.getDataFolder(), "ItemConfig.yml");
        }
        if (skillConfigFile == null) {
            skillConfigFile = new File(plugin.getDataFolder(), "skillConfig.yml");
        }

        weaponConfig = YamlConfiguration.loadConfiguration(weaponConfigFile);
        chestConfig = YamlConfiguration.loadConfiguration(chestConfigFile);
        mobConfig = YamlConfiguration.loadConfiguration(mobConfigFIle);
        armorConfig = YamlConfiguration.loadConfiguration(armorConfigFile);
        itemConfig = YamlConfiguration.loadConfiguration(itemConfigFile);
        skillConfig = YamlConfiguration.loadConfiguration(skillConfigFile);

        InputStream weaponStream = plugin.getResource("WeaponConfig.yml");
        if (weaponStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(weaponStream));
            weaponConfig.setDefaults(defaultConfig);
        }

        InputStream chestStream = plugin.getResource("ChestConfig.yml");
        if (chestStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(chestStream));
            chestConfig.setDefaults(defaultConfig);
        }

        InputStream mobStream = plugin.getResource("MobConfig.yml");
        if (mobStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(mobStream));
            mobConfig.setDefaults(defaultConfig);
        }
        InputStream armorStream = plugin.getResource("ArmorConfig.yml");
        if (armorStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(armorStream));
            armorConfig.setDefaults(defaultConfig);
        }
        InputStream itemStream = plugin.getResource("ItemConfig.yml");
        if (itemStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(itemStream));
            itemConfig.setDefaults(defaultConfig);
        }
        InputStream skillStream = plugin.getResource("SkillConfig.yml");
        if (skillStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(skillStream));
            skillConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getWeaponConfig() {
        if (weaponConfig == null) reloadConfig();
        return weaponConfig;
    }

    public FileConfiguration getChestConfig() {
        if (chestConfig == null) reloadConfig();
        return chestConfig;
    }

    public FileConfiguration getMobConfig() {
        if (mobConfig == null) reloadConfig();
        return mobConfig;
    }

    public FileConfiguration getArmorsConfig() {
        if (armorConfig == null) reloadConfig();
        return armorConfig;
    }

    public FileConfiguration getItemConfig() {
        if (itemConfig == null) reloadConfig();
        return itemConfig;
    }

    public FileConfiguration getSkillConfig() {
        if (skillConfig == null) reloadConfig();
        return skillConfig;
    }

    public void saveConfig() {
        if (weaponConfig == null || weaponConfigFile == null)
            return;
        try {
            getWeaponConfig().save(this.weaponConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("weaponConfig saveConfig doesn't work");
        }

        if (chestConfig == null || chestConfigFile == null)
            return;
        try {
            getChestConfig().save(this.chestConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("chestConfig saveConfig doesn't work");
        }

        if (mobConfig == null || mobConfigFIle == null)
            return;
        try {
            getMobConfig().save(this.mobConfigFIle);
        } catch (IOException e) {
            plugin.getLogger().severe("mobConfig saveConfig doesn't work");
        }

        if (armorConfig == null || armorConfigFile == null)
            return;
        try {
            getArmorsConfig().save(this.armorConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("armorConfig saveConfig doesn't work");
        }

        if (itemConfig == null || itemConfigFile == null)
            return;
        try {
            getItemConfig().save(this.itemConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("itemConfig saveConfig doesn't work");
        }

        if (skillConfig == null || skillConfigFile == null)
            return;
        try {
            getSkillConfig().save(this.skillConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("skillConfig saveConfig doesn't work");
        }
    }

    public void saveDefaultConfig() {
        if (weaponConfigFile == null) {
            weaponConfigFile = new File(plugin.getDataFolder(), "WeaponConfig.yml");
        }

        if (chestConfigFile == null) {
            chestConfigFile = new File(plugin.getDataFolder(), "ChestConfig.yml");
        }

        if (mobConfigFIle == null) {
            mobConfigFIle = new File(plugin.getDataFolder(), "MobConfig.yml");
        }

        if (armorConfigFile == null) {
            armorConfigFile = new File(plugin.getDataFolder(), "ArmorConfig.yml");
        }

        if (itemConfigFile == null) {
            itemConfigFile = new File(plugin.getDataFolder(), "ItemConfig.yml");
        }

        if (skillConfigFile == null) {
            skillConfigFile = new File(plugin.getDataFolder(), "SkillConfig.yml");
        }

        if (!this.weaponConfigFile.exists())
            plugin.saveResource("WeaponConfig.yml", false);
        if (!this.chestConfigFile.exists())
            plugin.saveResource("ChestConfig.yml", false);
        if (!this.mobConfigFIle.exists())
            plugin.saveResource("MobConfig.yml", false);
        if (!this.armorConfigFile.exists())
            plugin.saveResource("ArmorConfig.yml", false);
        if (!this.itemConfigFile.exists())
            plugin.saveResource("ItemConfig.yml", false);
        if (!this.skillConfigFile.exists())
            plugin.saveResource("SkillConfig.yml", false);
    }
}