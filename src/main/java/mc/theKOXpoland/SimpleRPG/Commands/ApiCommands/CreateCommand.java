package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import mc.theKOXpoland.SimpleRPG.MainFile;

public class CreateCommand {

    MainFile plugin;
    public CreateCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public void CreateItems() {

        CommandAPICommand allCustomItemsSubCommand = new CommandAPICommand("items");
        WeaponCommand weaponSubCommand = new WeaponCommand();
        ArmorCommand armorsSubCommand = new ArmorCommand();
        CustomItemCommand itemSubCommand = new CustomItemCommand();
        MobCommand mobCommand = new MobCommand();

<<<<<<< HEAD
        allCustomItemsSubCommand.withSubcommands(weaponSubCommand, armorsSubCommand, itemSubCommand);
=======
        itemSubCommand.withSubcommands(weaponSubCommand, armorsSubCommand);
>>>>>>> main

        CommandAPICommand createCommand = new CommandAPICommand("create")
                .withPermission("srpg.create")
                .withSubcommand(allCustomItemsSubCommand)
                .withSubcommands(mobCommand);

        createCommand.register();
<<<<<<< HEAD
=======
                /*.withSubcommand(new CommandAPICommand("chest")
                        .executes((sender, args) -> {
                            if (sender instanceof Player player) {

                                ItemStack chest = new ItemStack(Material.CHEST);
                                ItemMeta meta = chest.getItemMeta();
                                meta.addEnchant(Enchantment.LUCK, 1, false);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                chest.setItemMeta(meta);

                                player.getInventory().addItem(chest);

                                ItemStack normalchest = new ItemStack(Material.PLAYER_HEAD, 1);
                                SkullMeta skull = (SkullMeta) normalchest.getItemMeta();
                                skull.addEnchant(Enchantment.LUCK, 1, false);
                                skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                normalchest.setItemMeta(skull);


                                player.getInventory().addItem(createHead("cba77856d80dade55699544ddcd685964b0180823fe4ff270de7e5f58aecf3a5"));
                                player.getInventory().addItem(createHead("56d7fdb50f14c731c727b0e0d189b6a874319fc0d79c8a099acfc77c7b2d9196"));
                                player.getInventory().addItem(createHead("8d6a60e5799f4edb1bf52df49474d7b428c964e4a941328e241a1eab96025869"));
                                player.getInventory().addItem(createHead("c285dd77c64e2368fe77c31ff7c3d42b700fb7b74f2b463e696916c90f5d27ab"));
                                player.getInventory().addItem(createHead("940d127a92d0b3d5b5a32add67ddf7db98c1ad7d54919494662e8deaeb4a0d42"));
                                player.getInventory().addItem(createHead("cd592402a294efed3b8b6e59a53c2fbe71eabd85ed05fa6b0b0baa04303c086f"));


                            }
                        })
                )*/
>>>>>>> main
    }
}
