package lotr.common.enchant;

import lotr.common.entity.npc.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

import java.text.DecimalFormat;
import java.util.*;

public abstract class LOTREnchantment {
    public static List<LOTREnchantment> allEnchantments = new ArrayList<>();
    private static Map<String, LOTREnchantment> enchantsByName = new HashMap<>();
	public static LOTREnchantment strong1 = new LOTREnchantmentDamage("strong1", 0.5f).setEnchantWeight(10);
	public static LOTREnchantment strong2 = new LOTREnchantmentDamage("strong2", 1.0f).setEnchantWeight(5);
	public static LOTREnchantment strong3 = new LOTREnchantmentDamage("strong3", 2.0f).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment strong4 = new LOTREnchantmentDamage("strong4", 3.0f).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment weak1 = new LOTREnchantmentDamage("weak1", -0.5f).setEnchantWeight(6);
	public static LOTREnchantment weak2 = new LOTREnchantmentDamage("weak2", -1.0f).setEnchantWeight(4);
	public static LOTREnchantment weak3 = new LOTREnchantmentDamage("weak3", -2.0f).setEnchantWeight(2);
	public static LOTREnchantment baneElf = new LOTREnchantmentBane("baneElf", 4.0f, LOTREntityElf.class).setEnchantWeight(0);
	public static LOTREnchantment baneOrc = new LOTREnchantmentBane("baneOrc", 4.0f, LOTREntityOrc.class).setEnchantWeight(0);
	public static LOTREnchantment baneDwarf = new LOTREnchantmentBane("baneDwarf", 4.0f, LOTREntityDwarf.class).setEnchantWeight(0);
	public static LOTREnchantment baneWarg = new LOTREnchantmentBane("baneWarg", 4.0f, LOTREntityWarg.class).setEnchantWeight(0);
	public static LOTREnchantment baneTroll = new LOTREnchantmentBane("baneTroll", 4.0f, LOTREntityTroll.class, LOTREntityHalfTroll.class).setEnchantWeight(0);
	public static LOTREnchantment baneSpider = new LOTREnchantmentBane("baneSpider", 4.0f, EnumCreatureAttribute.ARTHROPOD).setEnchantWeight(0);
	public static LOTREnchantment baneWight = new LOTREnchantmentBane("baneWight", 4.0f, EnumCreatureAttribute.UNDEAD).setEnchantWeight(0);
	public static LOTREnchantment baneWraith = new LOTREnchantmentBane("baneWraith", 0.0f, LOTREntityMarshWraith.class).setUnachievable().setEnchantWeight(0);
	public static LOTREnchantment durable1 = new LOTREnchantmentDurability("durable1", 1.25f).setEnchantWeight(15);
	public static LOTREnchantment durable2 = new LOTREnchantmentDurability("durable2", 1.5f).setEnchantWeight(8);
	public static LOTREnchantment durable3 = new LOTREnchantmentDurability("durable3", 2.0f).setEnchantWeight(4).setSkilful();
	public static LOTREnchantment meleeSpeed1 = new LOTREnchantmentMeleeSpeed("meleeSpeed1", 1.25f).setEnchantWeight(6);
	public static LOTREnchantment meleeSlow1 = new LOTREnchantmentMeleeSpeed("meleeSlow1", 0.75f).setEnchantWeight(4);
	public static LOTREnchantment meleeReach1 = new LOTREnchantmentMeleeReach("meleeReach1", 1.25f).setEnchantWeight(6);
	public static LOTREnchantment meleeUnreach1 = new LOTREnchantmentMeleeReach("meleeUnreach1", 0.75f).setEnchantWeight(4);
	public static LOTREnchantment knockback1 = new LOTREnchantmentKnockback("knockback1", 1).setEnchantWeight(6);
	public static LOTREnchantment knockback2 = new LOTREnchantmentKnockback("knockback2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment toolSpeed1 = new LOTREnchantmentToolSpeed("toolSpeed1", 1.5f).setEnchantWeight(20);
	public static LOTREnchantment toolSpeed2 = new LOTREnchantmentToolSpeed("toolSpeed2", 2.0f).setEnchantWeight(10);
	public static LOTREnchantment toolSpeed3 = new LOTREnchantmentToolSpeed("toolSpeed3", 3.0f).setEnchantWeight(5).setSkilful();
	public static LOTREnchantment toolSpeed4 = new LOTREnchantmentToolSpeed("toolSpeed4", 4.0f).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment toolSlow1 = new LOTREnchantmentToolSpeed("toolSlow1", 0.75f).setEnchantWeight(10);
	public static LOTREnchantment toolSilk = new LOTREnchantmentSilkTouch("toolSilk").setEnchantWeight(10).setSkilful();
	public static LOTREnchantment looting1 = new LOTREnchantmentLooting("looting1", 1).setEnchantWeight(6);
	public static LOTREnchantment looting2 = new LOTREnchantmentLooting("looting2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment looting3 = new LOTREnchantmentLooting("looting3", 3).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment protect1 = new LOTREnchantmentProtection("protect1", 1).setEnchantWeight(10);
	public static LOTREnchantment protect2 = new LOTREnchantmentProtection("protect2", 2).setEnchantWeight(3).setSkilful();
	public static LOTREnchantment protectWeak1 = new LOTREnchantmentProtection("protectWeak1", -1).setEnchantWeight(5);
	public static LOTREnchantment protectWeak2 = new LOTREnchantmentProtection("protectWeak2", -2).setEnchantWeight(2);
	public static LOTREnchantment protectFire1 = new LOTREnchantmentProtectionFire("protectFire1", 1).setEnchantWeight(5);
	public static LOTREnchantment protectFire2 = new LOTREnchantmentProtectionFire("protectFire2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment protectFire3 = new LOTREnchantmentProtectionFire("protectFire3", 3).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment protectFall1 = new LOTREnchantmentProtectionFall("protectFall1", 1).setEnchantWeight(5);
	public static LOTREnchantment protectFall2 = new LOTREnchantmentProtectionFall("protectFall2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment protectFall3 = new LOTREnchantmentProtectionFall("protectFall3", 3).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment protectRanged1 = new LOTREnchantmentProtectionRanged("protectRanged1", 1).setEnchantWeight(5);
	public static LOTREnchantment protectRanged2 = new LOTREnchantmentProtectionRanged("protectRanged2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment protectRanged3 = new LOTREnchantmentProtectionRanged("protectRanged3", 3).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment protectMithril = new LOTREnchantmentProtectionMithril("protectMithril").setEnchantWeight(0);
	public static LOTREnchantment rangedStrong1 = new LOTREnchantmentRangedDamage("rangedStrong1", 1.1f).setEnchantWeight(10);
	public static LOTREnchantment rangedStrong2 = new LOTREnchantmentRangedDamage("rangedStrong2", 1.2f).setEnchantWeight(3);
	public static LOTREnchantment rangedStrong3 = new LOTREnchantmentRangedDamage("rangedStrong3", 1.3f).setEnchantWeight(1).setSkilful();
	public static LOTREnchantment rangedWeak1 = new LOTREnchantmentRangedDamage("rangedWeak1", 0.75f).setEnchantWeight(8);
	public static LOTREnchantment rangedWeak2 = new LOTREnchantmentRangedDamage("rangedWeak2", 0.5f).setEnchantWeight(3);
	public static LOTREnchantment rangedKnockback1 = new LOTREnchantmentRangedKnockback("rangedKnockback1", 1).setEnchantWeight(6);
	public static LOTREnchantment rangedKnockback2 = new LOTREnchantmentRangedKnockback("rangedKnockback2", 2).setEnchantWeight(2).setSkilful();
	public static LOTREnchantment fire = new LOTREnchantmentWeaponSpecial("fire").setEnchantWeight(0).setApplyToProjectile();
	public static LOTREnchantment chill = new LOTREnchantmentWeaponSpecial("chill").setEnchantWeight(0).setApplyToProjectile();
	public static LOTREnchantment headhunting = new LOTREnchantmentWeaponSpecial("headhunting").setCompatibleOtherSpecial().setIncompatibleBane().setEnchantWeight(0).setApplyToProjectile();
	public String enchantName;
	public List<LOTREnchantmentType> itemTypes;
	public int enchantWeight;
	public float valueModifier = 1.0f;
	public boolean skilful;
	public boolean persistsReforge;
	public boolean bypassAnvilLimit;
	public boolean applyToProjectile;

	protected LOTREnchantment(String s, LOTREnchantmentType type) {
		this(s, new LOTREnchantmentType[]{type});
	}

	protected LOTREnchantment(String s, LOTREnchantmentType[] types) {
		enchantName = s;
		itemTypes = Arrays.asList(types);
		allEnchantments.add(this);
		enchantsByName.put(enchantName, this);
	}

	public static LOTREnchantment getEnchantmentByName(String s) {
		return enchantsByName.get(s);
	}

	public boolean applyToProjectile() {
		return applyToProjectile;
	}

	public boolean bypassAnvilLimit() {
		return bypassAnvilLimit;
	}

	public boolean canApply(ItemStack itemstack, boolean considering) {
		for (LOTREnchantmentType type : itemTypes) {
			if (!type.canApply(itemstack, considering)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public String formatAdditive(float f) {
		String s = formatDecimalNumber(f);
		if (f >= 0.0f) {
			s = "+" + s;
		}
		return s;
	}

	public String formatAdditiveInt(int i) {
		String s = String.valueOf(i);
		if (i >= 0) {
			s = "+" + s;
		}
		return s;
	}

	public String formatDecimalNumber(float f) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		return df.format(f);
	}

	public String formatMultiplicative(float f) {
		String s = formatDecimalNumber(f);
		return "x" + s;
	}

	public abstract String getDescription(ItemStack var1);

	public String getDisplayName() {
		return StatCollector.translateToLocal("lotr.enchant." + enchantName);
	}

	public IChatComponent getEarnMessage(ItemStack itemstack) {
		IChatComponent msg = new ChatComponentTranslation("lotr.enchant." + enchantName + ".earn", itemstack.getDisplayName());
		msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		return msg;
	}

	public IChatComponent getEarnMessageWithName(ICommandSender entityplayer, ItemStack itemstack) {
		IChatComponent msg = new ChatComponentTranslation("lotr.enchant." + enchantName + ".earnName", entityplayer.getCommandSenderName(), itemstack.getDisplayName());
		msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		return msg;
	}

	public int getEnchantWeight() {
		return enchantWeight;
	}

	public LOTREnchantment setEnchantWeight(int i) {
		enchantWeight = i;
		return this;
	}

	public String getNamedFormattedDescription(ItemStack itemstack) {
		String s = StatCollector.translateToLocalFormatted("lotr.enchant.descFormat", getDisplayName(), getDescription(itemstack));
		return isBeneficial() ? EnumChatFormatting.GRAY + s : EnumChatFormatting.DARK_GRAY + s;
	}

	public float getValueModifier() {
		return valueModifier;
	}

	public LOTREnchantment setValueModifier(float f) {
		valueModifier = f;
		return this;
	}

	public boolean hasTemplateItem() {
		return enchantWeight > 0 && isBeneficial();
	}

	public abstract boolean isBeneficial();

	public boolean isCompatibleWith(LOTREnchantment other) {
		return getClass() != other.getClass();
	}

	public boolean isSkilful() {
		return skilful;
	}

	public boolean persistsReforge() {
		return persistsReforge;
	}

	public LOTREnchantment setApplyToProjectile() {
		applyToProjectile = true;
		return this;
	}

	public LOTREnchantment setBypassAnvilLimit() {
		bypassAnvilLimit = true;
		return this;
	}

	public LOTREnchantment setPersistsReforge() {
		persistsReforge = true;
		return this;
	}

	public LOTREnchantment setSkilful() {
		skilful = true;
		return this;
	}
}
