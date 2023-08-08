package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomArmor;
import mc.theKOXpoland.SimpleRPG.Items.Managers.CustomItemsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorCommand extends CommandAPICommand {

    public ArmorCommand() {
        super("armor");

        withArguments(new StringArgument("armor").replaceSuggestions(ArgumentSuggestions.strings(CustomArmor.armorsName)));

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