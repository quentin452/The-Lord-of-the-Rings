package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockGuldurilBrick;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRItemGuldurilCrystal extends LOTRItemWithAnvilNameColor {
	public LOTRItemGuldurilCrystal() {
		super(EnumChatFormatting.DARK_GREEN);
		setCreativeTab(LOTRCreativeTabs.tabMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		boolean hasAlignment;
		if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
			return false;
		}
		int guldurilBrickMeta = LOTRBlockGuldurilBrick.guldurilMetaForBlock(world.getBlock(i, j, k), world.getBlockMetadata(i, j, k));
		hasAlignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) >= 1.0f;
		if (guldurilBrickMeta >= 0) {
			if (hasAlignment) {
				world.setBlock(i, j, k, LOTRMod.guldurilBrick, guldurilBrickMeta, 3);
				--itemstack.stackSize;
				spawnCrystalParticles(world, i, j, k);
			} else {
				for (int l = 0; l < 8; ++l) {
					double d = i - 0.25 + world.rand.nextFloat() * 1.5;
					double d1 = j - 0.25 + world.rand.nextFloat() * 1.5;
					double d2 = k - 0.25 + world.rand.nextFloat() * 1.5;
					world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
				}
				if (!world.isRemote) {
					LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.DOL_GULDUR);
				}
			}
			return true;
		}
		if (world.getBlock(i, j, k) == LOTRMod.sapling && (world.getBlockMetadata(i, j, k) & 3) == 1 && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0f) {
			world.setBlock(i, j, k, LOTRMod.corruptMallorn, 0, 3);
			--itemstack.stackSize;
			spawnCrystalParticles(world, i, j, k);
			return true;
		}
		return false;
	}

	public void spawnCrystalParticles(World world, int i, int j, int k) {
		for (int l = 0; l < 16; ++l) {
			double d = i - 0.25 + world.rand.nextFloat() * 1.5;
			double d1 = j - 0.25 + world.rand.nextFloat() * 1.5;
			double d2 = k - 0.25 + world.rand.nextFloat() * 1.5;
			world.spawnParticle("iconcrack_" + Item.getIdFromItem(this), d, d1, d2, 0.0, 0.0, 0.0);
		}
	}
}
