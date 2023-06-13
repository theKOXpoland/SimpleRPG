package mc.theKOXpoland.SimpleRPG.Listeners;

import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.MobsManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobsRespawnEvent implements Listener {

    private static MainFile plugin;
    public MobsRespawnEvent(MainFile plugin) {
        MobsRespawnEvent.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Location location = event.getLocation();
        MobsManager.mobLocation(location);
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            Entity entity = event.getEntity();
            if (entity instanceof Monster) {
                event.setCancelled(true);
            }
            if (!(entity instanceof Monster)) {
                int randomNumber = (int) (Math.random() * 100);
                if (randomNumber <= 79) {
                    MobsManager.customMobsList.get(0);
                    event.setCancelled(true);
                }
            }
        }
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {

            if (MobsManager.customMobsList.size() == 0) {
                return;
            }

            Entity entity = event.getEntity();
            if (entity instanceof Monster) {
                MobsManager.customMobsList.get(0).spawnEntity(location);
                event.setCancelled(true);
            }
            if (!(entity instanceof Monster)) {
                int randomNumber = (int) (Math.random() * 1);
                if (randomNumber <= 1) {
                    event.setCancelled(true);
                    MobsManager.customMobsList.get(0).spawnEntity(location);
                }
            }
        }
    }
}
