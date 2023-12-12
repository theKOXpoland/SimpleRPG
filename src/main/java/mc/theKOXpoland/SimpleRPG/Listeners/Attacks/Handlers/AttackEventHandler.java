package mc.theKOXpoland.SimpleRPG.Listeners.Attacks.Handlers;

import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.ArrowHitEvent;
import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.MagicHitEvent;
import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.MeleeHitEvent;
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

        System.out.println("melee");
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

        System.out.println("magick");
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
        System.out.println("arrow");
    }
}
