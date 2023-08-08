package mc.theKOXpoland.SimpleRPG.Attacks.Skills;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SkillsInterface {

        void useSkill(Player player, ItemStack item);

        double getSkillCooldown();
}

