package mc.theKOXpoland.SimpleRPG.AI.CustomTypes;

import com.destroystokyo.paper.entity.Pathfinder;
import mc.theKOXpoland.SimpleRPG.Constructors.Mobs.CustomMob;

public abstract class NeutralAI implements Pathfinder {

    private final CustomMob customMob;

    public NeutralAI(CustomMob customMob) {
        this.customMob = customMob;
    }

    public boolean shouldExecute() {
        // Add conditions for when the neutral AI should execute
        return true;  // Placeholder, replace with actual conditions
    }

    public void start() {
        // Add logic for starting the neutral AI (e.g., start wandering)
        // You can use custom methods or entity-specific actions here
    }

    public boolean shouldContinue() {
        // Add conditions for when the neutral AI should continue executing
        return true;  // Placeholder, replace with actual conditions
    }

    public void stop() {
        // Add logic for stopping the neutral AI (e.g., stop wandering)
        // You can use custom methods or entity-specific actions here
    }

    public void tick() {
        // Add the main logic for the neutral AI
        // You can use custom methods or entity-specific actions here
    }
}
