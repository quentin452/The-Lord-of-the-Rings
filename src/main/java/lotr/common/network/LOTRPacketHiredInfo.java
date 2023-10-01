package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.util.UUID;

public class LOTRPacketHiredInfo implements IMessage {
	public int entityID;
	public boolean isHired;
	public UUID hiringPlayer;
	public LOTRHiredNPCInfo.Task task;
	public String squadron;
	public int xpLvl;

	public LOTRPacketHiredInfo() {
	}

	public LOTRPacketHiredInfo(int i, UUID player, LOTRHiredNPCInfo.Task t, String sq, int lvl) {
		entityID = i;
		hiringPlayer = player;
		isHired = hiringPlayer != null;
		task = t;
		squadron = sq;
		xpLvl = lvl;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		isHired = data.readBoolean();
		hiringPlayer = isHired ? new UUID(data.readLong(), data.readLong()) : null;
		task = LOTRHiredNPCInfo.Task.forID(data.readByte());
		short sqLength = data.readShort();
		if (sqLength > -1) {
			squadron = data.readBytes(sqLength).toString(Charsets.UTF_8);
		}
		xpLvl = data.readShort();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(isHired);
		if (isHired) {
			data.writeLong(hiringPlayer.getMostSignificantBits());
			data.writeLong(hiringPlayer.getLeastSignificantBits());
		}
		data.writeByte(task.ordinal());
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeShort(-1);
		} else {
			byte[] sqBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeShort(sqBytes.length);
			data.writeBytes(sqBytes);
		}
		data.writeShort(xpLvl);
	}

	public static class Handler implements IMessageHandler<LOTRPacketHiredInfo, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketHiredInfo packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).hiredNPCInfo.receiveBasicData(packet);
			}
			return null;
		}
	}

}
