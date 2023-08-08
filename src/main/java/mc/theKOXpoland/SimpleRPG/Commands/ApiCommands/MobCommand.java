package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Mobs.Managers.MobManager;
import org.bukkit.entity.Player;

public class MobCommand extends CommandAPICommand {
    public MobCommand() {
        super("mobs");

        withArguments(new StringArgument("mob").replaceSuggestions(ArgumentSuggestions.strings(MobManager.mobsNames)));

        executes((sender, args) -> {
            Player player = (Player) sender;
            String mob = String.valueOf(args.get(0));

            if (MobManager.customMobMap.containsKey(mob)) {
                if (MobManager.customMobMap.get(mob).getCustomEntityType().equals("NPC")) {
                    MobManager.customMobMap.get(mob).spawnCitizensNPC(player.getLocation());
                    return;
                }
                MobManager.customMobMap.get(mob).spawnEntity(player.getLocation());
            }
        }, ExecutorType.PLAYER)
                .register();
    }
}