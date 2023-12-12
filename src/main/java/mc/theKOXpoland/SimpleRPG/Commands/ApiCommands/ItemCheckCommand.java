package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCheckCommand {
    MainFile plugin;
    public ItemCheckCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    public static void ItemCheck() {
        new CommandAPICommand("check")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {
                    Player player = (Player) sender;
                    if (player == null) {
                        return;
                    }
                    ItemStack item = player.getInventory().getItemInMainHand();
                    CustomChecker.getCustomItemType(item);

                })
                .register();
    }
}
