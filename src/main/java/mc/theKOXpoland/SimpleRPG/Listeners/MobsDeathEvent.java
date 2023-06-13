package mc.theKOXpoland.SimpleRPG.Listeners;

import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.CustomItemsManager;
import mc.theKOXpoland.SimpleRPG.Managers.MobsManager;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MobsDeathEvent implements Listener {

    private static MainFile plugin;
    public MobsDeathEvent(MainFile plugin) {
        MobsDeathEvent.plugin = plugin;
    }

    @EventHandler
    public void onCreatureDeath(EntityDeathEvent event) {
        Entity dedEntity = event.getEntity();

        if (dedEntity.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            for (String key : MobsManager.customMobMap.keySet()) {
                if (dedEntity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING).contains(key)) {
                    CustomMob killedMob = MobsManager.customMobMap.get(key);
                    event.setDroppedExp(killedMob.getDroppedExp());

                    double totalChance = 0;
                    double splitedChance;
                    double firstNumber = 0;
                    ValueRange range;

                    Map<ValueRange, ItemStack> rangeItem = new HashMap<>();

                    for (String wepString :  killedMob.getMobDrop()) {
                        String[] splitedWeapons = wepString.split(":");
                        if (Material.matchMaterial(splitedWeapons[0]) != null || splitedWeapons[0].equals("None")) {
                            splitedChance = Double.parseDouble(splitedWeapons[1]);

                            totalChance = totalChance + splitedChance;

                            range = ValueRange.of((long) firstNumber, (long) totalChance);
                            ItemStack Item = new ItemStack(Material.AIR, 1);
                            rangeItem.put(range, Item);

                            firstNumber = firstNumber + splitedChance;
                        }
                        for (String dropedItem : CustomItemsManager.getCustomItemsMap().keySet()) {
                            if (splitedWeapons[0].equals(dropedItem)) {
                                if (!rangeItem.containsValue(CustomItemsManager.getCustomItemsMap().get(dropedItem))) {

                                    splitedChance = Double.parseDouble(splitedWeapons[1]);

                                    totalChance = totalChance + splitedChance;

                                    range = ValueRange.of((long) firstNumber, (long) totalChance);
                                    rangeItem.put(range, CustomItemsManager.getCustomItemsMap().get(dropedItem));


                                    firstNumber = firstNumber + splitedChance;
                                }
                            }
                        }
                    }

                    double random = Math.random() * totalChance;

                    Set<ValueRange> rangeList = rangeItem.keySet();
                    for (ValueRange keys : rangeList) {
                        if (keys.isValidValue((long) Util.roundDouble(random, 2))) {
                            ItemStack choosenDrop = rangeItem.get(keys);

                            event.getDrops().clear();
                            event.getDrops().add(choosenDrop);

                            if (choosenDrop.getItemMeta() != null) {
                                System.out.println(dedEntity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING)
                                        + " dropnął " + choosenDrop.getItemMeta().getPersistentDataContainer()
                                        .get(plugin.Key_NBT_Name, PersistentDataType.STRING));
                            } else {
                                System.out.println(dedEntity.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING)
                                        + " dropnął " + choosenDrop.getType());
                            }
                        }
                    }

                }
            }
        }
    }
}
