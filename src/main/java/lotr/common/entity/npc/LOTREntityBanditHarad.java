package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBanditHarad extends LOTREntityBandit {
	public static ItemStack[] weapons = {new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned)};
	public static int[] robeColors = {3354412, 5984843, 5968655, 3619908, 9007463, 3228720};

	public LOTREntityBanditHarad(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(4, null);
		if (rand.nextInt(4) == 0) {
			ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
			int robeColor = robeColors[rand.nextInt(robeColors.length)];
			LOTRItemHaradRobes.setRobesColor(turban, robeColor);
			if (rand.nextInt(3) == 0) {
				LOTRItemHaradTurban.setHasOrnament(turban, true);
			}
			setCurrentItemOrArmor(4, turban);
		}
		return data;
	}
}
