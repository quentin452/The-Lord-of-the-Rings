package lotr.common.inventory;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.recipe.LOTRRecipes;
import lotr.common.recipe.LOTRRecipesPouch;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class LOTRContainerCraftingTable extends ContainerWorkbench {
	public World theWorld;
	public int tablePosX;
	public int tablePosY;
	public int tablePosZ;
	public List<IRecipe> recipeList;
	public LOTRBlockCraftingTable tableBlock;

	protected LOTRContainerCraftingTable(InventoryPlayer inv, World world, int i, int j, int k, List<IRecipe> list, Block block) {
		super(inv, world, i, j, k);
		theWorld = world;
		tablePosX = i;
		tablePosY = j;
		tablePosZ = k;
		tableBlock = (LOTRBlockCraftingTable) block;
		recipeList = new ArrayList<>(list);
		recipeList.add(new LOTRRecipesPouch(tableBlock.tableFaction));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theWorld.getBlock(tablePosX, tablePosY, tablePosZ) == tableBlock && entityplayer.getDistanceSq(tablePosX + 0.5, tablePosY + 0.5, tablePosZ + 0.5) <= 64.0;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (recipeList == null) {
			return;
		}
		craftResult.setInventorySlotContents(0, LOTRRecipes.findMatchingRecipe(recipeList, craftMatrix, theWorld));
	}

	public static class Angmar extends LOTRContainerCraftingTable {
		public Angmar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.angmarRecipes, LOTRMod.angmarTable);
		}
	}

	public static class BlueDwarven extends LOTRContainerCraftingTable {
		public BlueDwarven(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.blueMountainsRecipes, LOTRMod.blueDwarvenTable);
		}
	}

	public static class Bree extends LOTRContainerCraftingTable {
		public Bree(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.breeRecipes, LOTRMod.breeTable);
		}
	}

	public static class Dale extends LOTRContainerCraftingTable {
		public Dale(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.daleRecipes, LOTRMod.daleTable);
		}
	}

	public static class DolAmroth extends LOTRContainerCraftingTable {
		public DolAmroth(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.dolAmrothRecipes, LOTRMod.dolAmrothTable);
		}
	}

	public static class DolGuldur extends LOTRContainerCraftingTable {
		public DolGuldur(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.dolGuldurRecipes, LOTRMod.dolGuldurTable);
		}
	}

	public static class Dorwinion extends LOTRContainerCraftingTable {
		public Dorwinion(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.dorwinionRecipes, LOTRMod.dorwinionTable);
		}
	}

	public static class Dunlending extends LOTRContainerCraftingTable {
		public Dunlending(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.dunlendingRecipes, LOTRMod.dunlendingTable);
		}
	}

	public static class Dwarven extends LOTRContainerCraftingTable {
		public Dwarven(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.dwarvenRecipes, LOTRMod.dwarvenTable);
		}
	}

	public static class Elven extends LOTRContainerCraftingTable {
		public Elven(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.elvenRecipes, LOTRMod.elvenTable);
		}
	}

	public static class Gondorian extends LOTRContainerCraftingTable {
		public Gondorian(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.gondorianRecipes, LOTRMod.gondorianTable);
		}
	}

	public static class Gulf extends LOTRContainerCraftingTable {
		public Gulf(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.gulfRecipes, LOTRMod.gulfTable);
		}
	}

	public static class Gundabad extends LOTRContainerCraftingTable {
		public Gundabad(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.gundabadRecipes, LOTRMod.gundabadTable);
		}
	}

	public static class HalfTroll extends LOTRContainerCraftingTable {
		public HalfTroll(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.halfTrollRecipes, LOTRMod.halfTrollTable);
		}
	}

	public static class HighElven extends LOTRContainerCraftingTable {
		public HighElven(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.highElvenRecipes, LOTRMod.highElvenTable);
		}
	}

	public static class Hobbit extends LOTRContainerCraftingTable {
		public Hobbit(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.hobbitRecipes, LOTRMod.hobbitTable);
		}
	}

	public static class Moredain extends LOTRContainerCraftingTable {
		public Moredain(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.moredainRecipes, LOTRMod.moredainTable);
		}
	}

	public static class Morgul extends LOTRContainerCraftingTable {
		public Morgul(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.morgulRecipes, LOTRMod.morgulTable);
		}
	}

	public static class NearHarad extends LOTRContainerCraftingTable {
		public NearHarad(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.nearHaradRecipes, LOTRMod.nearHaradTable);
		}
	}

	public static class Ranger extends LOTRContainerCraftingTable {
		public Ranger(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.rangerRecipes, LOTRMod.rangerTable);
		}
	}

	public static class Rhun extends LOTRContainerCraftingTable {
		public Rhun(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.rhunRecipes, LOTRMod.rhunTable);
		}
	}

	public static class Rivendell extends LOTRContainerCraftingTable {
		public Rivendell(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.rivendellRecipes, LOTRMod.rivendellTable);
		}
	}

	public static class Rohirric extends LOTRContainerCraftingTable {
		public Rohirric(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.rohirricRecipes, LOTRMod.rohirricTable);
		}
	}

	public static class Tauredain extends LOTRContainerCraftingTable {
		public Tauredain(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.tauredainRecipes, LOTRMod.tauredainTable);
		}
	}

	public static class Umbar extends LOTRContainerCraftingTable {
		public Umbar(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.umbarRecipes, LOTRMod.umbarTable);
		}
	}

	public static class Uruk extends LOTRContainerCraftingTable {
		public Uruk(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.urukRecipes, LOTRMod.urukTable);
		}
	}

	public static class WoodElven extends LOTRContainerCraftingTable {
		public WoodElven(InventoryPlayer inv, World world, int i, int j, int k) {
			super(inv, world, i, j, k, LOTRRecipes.woodElvenRecipes, LOTRMod.woodElvenTable);
		}
	}

}
