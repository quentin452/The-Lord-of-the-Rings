package lotr.common.entity.npc;

import java.util.UUID;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityWoodElfScout extends LOTREntityWoodElf {
	public static UUID scoutArmorSpeedBoost_id = UUID.fromString("cf0ceb91-0f13-4788-be0e-a6c67a830308");
	public static AttributeModifier scoutArmorSpeedBoost = new AttributeModifier(scoutArmorSpeedBoost_id, "WE Scout armor speed boost", 0.3, 2).setSaved(false);

	public LOTREntityWoodElfScout(World world) {
		super(world);
		tasks.addTask(2, rangedAttackAI);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public EntityAIBase createElfRangedAttackAI() {
		return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
	}

	public void doTeleportEffects() {
		worldObj.playSoundAtEntity(this, "lotr:elf.woodElf_teleport", getSoundVolume(), 0.5f + rand.nextFloat());
		worldObj.setEntityState(this, (byte) 15);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "woodElf/elf/hired";
			}
			if (LOTRLevelData.getData(entityplayer).getAlignment(getFaction()) >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
				return "woodElf/warrior/friendly";
			}
			return "woodElf/elf/neutral";
		}
		return "woodElf/warrior/hostile";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte b) {
		if (b == 15) {
			for (int i = 0; i < 16; ++i) {
				double d = posX + (rand.nextDouble() - 0.5) * width;
				double d1 = posY + rand.nextDouble() * height;
				double d2 = posZ + (rand.nextDouble() - 0.5) * width;
				double d3 = -0.05 + rand.nextFloat() * 0.1f;
				double d4 = -0.05 + rand.nextFloat() * 0.1f;
				double d5 = -0.05 + rand.nextFloat() * 0.1f;
				LOTRMod.proxy.spawnParticle("leafGreen_" + (20 + rand.nextInt(30)), d, d1, d2, d3, d4, d5);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	@Override
	public void onLivingUpdate() {
		ItemStack currentItem;
		EntityLivingBase lastAttacker;
		super.onLivingUpdate();
		if (!worldObj.isRemote && isEntityAlive() && ridingEntity == null && (currentItem = getEquipmentInSlot(0)) != null && currentItem.getItem() instanceof ItemBow && (lastAttacker = getAITarget()) != null && getDistanceSqToEntity(lastAttacker) < 16.0 && rand.nextInt(20) == 0) {
			for (int l = 0; l < 32; ++l) {
				int k;
				int j;
				int i = MathHelper.floor_double(posX) - rand.nextInt(16) + rand.nextInt(16);
				if (getDistance(i, j = MathHelper.floor_double(posY) - rand.nextInt(3) + rand.nextInt(3), k = MathHelper.floor_double(posZ) - rand.nextInt(16) + rand.nextInt(16)) <= 6.0 || !worldObj.getBlock(i, j - 1, k).isNormalCube() || worldObj.getBlock(i, j, k).isNormalCube() || worldObj.getBlock(i, j + 1, k).isNormalCube()) {
					continue;
				}
				double d = i + 0.5;
				double d1 = j;
				double d2 = k + 0.5;
				AxisAlignedBB aabb = boundingBox.copy().offset(d - posX, d1 - posY, d2 - posZ);
				if (!worldObj.checkNoEntityCollision(aabb) || !worldObj.getCollidingBoundingBoxes(this, aabb).isEmpty() || worldObj.isAnyLiquid(aabb)) {
					continue;
				}
				doTeleportEffects();
				setPosition(d, d1, d2);
				break;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElvenScout));
		setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElvenScout));
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElvenScout));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElvenScout));
		}
		return data;
	}
}
