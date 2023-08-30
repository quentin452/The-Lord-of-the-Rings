package lotr.common.block;

import java.util.*;

import lotr.common.*;
import lotr.common.fac.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRBlockCraftingTable extends Block {
	public static Collection<LOTRBlockCraftingTable> allCraftingTables = new ArrayList<>();
	public LOTRFaction tableFaction;
	public int tableGUIID;

	public LOTRBlockCraftingTable(Material material, LOTRFaction faction, int guiID) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
		setHardness(2.5f);
		tableFaction = faction;
		tableGUIID = guiID;
		allCraftingTables.add(this);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		boolean hasRequiredAlignment;
		hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(tableFaction) >= 1.0f;
		if (hasRequiredAlignment) {
			if (!world.isRemote) {
				entityplayer.openGui(LOTRMod.instance, tableGUIID, world, i, j, k);
			}
		} else {
			for (int l = 0; l < 8; ++l) {
				double d = i + world.rand.nextFloat();
				double d1 = j + 1.0;
				double d2 = k + world.rand.nextFloat();
				world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
			}
			if (!world.isRemote) {
				LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, tableFaction);
			}
		}
		return true;
	}
}
