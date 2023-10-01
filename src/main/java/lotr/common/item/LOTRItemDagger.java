package lotr.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;

public class LOTRItemDagger extends LOTRItemSword {
	public DaggerEffect effect;

	public LOTRItemDagger(Item.ToolMaterial material) {
		this(material, DaggerEffect.NONE);
	}

	public LOTRItemDagger(Item.ToolMaterial material, DaggerEffect e) {
		super(material);
		lotrWeaponDamage -= 3.0f;
		effect = e;
	}

	public LOTRItemDagger(LOTRMaterial material) {
		this(material, DaggerEffect.NONE);
	}

	public LOTRItemDagger(LOTRMaterial material, DaggerEffect e) {
		this(material.toToolMaterial(), e);
	}

	public static void applyStandardPoison(EntityLivingBase entity) {
		EnumDifficulty difficulty = entity.worldObj.difficultySetting;
		int duration = 1 + difficulty.getDifficultyId() * 2;
		PotionEffect poison = new PotionEffect(Potion.poison.id, (duration + itemRand.nextInt(duration)) * 20);
		entity.addPotionEffect(poison);
	}

	public DaggerEffect getDaggerEffect() {
		return effect;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		itemstack.damageItem(1, user);
		if (effect == DaggerEffect.NONE) {
			return true;
		}
		if (effect == DaggerEffect.POISON) {
			applyStandardPoison(hitEntity);
		}
		return true;
	}

	public enum DaggerEffect {
		NONE, POISON

	}

}
