package mc.theKOXpoland.SimpleRPG.Others.Listeners;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.time.temporal.ValueRange;
import java.util.*;

public class ChestOpener implements Listener {

    private static MainFile plugin;
    public ChestOpener(MainFile plugin) {
        ChestOpener.plugin = plugin;
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.CHEST) {
                Chest chest = (Chest) event.getClickedBlock().getState();
                if (chest.hasLootTable()) {
                    if (!chest.hasPlayerLooted(player) || !chest.getBlockInventory().isEmpty()) {
                        int randomNumber = (int) (Math.random() * 100);
                        int percent = plugin.configManager.getChestConfig().getInt("itemChance");
                        //Getting random number from 0 to 99, if number is <= % form config, then action is a go
                        if (randomNumber <= percent) {

                            Map<Integer, Integer> slotList = new HashMap<>();

                            for (int i = 0; i < chest.getBlockInventory().getSize(); i++) {

                                List<Integer> slots = new ArrayList<>();

                                Inventory chestInventory = chest.getBlockInventory();
                                ItemStack item = chestInventory.getItem(i);

                                    if (item == null || item.getType() == Material.AIR ) {
                                        slots.add(i);
                                        for (int j = 0; j < slots.size(); j++) {
                                            slotList.put(j, i);
                                        }
                                    }
                            }

                            List<String> weapons = plugin.configManager.getChestConfig().getStringList("items");

                            double totalChance = 0;
                            double splitedChance;
                            double firstNumber = 0;
                            ValueRange range;

                            Map<ValueRange, ItemStack> rangeItem = new HashMap<>();

                            for (String wepString : weapons) {
                                String[] splitedWeapons = wepString.split(":");
                                for (String key : CustomWeapon.customWeaponsMap.keySet()) {
                                    if (splitedWeapons[0].equals(key)) {
                                        if (!rangeItem.containsValue(CustomWeapon.customWeaponsMap.get(key))) {

                                            splitedChance = Double.parseDouble(splitedWeapons[1]);

                                            totalChance = totalChance + splitedChance;

                                            range = ValueRange.of((long) firstNumber, (long) totalChance);
                                            rangeItem.put(range, CustomWeapon.customWeaponsMap.get(key));

                                            firstNumber = firstNumber + splitedChance;
                                        }
                                    }
                                }
                            }

                            double random = Math.random() * totalChance;

                            Set<ValueRange> rangeList = rangeItem.keySet();
                            for (ValueRange keys : rangeList) {
                                if (keys.isValidValue((long) Util.roundDouble(random, 2))) {

                                    int slot = Util.getRandomNumber(0, slotList.get(Util.getRandomNumber(0, slotList.size())));

                                    ItemStack chosenWeapon = rangeItem.get(keys);

                                    chest.getBlockInventory().setItem(slot, chosenWeapon);

                                    System.out.println(player.getName() + " got " + chosenWeapon.getItemMeta().getPersistentDataContainer()
                                            .get(plugin.Key_NBT_Name, PersistentDataType.STRING) + " no cords " + chest.getLocation().getBlock());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}