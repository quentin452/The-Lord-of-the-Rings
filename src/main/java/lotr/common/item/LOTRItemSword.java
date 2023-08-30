package lotr.common.item;

import java.util.UUID;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemSword extends ItemSword {
	@SideOnly(Side.CLIENT)
	public IIcon glowingIcon;
	public boolean isElvenBlade;
	public float lotrWeaponDamage;

	public LOTRItemSword(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(LOTRCreativeTabs.tabCombat);
		lotrWeaponDamage = material.getDamageVsEntity() + 4.0f;
	}

	public LOTRItemSword(LOTRMaterial material) {
		this(material.toToolMaterial());
	}

	public LOTRItemSword addWeaponDamage(float f) {
		lotrWeaponDamage += f;
		return this;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "LOTR Weapon modifier", lotrWeaponDamage, 0));
		return multimap;
	}

	public float getLOTRWeaponDamage() {
		return lotrWeaponDamage;
	}

	public boolean isElvenBlade() {
		return isElvenBlade;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (getItemUseAction(itemstack) == EnumAction.none) {
			return itemstack;
		}
		return super.onItemRightClick(itemstack, world, entityplayer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		if (isElvenBlade) {
			glowingIcon = iconregister.registerIcon(getIconString() + "_glowing");
		}
	}

	public LOTRItemSword setIsElvenBlade() {
		isElvenBlade = true;
		return this;
	}

	public static UUID accessWeaponDamageModifier() {
		return field_111210_e;
	}
}
