package mc.theKOXpoland.SimpleRPG.Listeners.Mobs;

import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class VillagersEvents implements Listener {

    private static MainFile plugin;
    public VillagersEvents(MainFile plugin) {
        VillagersEvents.plugin = plugin;
    }

    @EventHandler
    public void onCareerChangeEvent(VillagerCareerChangeEvent event) {
        Entity target = event.getEntity();

        if (CustomChecker.mobNamesEquals(target)) {
            event.setCancelled(true);
        }
    }
}
