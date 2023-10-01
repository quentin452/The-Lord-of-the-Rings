package lotr.common.entity.projectile;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRReflection;
import lotr.common.item.LOTRItemRing;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityFishHook extends EntityFishHook {
	public LOTREntityFishHook(World world) {
		super(world);
	}

	public LOTREntityFishHook(World world, EntityPlayer entityplayer) {
		super(world, entityplayer);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
	}

	@Override
	public int func_146034_e() {
		if (worldObj.isRemote) {
			return 0;
		}
		int damage = 0;
		if (field_146043_c != null) {
			double d0 = field_146042_b.posX - posX;
			double d1 = field_146042_b.posY - posY;
			double d2 = field_146042_b.posZ - posZ;
			double dist = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
			double motion = 0.1;
			field_146043_c.motionX += d0 * motion;
			field_146043_c.motionY += d1 * motion + MathHelper.sqrt_double(dist) * 0.08;
			field_146043_c.motionZ += d2 * motion;
			damage = 3;
		} else if (LOTRReflection.getFishHookBobTime(this) > 0) {
			float chance = worldObj.rand.nextFloat();
			int luck = EnchantmentHelper.func_151386_g(field_146042_b);
			int speed = EnchantmentHelper.func_151387_h(field_146042_b);
			LOTRFishing.FishResult result = LOTRFishing.getFishResult(rand, chance, luck, speed, true);
			ItemStack item = result.fishedItem;
			EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, item);
			double d0 = field_146042_b.posX - posX;
			double d1 = field_146042_b.posY - posY;
			double d2 = field_146042_b.posZ - posZ;
			double dist = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
			double motion = 0.1;
			entityitem.motionX = d0 * motion;
			entityitem.motionY = d1 * motion + MathHelper.sqrt_double(dist) * 0.08;
			entityitem.motionZ = d2 * motion;
			worldObj.spawnEntityInWorld(entityitem);
			worldObj.spawnEntityInWorld(new EntityXPOrb(field_146042_b.worldObj, field_146042_b.posX, field_146042_b.posY + 0.5, field_146042_b.posZ + 0.5, rand.nextInt(6) + 1));
			field_146042_b.addStat(result.category.stat, 1);
			if (item.getItem() instanceof LOTRItemRing) {
				LOTRLevelData.getData(field_146042_b).addAchievement(LOTRAchievement.fishRing);
			}
			damage = 1;
		}
		if (LOTRReflection.isFishHookInGround(this)) {
			damage = 2;
		}
		setDead();
		field_146042_b.fishEntity = null;
		return damage;
	}

	public int getPlayerID() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	public void setPlayerID(int id) {
		dataWatcher.updateObject(16, id);
	}

	@Override
	public void onUpdate() {
		if (field_146042_b == null) {
			if (!worldObj.isRemote) {
				setDead();
				return;
			}
			int playerID = getPlayerID();
			Entity player = worldObj.getEntityByID(playerID);
			if (!(player instanceof EntityPlayer)) {
				setDead();
				return;
			}
			EntityPlayer entityplayer = (EntityPlayer) player;
			entityplayer.fishEntity = this;
			field_146042_b = entityplayer;
		}
		super.onUpdate();
	}
}
