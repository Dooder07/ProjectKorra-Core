package com.projectkorra.core.system.user;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.projectkorra.core.system.ability.Ability;
import com.projectkorra.core.system.ability.AbilityActivator;
import com.projectkorra.core.system.ability.AbilityBinds;
import com.projectkorra.core.system.ability.Bindable;
import com.projectkorra.core.system.skill.Skill;
import com.projectkorra.core.system.skill.SkillHolder;

public abstract class SkilledEntity extends SkillHolder implements AbilityActivator {

	private LivingEntity entity;
	private AbilityBinds binds;
	
	SkilledEntity(LivingEntity entity, Collection<Skill> skills, Collection<Skill> toggled, AbilityBinds binds) {
		super(skills, toggled);
		this.entity = entity;
		this.binds = AbilityBinds.copyOf(binds);
	}
	
	public abstract int getCurrentSlot();
	public abstract void sendMessage(String message);
	public abstract boolean hasPermission(String perm);
	
	public LivingEntity getEntity() {
		return entity;
	}
	
	public boolean canBind(Ability ability) {
		return ability instanceof Bindable && this.hasPermission("bending.ability." + ability.getName());
	}
	
	@Override
	public Ability getCurrentAbility() {
		return binds.get(getCurrentSlot()).orElse(null);
	}
	
	@Override
	public Location getLocation() {
		return entity.getEyeLocation();
	}
	
	@Override
	public Vector getDirection() {
		return entity.getLocation().getDirection();
	}
}
