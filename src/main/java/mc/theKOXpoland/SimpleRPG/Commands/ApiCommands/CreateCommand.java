package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Items.ArmorCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Items.CustomItemCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Items.WeaponCommand;
import mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Mobs.MobCommand;
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

        allCustomItemsSubCommand.withSubcommands(weaponSubCommand, armorsSubCommand, itemSubCommand);

        CommandAPICommand createCommand = new CommandAPICommand("create")
                .withPermission("srpg.create")
                .withSubcommand(allCustomItemsSubCommand)
                .withSubcommands(mobCommand);

        createCommand.register();
    }
}
