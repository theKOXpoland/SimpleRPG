package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Items;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomItem;
import mc.theKOXpoland.SimpleRPG.Managers.Items.CustomItemsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomItemCommand extends CommandAPICommand {

    public CustomItemCommand() {
        super("item");

        withArguments(new StringArgument("item").replaceSuggestions(ArgumentSuggestions.strings(CustomItem.itemsName)));

        executes((sender, args) -> {
            Player player = (Player) sender;
            String item = String.valueOf(args.get(0));

            if (CustomItemsManager.getAllCustomItemsMap().containsKey(item)) {
                ItemStack itemStack = CustomItemsManager.getAllCustomItemsMap().get(item);
                player.getInventory().addItem(itemStack);
            }
        }, ExecutorType.PLAYER)
                .register();
    }

}