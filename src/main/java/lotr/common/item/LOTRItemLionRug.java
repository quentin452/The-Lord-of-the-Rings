package lotr.common.item;

import lotr.common.entity.item.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Locale;

public class LOTRItemLionRug extends LOTRItemRugBase {
	public LOTRItemLionRug() {
		super(LionRugType.lionRugNames());
	}

	@Override
	public LOTREntityRugBase createRug(World world, ItemStack itemstack) {
		LOTREntityLionRug rug = new LOTREntityLionRug(world);
		rug.setRugType(LionRugType.forID(itemstack.getItemDamage()));
		return rug;
	}

	public enum LionRugType {
		LION(0), LIONESS(1);

		public int lionID;

		LionRugType(int i) {
			lionID = i;
		}

		public String textureName() {
			return name().toLowerCase(Locale.ROOT);
		}

		public static LionRugType forID(int ID) {
			for (LionRugType t : values()) {
				if (t.lionID != ID) {
					continue;
				}
				return t;
			}
			return LION;
		}

		public static String[] lionRugNames() {
			String[] names = new String[values().length];
			for (int i = 0; i < names.length; ++i) {
				names[i] = values()[i].textureName();
			}
			return names;
		}
	}

}
