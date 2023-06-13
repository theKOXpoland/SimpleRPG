package mc.theKOXpoland.SimpleRPG.Managers;

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

    private FileConfiguration weaponsConfig;
    private FileConfiguration chestsConfig;
    private FileConfiguration mobsConfig;
    private FileConfiguration armorsConfg;
    private File weaponsConfigFile;
    private File chestsConfigFile;
    private File mobsConfigFile;
    private File armorsConfigFile;

    public void reloadConfig() {
        if (weaponsConfigFile == null) {
            weaponsConfigFile = new File(plugin.getDataFolder(), "WeaponsConfig.yml");
        }
        if (chestsConfigFile == null) {
            chestsConfigFile = new File(plugin.getDataFolder(), "ChestsConfig.yml");
        }
        if (mobsConfigFile == null) {
            mobsConfigFile = new File(plugin.getDataFolder(), "MobsConfig.yml");
        }
        if (armorsConfigFile == null) {
            armorsConfigFile = new File(plugin.getDataFolder(), "ArmorsConfig.yml");
        }

        weaponsConfig = YamlConfiguration.loadConfiguration(weaponsConfigFile);
        chestsConfig = YamlConfiguration.loadConfiguration(chestsConfigFile);
        mobsConfig = YamlConfiguration.loadConfiguration(mobsConfigFile);
        armorsConfg = YamlConfiguration.loadConfiguration(armorsConfigFile);

        InputStream defaultWeaponsStream = plugin.getResource("WeaponsConfig.yml");
        if (defaultWeaponsStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultWeaponsStream));
            weaponsConfig.setDefaults(defaultConfig);
        }

        InputStream defaultChestsStream = plugin.getResource("ChestsConfig.yml");
        if (defaultChestsStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultChestsStream));
            chestsConfig.setDefaults(defaultConfig);
        }

        InputStream defaultMobsStream = plugin.getResource("MobsConfig.yml");
        if (defaultMobsStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultMobsStream));
            mobsConfig.setDefaults(defaultConfig);
        }
        InputStream defaultArmorsStream = plugin.getResource("ArmorsConfig.yml");
        if (defaultArmorsStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultArmorsStream));
            armorsConfg.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getWeaponsConfig() {
        if (weaponsConfig == null) reloadConfig();
        return weaponsConfig;
    }

    public FileConfiguration getChestConfig() {
        if (chestsConfig == null) reloadConfig();
        return chestsConfig;
    }

    public FileConfiguration getMobsConfig() {
        if (mobsConfig == null) reloadConfig();
        return mobsConfig;
    }

    public FileConfiguration getArmorsConfg() {
        if (armorsConfg == null) reloadConfig();
        return armorsConfg;
    }

    public void saveConfig() {
        if (weaponsConfig == null || weaponsConfigFile == null)
            return;
        try {
            getWeaponsConfig().save(this.weaponsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }

        if (chestsConfig == null || chestsConfigFile == null)
            return;
        try {
            getChestConfig().save(this.chestsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }

        if (mobsConfig == null || mobsConfigFile == null)
            return;
        try {
            getMobsConfig().save(this.mobsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }

        if (armorsConfg == null || armorsConfigFile == null)
            return;
        try {
            getMobsConfig().save(this.armorsConfigFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie dziala");
        }
    }

    public void saveDefaultConfig() {
        if (weaponsConfigFile == null) {
            weaponsConfigFile = new File(plugin.getDataFolder(), "WeaponsConfig.yml");
        }

        if (chestsConfigFile == null) {
            chestsConfigFile = new File(plugin.getDataFolder(), "ChestsConfig.yml");
        }

        if (mobsConfigFile == null) {
            mobsConfigFile = new File(plugin.getDataFolder(), "MobsConfig.yml");
        }

        if (armorsConfigFile == null) {
            armorsConfigFile = new File(plugin.getDataFolder(), "ArmorsConfig.yml");
        }

        if (!this.weaponsConfigFile.exists())
            plugin.saveResource("WeaponsConfig.yml", false);
        if (!this.chestsConfigFile.exists())
            plugin.saveResource("ChestsConfig.yml", false);
        if (!this.mobsConfigFile.exists())
            plugin.saveResource("MobsConfig.yml", false);
        if (!this.armorsConfigFile.exists())
            plugin.saveResource("ArmorsConfig.yml", false);
    }


}


