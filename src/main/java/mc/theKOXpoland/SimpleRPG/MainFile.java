package mc.theKOXpoland.SimpleRPG;

import dev.jorel.commandapi.CommandAPI;
<<<<<<< HEAD
import mc.theKOXpoland.SimpleRPG.Attacks.Listeners.PlayerSkillUse;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.ConfigCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.CreateCommand;
import mc.theKOXpoland.SimpleRPG.Commands.SpiralTest;
import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomArmor;
import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomItem;
import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Items.Listeners.*;
import mc.theKOXpoland.SimpleRPG.Items.Managers.ArmorManager;
import mc.theKOXpoland.SimpleRPG.Items.Managers.CustomItemsManager;
import mc.theKOXpoland.SimpleRPG.Items.Managers.ItemManager;
import mc.theKOXpoland.SimpleRPG.Items.Managers.WeaponManager;
import mc.theKOXpoland.SimpleRPG.Mobs.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Mobs.Listeners.*;
import mc.theKOXpoland.SimpleRPG.Mobs.Managers.MobManager;
import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.AttackEventHandler;
import mc.theKOXpoland.SimpleRPG.Attacks.Listeners.AttackListener;
import mc.theKOXpoland.SimpleRPG.Others.Listeners.ChestOpener;
import mc.theKOXpoland.SimpleRPG.Attacks.Listeners.WeaponSkill;
import mc.theKOXpoland.SimpleRPG.Others.Managers.ConfigManager;
import mc.theKOXpoland.SimpleRPG.Attacks.Managers.SkillManager;
=======
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.ConfigCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.CreateCommand;
import mc.theKOXpoland.SimpleRPG.Commands.SpiralTest;
import mc.theKOXpoland.SimpleRPG.Customs.CustomArmors;
import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Listeners.*;
import mc.theKOXpoland.SimpleRPG.Managers.*;
>>>>>>> main
import mc.theKOXpoland.SimpleRPG.Tasks.CooldownTask;
import mc.theKOXpoland.SimpleRPG.Tasks.ItemHeldTask;
import mc.theKOXpoland.SimpleRPG.Tasks.TargetPlayerTask;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcmonkey.sentinel.SentinelPlugin;

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
        armorManager.init();
        itemManager.init();

        skillManager.loadSkills();

<<<<<<< HEAD
        CustomItemsManager.loadAllCustomItemsNamesList();
        CustomItemsManager.loadAllCustomItemsMap();

        SentinelPlugin.instance.registerIntegration(new CustomMob(this));
=======
        CustomItemsManager.loadItemsNamesList();
        CustomItemsManager.loadCustomItemsMap();
>>>>>>> main
        
        Objects.requireNonNull(getCommand("spiraltest")).setExecutor(new SpiralTest(this));

        Bukkit.getPluginManager().registerEvents(new ItemDepravation(this),this);
        Bukkit.getPluginManager().registerEvents(new ItemSwapEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new WeaponSkill(this),this);
        Bukkit.getPluginManager().registerEvents(new MobsRespawnEvent(),this);
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
<<<<<<< HEAD
        new CooldownTask(this).runTaskTimer(this, 0, getConfig().getInt("CooldownTask") * 20L);
        new TargetPlayerTask(this).runTaskTimer(this, 0, getConfig().getInt("TargetPlayerTask") * 20L);
=======
        new CooldownTask(this).runTaskTimer(this, 0, 20);
>>>>>>> main

        Bukkit.getLogger().info("[SimpleRPG]" + ANSI_GREEN + " Activated!" +  ANSI_RESET);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
<<<<<<< HEAD
        CitizensAPI.getNPCRegistries().forEach(NPCRegistry::deregisterAll);
        //configManager.saveConfig();
        CustomItemsManager.clearAllCustomLists();
        CustomItemsManager.clearAllCustomMaps();
        MobManager.customMobsList.clear();
        MobManager.customMobMap.clear();
=======
        configManager.saveConfig();

>>>>>>> main
    }

}