package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTRFishing;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.FishingHooks;

public class LOTRItemTrident extends LOTRItemPolearm {
	public LOTRItemTrident(Item.ToolMaterial material) {
		super(material);
	}

	public LOTRItemTrident(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	public boolean canFishAt(World world, int i, int j, int k) {
		double d = i + 0.5;
		double d1 = j + 0.5;
		double d2 = k + 0.5;
		double d3 = 0.125;
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(d - d3, d1 - d3, d2 - d3, d + d3, d1 + d3, d2 + d3);
		int range = 5;
		for (int l = 0; l < range; ++l) {
			double d5 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (l) / range - d3 + d3;
			double d6 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (l + 1) / range - d3 + d3;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(boundingBox.minX, d5, boundingBox.minZ, boundingBox.maxX, d6, boundingBox.maxZ);
			if (!world.isAABBInMaterial(aabb, Material.water)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int useTick) {
		int k;
		int i;
		int j;
		int usageTime = getMaxItemUseDuration(itemstack) - useTick;
		if (usageTime <= 5) {
			return;
		}
		entityplayer.swingItem();
		MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);
		if (m != null && m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && canFishAt(world, i = m.blockX, j = m.blockY, k = m.blockZ)) {
			for (int l = 0; l < 20; ++l) {
				double d = i + world.rand.nextFloat();
				double d1 = j + world.rand.nextFloat();
				double d2 = k + world.rand.nextFloat();
				String s = world.rand.nextBoolean() ? "bubble" : "splash";
				world.spawnParticle(s, d, d1, d2, 0.0, world.rand.nextFloat() * 0.2f, 0.0);
			}
			if (!world.isRemote) {
				entityplayer.addExhaustion(0.06f);
				if (world.rand.nextInt(3) == 0) {
					world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.splash", 0.5f, 1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4f);
					itemstack.damageItem(1, entityplayer);
					if (world.rand.nextInt(3) == 0) {
						float chance = world.rand.nextFloat();
						int luck = EnchantmentHelper.func_151386_g(entityplayer);
						int speed = EnchantmentHelper.func_151387_h(entityplayer);
						LOTRFishing.FishResult result = LOTRFishing.getFishResult(world.rand, chance, luck, speed, false);
						EntityItem fish = new EntityItem(world, i + 0.5, j + 0.5, k + 0.5, result.fishedItem);
						double d = entityplayer.posX - fish.posX;
						double d1 = entityplayer.posY - fish.posY;
						double d2 = entityplayer.posZ - fish.posZ;
						double dist = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
						double motion = 0.1;
						fish.motionX = d * motion;
						fish.motionY = d1 * motion + MathHelper.sqrt_double(dist) * 0.08;
						fish.motionZ = d2 * motion;
						world.spawnEntityInWorld(fish);
						entityplayer.addStat(result.category.stat, 1);
						world.spawnEntityInWorld(new EntityXPOrb(world, fish.posX, fish.posY, fish.posZ, world.rand.nextInt(3) + 1));
						if (result.category == FishingHooks.FishableCategory.FISH && this == LOTRMod.dunlendingTrident) {
							LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTrident);
						}
					}
				}
			}
		}
	}
}
