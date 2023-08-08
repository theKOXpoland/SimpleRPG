package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AttackEventHandler implements Listener {

    @EventHandler
    public void onMeleeHit(MeleeHitEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        double damage = event.getDamage();

        if (target == player) {
            return;
        }

        if (target instanceof Player playerTarget) {
           if (playerTarget.isInvisible() || playerTarget.isInvisible()) {
               return;
           }
        }

        if (target instanceof Damageable damageable) {
            damageable.damage(damage);
        }
    }

    @EventHandler
    public void onMagicHit(MagicHitEvent event) {
        Player player = event.getPlayer();
        Entity target = event.getTarget();
        double damage = event.getDamage();

            if (target == player) {
                return;
            }

            if (target instanceof Player playerTarget) {
                if (playerTarget.isInvisible() || playerTarget.isInvisible()) {
                    return;
                }
            }

            if (target instanceof Damageable damageable) {
                damageable.damage(damage);
            }
    }

    @EventHandler
    public void onArrowHit(ArrowHitEvent event) {
        Player player = event.getShooter();
        Entity target = event.getTarget();
        double damage = event.getDamage();

        if (target == player) {
            return;
        }

        if (target instanceof Player playerTarget) {
            if (playerTarget.isInvisible() || playerTarget.isInvisible()) {
                return;
            }
        }

        if (target instanceof Damageable damageable) {
            damageable.damage(damage);
        }
    }
}
