package com.projectkorra.core.game.firebending.firejet;

import com.projectkorra.core.ability.Ability;
import com.projectkorra.core.ability.AbilityInstance;
import com.projectkorra.core.ability.AbilityUser;
import com.projectkorra.core.ability.attribute.Attribute;
import com.projectkorra.core.util.Effects;
import com.projectkorra.core.util.Particles;

import org.bukkit.Particle;
import org.bukkit.Sound;

public class JetJumpInstance extends AbilityInstance {

    @Attribute(Attribute.SPEED)
    private double speed;
    @Attribute(Attribute.COOLDOWN)
    private long cooldown;

    public JetJumpInstance(Ability provider, AbilityUser user, double speed, long cooldown) {
        super(provider, user);
        this.speed = speed;
        this.cooldown = cooldown;
    }

    @Override
    protected void onStart() {
        Effects.playSound(user.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1f, 0.6f);
        Particles.firebending(user.getLocation(), 30, 0.2, 0.4, 0.2);
        user.addCooldown("JetExtra", cooldown);
        Particles.spawn(Particle.EXPLOSION_LARGE, user.getLocation());
    }

    @Override
    protected boolean onUpdate(double timeDelta) {
        if (this.ticksLived() < 1) {
            user.getEntity().setVelocity(user.getEntity().getVelocity().add(user.getDirection().setY(speed)));
        } else if (this.timeLived() > 400) {
            return false;
        }

        Particles.firebending(user.getLocation(), 10, 0.1, 0.1, 0.1);
        return true;
    }

    @Override
    protected void postUpdate() {}

    @Override
    protected void onStop() {}

    @Override
    public String getName() {
        return "JetJump";
    }
    
}
