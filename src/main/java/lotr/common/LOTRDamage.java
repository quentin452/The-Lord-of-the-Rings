package lotr.common;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketEnvironmentOverlay;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class LOTRDamage {
	public static DamageSource frost = new DamageSource("lotr.frost").setDamageBypassesArmor();
	public static DamageSource poisonDrink = new DamageSource("lotr.poisonDrink").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource plantHurt = new DamageSource("lotr.plantHurt").setDamageBypassesArmor();

	public static void doBurnDamage(EntityPlayerMP entityplayer) {
		IMessage packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.BURN);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public static void doFrostDamage(EntityPlayerMP entityplayer) {
		IMessage packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.FROST);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}
}
