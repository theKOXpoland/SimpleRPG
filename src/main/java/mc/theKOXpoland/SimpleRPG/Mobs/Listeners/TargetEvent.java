package mc.theKOXpoland.SimpleRPG.Mobs.Listeners;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.persistence.PersistentDataType;

public class TargetEvent implements Listener {

    private static MainFile plugin;
    public TargetEvent(MainFile plugin) {
        TargetEvent.plugin = plugin;
    }

    /*@EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
            String nbtType = entity.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
            if (nbtType != null) {
                if (nbtType.contains("Hostile") || nbtType.contains("Neutral")) {
                    if (entity instanceof Mob mob) {
                        TargetPlayerTask.target(mob, damager, plugin);
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void onEntityTargetEvent(EntityTargetEvent event) {
        Entity target = event.getTarget();

        if (target instanceof Player player) {
            /*if (player.hasPermission("srpg.noTarget")) {
                event.setTarget(null);
                event.setCancelled(true);
            }*/
        }

        if (target != null) {
            if (target.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
                String nbtType = target.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
                if (nbtType != null && nbtType.contains("Hostile")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityLivingTargetEvent(EntityTargetLivingEntityEvent event) {
        Entity target = event.getTarget();

        if (target instanceof Player player) {
           /* if (player.hasPermission("srpg.noTarget")) {
                event.setTarget(null);
                event.setCancelled(true);
            }*/
        }

        if (target != null) {
            if (target.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
                String nbtType = target.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
                if (nbtType != null && nbtType.contains("Hostile")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /*@EventHandler
    public void onEntityMoveEvent(EntityMoveEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getLocation().getWorld();
        Location location = entity.getLocation();

        Location from = event.getFrom();
        Location to = event.getTo();

        if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {

        }

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
            String nbtType = entity.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
            if (nbtType != null && nbtType.contains("Hostile")) {
                event.setCancelled(true);
                List<Entity> nearbyEntities = entity.getNearbyEntities(10, 10, 10);
                nearbyEntities.sort((o1, o2) -> (int) (o1.getLocation().distance(entity.getLocation()) - o2.getLocation().distance(entity.getLocation())));
                Player nearbyPlayer = (Player) nearbyEntities.stream().filter(entity1 -> entity1 instanceof Player).findFirst().orElse(null);
                System.out.println("w poblizu: " + nearbyPlayer + " , inne: " + nearbyEntities.stream().filter(entity1 -> entity1 instanceof Player).map(CommandSender::getName).toList());
                if (nearbyPlayer == null) {
                    System.out.println("nie ma w pobliu");
                    //nie ma w pobliu
                    return;
                }

                if (entity instanceof Mob mob) {
                 System.out.println("");
                    mob.setTarget(nearbyPlayer);
                    mob.getPathfinder().moveTo(nearbyPlayer, 1);
                    System.out.println("TARGET: " +nearbyPlayer.getName());
                        if (mob.getTarget() == null) {
                            mob.getPathfinder().moveTo(location);
                        }
                }
            }
        }

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
            String nbtType = entity.getPersistentDataContainer().get(plugin.Key_NBT_Type, PersistentDataType.STRING);
            if (nbtType != null && nbtType.contains("Hostile")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().getWorld() == world) {
                        if (entity.getNearbyEntities(10, 10, 10).contains(player)) {
                            if (entity instanceof Mob mob) {
                                mob.setTarget(player);
                                mob.getPathfinder().moveTo(player, 1);
                                System.out.println(player);
                                if (mob.getTarget() == null) {
                                    mob.getPathfinder().moveTo(location);
                                }
                            }
                        }
                    }
                }
                List<Entity> nearby =  entity.getNearbyEntities(5,5,5);
                for (Entity nearbyEntity : nearby) {
                    if (nearbyEntity instanceof Damageable damageable) {
                        if (damageable == player) {
                            damageable.damage(1);
                        }
                    }

                    if (nearbyEntity instanceof Monster monster) {
                        monster.getPathfinder().stopPathfinding();
                        return;
                    }
                }
            }
        }
    }*/
}
