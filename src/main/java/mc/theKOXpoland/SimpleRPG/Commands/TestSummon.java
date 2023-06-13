package mc.theKOXpoland.SimpleRPG.Commands;

import mc.theKOXpoland.SimpleRPG.Managers.WeaponManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestSummon implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("testsummon")) {
            if (sender instanceof Player player) {

                //player.getInventory().addItem(WeaponManager.MieczMendy);
                //player.getInventory().addItem(WeaponManager.KosaSmierci);
            }
        }

        return true;
    }
}

