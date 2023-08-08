package mc.theKOXpoland.SimpleRPG.Mobs.Listeners;

import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
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
