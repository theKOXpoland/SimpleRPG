package mc.theKOXpoland.SimpleRPG;

import dev.jorel.commandapi.CommandAPI;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.ItemCheckCommand;
import mc.theKOXpoland.SimpleRPG.Listeners.Attacks.Handlers.AttackEventHandler;
import mc.theKOXpoland.SimpleRPG.Listeners.Attacks.AttackListener;
import mc.theKOXpoland.SimpleRPG.Listeners.Attacks.PlayerSkillUse;
import mc.theKOXpoland.SimpleRPG.Listeners.Attacks.WeaponSkill;
import mc.theKOXpoland.SimpleRPG.Managers.Attacks.SkillManager;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.ConfigCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.CreateCommand;
import mc.theKOXpoland.SimpleRPG.Commands.SpiralTest;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomArmor;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomItem;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Managers.Items.ArmorManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.CustomItemsManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.ItemManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.WeaponManager;
import mc.theKOXpoland.SimpleRPG.Listeners.Items.*;
import mc.theKOXpoland.SimpleRPG.Listeners.Mobs.*;
import mc.theKOXpoland.SimpleRPG.Constructors.Mobs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Managers.Mobs.MobManager;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import mc.theKOXpoland.SimpleRPG.Listeners.ChestOpener;
import mc.theKOXpoland.SimpleRPG.Managers.ConfigManager;
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
    public MobManager mobManager;
    public ArmorManager armorManager;
    public ItemManager itemManager;
    public SkillManager skillManager;

    public AttackListener attackListener;

    public CustomMob customMob;
    public CustomWeapon customWeapon;
    public CustomArmor customArmor;
    public CustomItem customItem;

    public CustomChecker customChecker;

    public NamespacedKey Key_NBT_Name = new NamespacedKey(this, "NBTName");
    public NamespacedKey Key_NBT_Type = new NamespacedKey(this, "NBTtype");
    public NamespacedKey Key_NBT_First_Attack = new NamespacedKey(this, "NBTFirstAttack");
    public NamespacedKey Key_NBT_Second_Attack = new NamespacedKey(this, "NBTSecondAttack");

    @Override
    public void onLoad() {

        configManager = new ConfigManager(this);
        weaponManager = new WeaponManager(this);
        mobManager = new MobManager(this);
        armorManager = new ArmorManager(this);
        itemManager = new ItemManager(this);
        skillManager = new SkillManager(this);
        attackListener = new AttackListener(this);

        customMob = new CustomMob(this);
        customWeapon = new CustomWeapon(this);
        customArmor = new CustomArmor(this);
        customItem = new CustomItem(this);
        customChecker = new CustomChecker(this);

        ConfigCommand confingCommand = new ConfigCommand(this);
        CreateCommand createCommand = new CreateCommand(this);
        ItemCheckCommand itemCheckCommand = new ItemCheckCommand(this);

        confingCommand.ConfigReload();
        createCommand.CreateItems();
        ItemCheckCommand.ItemCheck();

    }

    @Override
    public void onEnable() {

        CommandAPI.onEnable();

        saveDefaultConfig();

        configManager.saveDefaultConfig();

        weaponManager.init();
        mobManager.init();
        armorManager.init();
        itemManager.init();

        skillManager.loadSkills();

        CustomItemsManager.loadAllCustomItemsNamesList();
        CustomItemsManager.loadAllCustomItemsMap();
        
        Objects.requireNonNull(getCommand("spiraltest")).setExecutor(new SpiralTest(this));

        Bukkit.getPluginManager().registerEvents(new ItemDepravation(this),this);
        Bukkit.getPluginManager().registerEvents(new ItemSwapEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new WeaponSkill(this),this);
        Bukkit.getPluginManager().registerEvents(new MobsRespawnEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new ChestOpener(this), this);
        Bukkit.getPluginManager().registerEvents(new MobsDeathEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new TargetEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new VillagersEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemReplace(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSkillUse(this), this);
        Bukkit.getPluginManager().registerEvents(new BlocksBreakEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractionEvent(this), this);

        Bukkit.getPluginManager().registerEvents(new AttackListener(this), this);
        Bukkit.getPluginManager().registerEvents(new AttackEventHandler(), this);

        new ItemHeldTask(this).runTaskTimer(this,0L,getConfig().getInt("ItemHeldTask") * 20L);
        new CooldownTask(this).runTaskTimer(this, 0, getConfig().getInt("CooldownTask") * 20L);

        Bukkit.getLogger().info("[SimpleRPG]" + ANSI_GREEN + " Activated!" +  ANSI_RESET);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        //configManager.saveConfig();
        CustomItemsManager.clearAllCustomLists();
        CustomItemsManager.clearAllCustomMaps();
        MobManager.customMobsList.clear();
        MobManager.customMobMap.clear();
    }

}