package lotr.common.item;

import lotr.common.*;
import lotr.common.fac.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRItemQuenditeCrystal extends LOTRItemWithAnvilNameColor {
	public LOTRItemQuenditeCrystal() {
		super(EnumChatFormatting.DARK_AQUA);
		setCreativeTab(LOTRCreativeTabs.tabMaterials);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
			return false;
		}
		if (world.getBlock(i, j, k) == Blocks.grass) {
			if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.LOTHLORIEN) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HIGH_ELF) >= 1.0f) {
				world.setBlock(i, j, k, LOTRMod.quenditeGrass, 0, 3);
				--itemstack.stackSize;
				for (int l = 0; l < 8; ++l) {
					world.spawnParticle("iconcrack_" + Item.getIdFromItem(this), (double) i + world.rand.nextFloat(), j + 1.5, (double) k + world.rand.nextFloat(), 0.0, 0.0, 0.0);
				}
			} else {
				for (int l = 0; l < 8; ++l) {
					double d = (double) i + world.rand.nextFloat();
					double d1 = j + 1.0;
					double d2 = (double) k + world.rand.nextFloat();
					world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
				}
				if (!world.isRemote) {
					LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, LOTRFaction.LOTHLORIEN, LOTRFaction.HIGH_ELF);
				}
			}
			return true;
		}
		return false;
	}
}
