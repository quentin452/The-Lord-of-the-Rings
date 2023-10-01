package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketBrokenPledge implements IMessage {
	public int cooldown;
	public int cooldownStart;
	public LOTRFaction brokenFac;

	public LOTRPacketBrokenPledge() {
	}

	public LOTRPacketBrokenPledge(int cd, int start, LOTRFaction f) {
		cooldown = cd;
		cooldownStart = start;
		brokenFac = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cooldown = data.readInt();
		cooldownStart = data.readInt();
		byte facID = data.readByte();
		if (facID >= 0) {
			brokenFac = LOTRFaction.forID(facID);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cooldown);
		data.writeInt(cooldownStart);
		if (brokenFac == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(brokenFac.ordinal());
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketBrokenPledge, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBrokenPledge packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			pd.setPledgeBreakCooldown(packet.cooldown);
			pd.setPledgeBreakCooldownStart(packet.cooldownStart);
			pd.setBrokenPledgeFaction(packet.brokenFac);
			return null;
		}
	}

}
