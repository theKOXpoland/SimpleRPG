package mc.theKOXpoland.SimpleRPG;

import dev.jorel.commandapi.CommandAPI;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.ConfigCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.CreateCommand;
import mc.theKOXpoland.SimpleRPG.Commands.SpiralTest;
import mc.theKOXpoland.SimpleRPG.Customs.CustomArmors;
import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Listeners.*;
import mc.theKOXpoland.SimpleRPG.Managers.*;
import mc.theKOXpoland.SimpleRPG.Tasks.CooldownTask;
import mc.theKOXpoland.SimpleRPG.Tasks.ItemHeldTask;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class MainFile extends JavaPlugin {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public ConfigManager configManager;
    public WeaponManager weaponManager;
    public MobsManager mobManager;
    public ArmorsManager armorsManager;
    public CustomMob customMob;
    public CustomWeapon customWeapon;
    public CustomArmors customArmors;
   // public CooldownManager cooldownManager;

    public NamespacedKey Key_NBT_Name = new NamespacedKey(this, "NBTName");
    public NamespacedKey Key_NBT_Type = new NamespacedKey(this, "NBTtype");
    public NamespacedKey Key_NBT_First_Attack = new NamespacedKey(this, "NBTFirstAttack");
    public NamespacedKey Key_NBT_Second_Attack = new NamespacedKey(this, "NBTSecondAttack");

    @Override
    public void onLoad() {

        configManager = new ConfigManager(this);
        weaponManager = new WeaponManager(this);
        mobManager = new MobsManager(this);
        armorsManager = new ArmorsManager(this);

        customMob = new CustomMob(this);
        customWeapon = new CustomWeapon(this);
        customArmors = new CustomArmors(this);

        //cooldownManager = new CooldownManager();

        ConfigCommand confingCommand = new ConfigCommand(this);
        CreateCommand createCommand = new CreateCommand(this);

        confingCommand.ConfigReload();
        createCommand.CreateItems();

    }

    @Override
    public void onEnable() {

        CommandAPI.onEnable();

        saveDefaultConfig();

        configManager.saveDefaultConfig();

        weaponManager.init();
        mobManager.init();
        armorsManager.init();

        CustomItemsManager.loadItemsNamesList();
        CustomItemsManager.loadCustomItemsMap();
        
        Objects.requireNonNull(getCommand("spiraltest")).setExecutor(new SpiralTest(this));

        Bukkit.getPluginManager().registerEvents(new ItemDepravation(this),this);
        Bukkit.getPluginManager().registerEvents(new ItemSwapEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new WeaponSkill(this),this);
        Bukkit.getPluginManager().registerEvents(new MobsRespawnEvent(),this);
        Bukkit.getPluginManager().registerEvents(new ChestOpener(this), this);
        Bukkit.getPluginManager().registerEvents(new MobsDeathEvent(this), this);

        new ItemHeldTask(this).runTaskTimer(this,0L,getConfig().getInt("ItemHeldTask") * 20L);
        new CooldownTask(this).runTaskTimer(this, 0, 20);

        Bukkit.getLogger().info("[SimpleRPG]" + ANSI_GREEN + " Activated!" +  ANSI_RESET);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        configManager.saveConfig();

    }

}