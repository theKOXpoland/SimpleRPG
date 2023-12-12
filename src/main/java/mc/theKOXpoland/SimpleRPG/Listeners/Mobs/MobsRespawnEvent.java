package mc.theKOXpoland.SimpleRPG.Listeners.Mobs;

import mc.theKOXpoland.SimpleRPG.Constructors.Mobs.CustomMob;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.Mobs.MobManager;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.time.temporal.ValueRange;
import java.util.*;

public class MobsRespawnEvent implements Listener {

    private static MainFile plugin;
    public MobsRespawnEvent(MainFile plugin) {
        MobsRespawnEvent.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Location location = event.getLocation();
        MobManager.mobLocation(location);
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            Entity entity = event.getEntity();

            double totalChance = 0;
            double splitedChance;
            double firstNumber = 0;

            ValueRange range;

            Map<ValueRange, String> rangeResp = new HashMap<>();
            List<EntityType> mobsList = new ArrayList<>();

            for (CustomMob customMob : MobManager.customMobsList) {
                if (customMob.isRespNaturally()) {

                    for (String worldString : customMob.getWorldResp()) {

                        if (!worldString.equalsIgnoreCase("ALL".toUpperCase())) {
                            World world;
                            try {
                                world = plugin.getServer().getWorld(worldString.toLowerCase());
                            } catch (IllegalArgumentException exp) {
                                plugin.getLogger().severe(customMob.getName() + " world is wrong!");
                                world = location.getWorld();
                            }

                            if (location.getWorld() != world) {
                                return;
                            }
                        }
                    }

                    for (String biomeString : customMob.getBiomeResp()) {

                        if (!biomeString.equalsIgnoreCase("ALL".toUpperCase())) {
                            Biome biome;
                            try {
                                biome = Biome.valueOf(biomeString.toUpperCase());
                            } catch (IllegalArgumentException exp) {
                                plugin.getLogger().severe(customMob.getName() + " biome is wrong!");
                                biome = Biome.PLAINS;
                             }

                            if (location.getBlock().getBiome() != biome) {
                                return;
                            }
                        }
                    }

                    for (String respawnInstead : customMob.getRespInstead()) {
                        String[] splitted = respawnInstead.split(":");
                        if (splitted[0].equals("Default")) {
                            splitedChance = Double.parseDouble(splitted[1]);

                            totalChance = totalChance + splitedChance;

                            range = ValueRange.of((long) firstNumber, (long) totalChance);
                            rangeResp.put(range, entity.getName());

                            firstNumber = firstNumber + splitedChance;
                        } else {
                            EntityType splitEdentityType = EntityType.valueOf(splitted[0].toUpperCase());
                            mobsList.add(splitEdentityType);
                            if (entity.getType() == splitEdentityType) {
                                splitedChance = Double.parseDouble(splitted[1]);

                                totalChance = totalChance + splitedChance;

                                range = ValueRange.of((long) firstNumber, (long) totalChance);
                                rangeResp.put(range, customMob.getName());

                                firstNumber = firstNumber + splitedChance;
                            }
                        }
                    }
                }
            }

            if (!mobsList.contains(entity.getType())) {
                return;
            }

            if (totalChance < 100) {
                firstNumber = totalChance + 1;
                splitedChance = (++totalChance - 100) * -1 + totalChance;
                range = ValueRange.of((long) firstNumber, (long) splitedChance);
                rangeResp.put(range, entity.getName());
            }

            double random = Math.random() * totalChance;

            Set<ValueRange> rangeList = rangeResp.keySet();

            for (ValueRange keys : rangeList) {
                if (keys.isValidValue((long) Util.roundDouble(random, 2))) {
                    String choosenResp = rangeResp.get(keys);

                    if (choosenResp.equals(entity.getName())) {
                        return;
                    }
                    else {
                        event.setCancelled(true);
                        MobManager.customMobMap.get(choosenResp).spawnEntity(location);
                    }
                }
            }
        }
    }
}
