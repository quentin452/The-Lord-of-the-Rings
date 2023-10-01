package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.world.EnumDifficulty;

public class LOTRPacketLogin implements IMessage {
	public int ringPortalX;
	public int ringPortalY;
	public int ringPortalZ;
	public int ftCooldownMax;
	public int ftCooldownMin;
	public EnumDifficulty difficulty;
	public boolean difficultyLocked;
	public boolean alignmentZones;
	public boolean feastMode;
	public boolean fellowshipCreation;
	public int fellowshipMaxSize;
	public boolean enchanting;
	public boolean enchantingLOTR;
	public boolean strictFactionTitleRequirements;
	public boolean conquestDecay;
	public int customWaypointMinY;
	public boolean commemorateEmpressShamiir;

	@Override
	public void fromBytes(ByteBuf data) {
		ringPortalX = data.readInt();
		ringPortalY = data.readInt();
		ringPortalZ = data.readInt();
		ftCooldownMax = data.readInt();
		ftCooldownMin = data.readInt();
		int diff = data.readByte();
		if (diff >= 0) {
			difficulty = EnumDifficulty.getDifficultyEnum(diff);
		} else {
			difficulty = null;
		}
		difficultyLocked = data.readBoolean();
		alignmentZones = data.readBoolean();
		feastMode = data.readBoolean();
		fellowshipCreation = data.readBoolean();
		fellowshipMaxSize = data.readInt();
		enchanting = data.readBoolean();
		enchantingLOTR = data.readBoolean();
		strictFactionTitleRequirements = data.readBoolean();
		conquestDecay = data.readBoolean();
		customWaypointMinY = data.readInt();
		commemorateEmpressShamiir = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(ringPortalX);
		data.writeInt(ringPortalY);
		data.writeInt(ringPortalZ);
		data.writeInt(ftCooldownMax);
		data.writeInt(ftCooldownMin);
		int diff = difficulty == null ? -1 : difficulty.getDifficultyId();
		data.writeByte(diff);
		data.writeBoolean(difficultyLocked);
		data.writeBoolean(alignmentZones);
		data.writeBoolean(feastMode);
		data.writeBoolean(fellowshipCreation);
		data.writeInt(fellowshipMaxSize);
		data.writeBoolean(enchanting);
		data.writeBoolean(enchantingLOTR);
		data.writeBoolean(strictFactionTitleRequirements);
		data.writeBoolean(conquestDecay);
		data.writeInt(customWaypointMinY);
		data.writeBoolean(commemorateEmpressShamiir);
	}

	public static class Handler implements IMessageHandler<LOTRPacketLogin, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketLogin packet, MessageContext context) {
			if (!LOTRMod.proxy.isSingleplayer()) {
				LOTRLevelData.destroyAllPlayerData();
			}
			LOTRLevelData.middleEarthPortalX = packet.ringPortalX;
			LOTRLevelData.middleEarthPortalY = packet.ringPortalY;
			LOTRLevelData.middleEarthPortalZ = packet.ringPortalZ;
			LOTRLevelData.setWaypointCooldown(packet.ftCooldownMax, packet.ftCooldownMin);
			EnumDifficulty diff = packet.difficulty;
			if (diff != null) {
				LOTRLevelData.setSavedDifficulty(diff);
				LOTRMod.proxy.setClientDifficulty(diff);
			} else {
				LOTRLevelData.setSavedDifficulty(null);
			}
			LOTRLevelData.setDifficultyLocked(packet.difficultyLocked);
			LOTRLevelData.setEnableAlignmentZones(packet.alignmentZones);
			LOTRLevelData.clientside_thisServer_feastMode = packet.feastMode;
			LOTRLevelData.clientside_thisServer_fellowshipCreation = packet.fellowshipCreation;
			LOTRLevelData.clientside_thisServer_fellowshipMaxSize = packet.fellowshipMaxSize;
			LOTRLevelData.clientside_thisServer_enchanting = packet.enchanting;
			LOTRLevelData.clientside_thisServer_enchantingLOTR = packet.enchantingLOTR;
			LOTRLevelData.clientside_thisServer_strictFactionTitleRequirements = packet.strictFactionTitleRequirements;
			LOTRLevelData.clientside_thisServer_customWaypointMinY = packet.customWaypointMinY;
			LOTRLevelData.clientside_thisServer_commemorateEmpressShamiir = packet.commemorateEmpressShamiir;
			return null;
		}
	}
}