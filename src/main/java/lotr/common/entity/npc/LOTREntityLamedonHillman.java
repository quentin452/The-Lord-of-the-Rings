package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLamedonHillman extends LOTREntityGondorLevyman {
	public static ItemStack[] hillmanWeapons = {new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
	public static int[] dyedHatColors = {6316128, 2437173, 0};
	public static int[] featherColors = {16777215, 10526880, 5658198, 2179924, 798013};

	public LOTREntityLamedonHillman(World world) {
		super(world);
	}

	@Override
	public EntityAIBase createGondorAttackAI() {
		return new LOTREntityAIAttackOnCollide(this, 1.6, true);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(hillmanWeapons.length);
		npcItemsInv.setMeleeWeapon(hillmanWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
		setCurrentItemOrArmor(2, null);
		setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLamedonJacket));
		if (rand.nextInt(3) == 0) {
			ItemStack hat = new ItemStack(LOTRMod.leatherHat);
			if (rand.nextBoolean()) {
				LOTRItemLeatherHat.setHatColor(hat, dyedHatColors[rand.nextInt(dyedHatColors.length)]);
			}
			if (rand.nextBoolean()) {
				LOTRItemLeatherHat.setFeatherColor(hat, featherColors[rand.nextInt(featherColors.length)]);
			}
			setCurrentItemOrArmor(4, hat);
		} else {
			setCurrentItemOrArmor(4, null);
		}
		return data;
	}
}
