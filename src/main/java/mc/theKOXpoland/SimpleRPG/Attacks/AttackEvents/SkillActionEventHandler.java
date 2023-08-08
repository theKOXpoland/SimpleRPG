package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillActionEventHandler implements Listener {

    @EventHandler
    public void onSkillAction(SkillActionEvent event) {
        // Get the player who triggered the skill action
        Player player = event.getPlayer();

        // Perform skill logic here, based on the specific action you want to associate with the skill
        // Example: Apply effects, teleport, etc.
    }
}
