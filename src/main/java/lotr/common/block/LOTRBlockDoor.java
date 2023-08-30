package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRBlockDoor extends BlockDoor {
	public LOTRBlockDoor() {
		this(Material.wood);
		setStepSound(Block.soundTypeWood);
		setHardness(3.0f);
	}

	public LOTRBlockDoor(Material material) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabUtil);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return (i & 8) != 0 ? null : Item.getItemFromBlock(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemIconName() {
		return getTextureName();
	}

	@Override
	public int getRenderType() {
		return LOTRMod.proxy.getDoorRenderID();
	}
}
