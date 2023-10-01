package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall extends LOTRBlockWallBase {
	public LOTRBlockWall() {
		super(LOTRMod.brick, 16);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (j) {
			case 0:
				return LOTRMod.rock.getIcon(i, 0);
			case 1:
				return LOTRMod.brick.getIcon(i, 0);
			case 2:
				return LOTRMod.rock.getIcon(i, 1);
			case 3:
				return LOTRMod.brick.getIcon(i, 1);
			case 4:
				return LOTRMod.brick.getIcon(i, 2);
			case 5:
				return LOTRMod.brick.getIcon(i, 3);
			case 6:
				return LOTRMod.brick.getIcon(i, 4);
			case 7:
				return LOTRMod.brick.getIcon(i, 6);
			case 8:
				return LOTRMod.rock.getIcon(i, 2);
			case 9:
				return LOTRMod.brick.getIcon(i, 7);
			case 10:
				return LOTRMod.brick.getIcon(i, 11);
			case 11:
				return LOTRMod.brick.getIcon(i, 12);
			case 12:
				return LOTRMod.brick.getIcon(i, 13);
			case 13:
				return LOTRMod.rock.getIcon(i, 3);
			case 14:
				return LOTRMod.brick.getIcon(i, 14);
			case 15:
				return LOTRMod.brick.getIcon(i, 15);
			default:
				break;
		}
		return super.getIcon(i, j);
	}
}
