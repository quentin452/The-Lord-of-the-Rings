package lotr.common.enchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRDamage;
import lotr.common.item.LOTRItemBalrogWhip;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

public class LOTREnchantmentWeaponSpecial extends LOTREnchantment {
	public boolean compatibleBane = true;
	public boolean compatibleOtherSpecial;

	public LOTREnchantmentWeaponSpecial(String s) {
		super(s, new LOTREnchantmentType[]{LOTREnchantmentType.MELEE, LOTREnchantmentType.THROWING_AXE, LOTREnchantmentType.RANGED_LAUNCHER});
		setValueModifier(3.0f);
		setBypassAnvilLimit();
	}

	public static void doChillAttack(EntityLivingBase entity) {
		if (entity instanceof EntityPlayerMP) {
			LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		int duration = 5;
		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));
		IMessage packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.CHILLING, entity);
		LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entity, 64.0));
	}

	public static int getFireAmount() {
		return 2;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return !(item instanceof LOTRItemBalrogWhip) || this != LOTREnchantment.fire && this != LOTREnchantment.chill;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
			return StatCollector.translateToLocalFormatted("lotr.enchant." + enchantName + ".desc.melee");
		}
		return StatCollector.translateToLocalFormatted("lotr.enchant." + enchantName + ".desc.ranged");
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(LOTREnchantment other) {
		if (!compatibleBane && other instanceof LOTREnchantmentBane) {
			return false;
		}
		return compatibleOtherSpecial || !(other instanceof LOTREnchantmentWeaponSpecial) || ((LOTREnchantmentWeaponSpecial) other).compatibleOtherSpecial;
	}

	public LOTREnchantmentWeaponSpecial setCompatibleOtherSpecial() {
		compatibleOtherSpecial = true;
		return this;
	}

	public LOTREnchantmentWeaponSpecial setIncompatibleBane() {
		compatibleBane = false;
		return this;
	}
}
