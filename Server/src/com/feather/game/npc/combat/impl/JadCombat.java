package com.feather.game.npc.combat.impl;

import com.feather.game.Animation;
import com.feather.game.Entity;
import com.feather.game.Graphics;
import com.feather.game.World;
import com.feather.game.npc.NPC;
import com.feather.game.npc.combat.CombatScript;
import com.feather.game.npc.combat.NPCCombatDefinitions;
import com.feather.game.tasks.WorldTask;
import com.feather.game.tasks.WorldTasksManager;
import com.feather.utils.Utils;

public class JadCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
	
		return new Object[] {2745, 15208};
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(3);
		if(attackStyle == 2) { //melee
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			int size = npc.getSize();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = Utils.random(2); // set mage
			else{
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(
						npc,
						1,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				return defs.getAttackDelay();
			}
		}
		if(attackStyle == 1) { //range
			npc.setNextAnimation(new Animation(16202));
			npc.setNextGraphics(new Graphics(2994));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					target.setNextGraphics(new Graphics(3000));
					delayHit(
							npc,
							1,
							target,
							getRangeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit()-2,
											NPCCombatDefinitions.RANGE, target)));
				}
			}, 3);
		}else{
			npc.setNextAnimation(new Animation( 16195));
			npc.setNextGraphics(new Graphics(2995));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					World.sendProjectile(npc, target, 2996, 80, 30, 40, 20, 5, 0);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							target.setNextGraphics(new Graphics(2741, 0, 100));
							delayHit(
									npc,
									0,
									target,
									getMagicHit(
											npc,
											getRandomMaxHit(npc, defs.getMaxHit()-2,
													NPCCombatDefinitions.MAGE, target)));
						}
						
					}, 1);
				}
			}, 2);
		}
		
		return defs.getAttackDelay()+2;
	}

}
