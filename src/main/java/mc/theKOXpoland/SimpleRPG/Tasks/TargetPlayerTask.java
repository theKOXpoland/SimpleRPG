package mc.theKOXpoland.SimpleRPG.Tasks;


import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Mobs.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Mobs.Managers.MobManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcmonkey.sentinel.SentinelPlugin;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetPlayerTask extends BukkitRunnable {

    MainFile plugin;
    public TargetPlayerTask(MainFile plugin) {
        this.plugin = plugin;
    }

    private static final Map<Entity, Player> customEntities = new HashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            /*if (player.hasPermission("srpg.noTarget")) {
                return;
            }*/

            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                return;
            }

            List<Entity> nearbyEntities = player.getNearbyEntities(7, 7, 7);

            for (Entity entity : nearbyEntities) {
                if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
                    String nbtType = entity.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
                    if (nbtType != null && nbtType.contains("Hostile")) {

                        SentinelTrait sentinel = SentinelPlugin.instance.getSentinelFor(player);

                        System.out.println(sentinel);

                        if (sentinel != null) {
                            sentinel.removeTarget("players");

                            sentinel.addTarget("players");
                            break;
                        }

                        customEntities.put(entity, player);
                        for (CustomMob customMob : MobManager.customMobsList) {
                            if (!customMob.isShouldTarget()) {
                                customEntities.remove(entity);
                                return;
                            }
                        }
                    }
                }
            }
        }
        targetPlayer();
    }

    public void targetPlayer() {
            for (Entity entity : customEntities.keySet()) {
                if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

                    String nbtName = entity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                    for (CustomMob customMob : MobManager.customMobsList) {
                            if (customMob.getName().equals(nbtName)) {
                                if (entity instanceof Mob mob) {
                                    if (mob.getTarget() != null) {
                                        if (mob.getTarget().equals(customEntities.get(entity))) {
                                            return;
                                        }
                                        target(mob, entity, plugin);
                                        NPC npc = CitizensAPI.getNPCRegistry().getNPC(customEntities.get(entity));
                                        npc.getNavigator().setTarget(mob, true);
                                    }
                                }

                                List<Entity> nearbyEntities = entity.getNearbyEntities(5, 5, 5);


                                if (customMob.isShouldDamage()) {
                                    for (Entity attackRangeEntities : nearbyEntities) {
                                        if (attackRangeEntities.equals(customEntities.get(entity))) {
                                            if (attackRangeEntities instanceof Player player) {
                                                if (customMob instanceof Mob mob) {
                                                    damage(player, mob, plugin);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
    }

   public static void target(Mob mob, Entity entity, MainFile plugin) {

        if (mob == null) {
            return;
        }

        if (entity == null) {
            return;
        }

        if (mob.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            String nbtName = mob.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

            for (CustomMob customMob : MobManager.customMobsList) {
                if (customMob.getName().equals(nbtName)) {
                    if (mob instanceof CustomMob customMob1) {
                        mob.getPathfinder().moveTo(customEntities.get(entity).getLocation(), customMob1.getSpeed());
                    }
                    break;
                }
            }
        }

        mob.lookAt(customEntities.get(entity));
        mob.setTarget(customEntities.get(entity));
        mob.attack(customEntities.get(entity));
    }

    public static void damage(Player player, Mob mob, MainFile plugin) {

        if (player == null) {
            return;
        }

        if (mob == null) {
            return;
        }

        if (mob.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            String nbtName = mob.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

            for (CustomMob customMob : MobManager.customMobsList) {
                if (customMob.getName().equals(nbtName)) {
                    if (mob instanceof CustomMob customMob1) {
                        player.damage(customMob1.getDamage());
                    }
                    break;
                }
            }
        }
    }
}
