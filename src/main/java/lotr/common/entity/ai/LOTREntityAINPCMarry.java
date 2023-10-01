package lotr.common.entity.ai;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class LOTREntityAINPCMarry extends EntityAIBase {
	public LOTREntityNPC theNPC;
	public World theWorld;
	public LOTREntityNPC theSpouse;
	public int marryDelay;
	public double moveSpeed;

	public LOTREntityAINPCMarry(LOTREntityNPC npc, double d) {
		theNPC = npc;
		theWorld = npc.worldObj;
		moveSpeed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return theSpouse != null && theSpouse.isEntityAlive() && theNPC.familyInfo.canMarryNPC(theSpouse) && theSpouse.familyInfo.canMarryNPC(theNPC);
	}

	public void marry() {
		int maxChildren;
		EntityPlayer ringPlayerSpouse;
		theNPC.familyInfo.spouseUniqueID = theSpouse.getUniqueID();
		theSpouse.familyInfo.spouseUniqueID = theNPC.getUniqueID();
		theNPC.setCurrentItemOrArmor(0, null);
		theNPC.setCurrentItemOrArmor(4, new ItemStack(theNPC.familyInfo.marriageRing));
		theSpouse.setCurrentItemOrArmor(0, null);
		theSpouse.setCurrentItemOrArmor(4, new ItemStack(theNPC.familyInfo.marriageRing));
		theNPC.changeNPCNameForMarriage(theSpouse);
		theSpouse.changeNPCNameForMarriage(theNPC);
		theNPC.familyInfo.maxChildren = maxChildren = theNPC.familyInfo.getRandomMaxChildren();
		theSpouse.familyInfo.maxChildren = maxChildren;
		theNPC.familyInfo.setMaxBreedingDelay();
		theSpouse.familyInfo.setMaxBreedingDelay();
		theNPC.spawnHearts();
		theSpouse.spawnHearts();
		EntityPlayer ringPlayer = theNPC.familyInfo.getRingGivingPlayer();
		if (ringPlayer != null) {
			LOTRLevelData.getData(ringPlayer).addAlignment(ringPlayer, LOTRAlignmentValues.MARRIAGE_BONUS, theNPC.getFaction(), theNPC);
			if (theNPC.familyInfo.marriageAchievement != null) {
				LOTRLevelData.getData(ringPlayer).addAchievement(theNPC.familyInfo.marriageAchievement);
			}
		}
		ringPlayerSpouse = theSpouse.familyInfo.getRingGivingPlayer();
		if (ringPlayerSpouse != null) {
			LOTRLevelData.getData(ringPlayerSpouse).addAlignment(ringPlayerSpouse, LOTRAlignmentValues.MARRIAGE_BONUS, theSpouse.getFaction(), theSpouse);
			if (theSpouse.familyInfo.marriageAchievement != null) {
				LOTRLevelData.getData(ringPlayerSpouse).addAchievement(theSpouse.familyInfo.marriageAchievement);
			}
		}
		theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theNPC.posX, theNPC.posY, theNPC.posZ, theNPC.getRNG().nextInt(8) + 2));
	}

	@Override
	public void resetTask() {
		theSpouse = null;
		marryDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theNPC.getClass() != theNPC.familyInfo.marriageEntityClass || theNPC.familyInfo.spouseUniqueID != null || theNPC.familyInfo.getAge() != 0 || theNPC.getEquipmentInSlot(4) != null || theNPC.getEquipmentInSlot(0) == null) {
			return false;
		}
		List<LOTREntityNPC> list = theNPC.worldObj.getEntitiesWithinAABB(theNPC.familyInfo.marriageEntityClass, theNPC.boundingBox.expand(16.0, 4.0, 16.0));
		LOTREntityNPC spouse = null;
		double distanceSq = Double.MAX_VALUE;
		for (LOTREntityNPC candidate : list) {
			double d;
			if (!theNPC.familyInfo.canMarryNPC(candidate) || !candidate.familyInfo.canMarryNPC(theNPC) || (d = theNPC.getDistanceSqToEntity(candidate)) > distanceSq) {
				continue;
			}
			distanceSq = d;
			spouse = candidate;
		}
		if (spouse == null) {
			return false;
		}
		theSpouse = spouse;
		return true;
	}

	@Override
	public void updateTask() {
		theNPC.getLookHelper().setLookPositionWithEntity(theSpouse, 10.0f, theNPC.getVerticalFaceSpeed());
		theNPC.getNavigator().tryMoveToEntityLiving(theSpouse, moveSpeed);
		++marryDelay;
		if (marryDelay % 20 == 0) {
			theNPC.spawnHearts();
		}
		if (marryDelay >= 60 && theNPC.getDistanceSqToEntity(theSpouse) < 9.0) {
			marry();
		}
	}
}
