package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketFamilyInfo implements IMessage {
	public int entityID;
	public int age;
	public boolean isMale;
	public String name;
	public boolean isDrunk;

	public LOTRPacketFamilyInfo() {
	}

	public LOTRPacketFamilyInfo(int id, int a, boolean m, String s, boolean drunk) {
		entityID = id;
		age = a;
		isMale = m;
		name = s;
		isDrunk = drunk;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		age = data.readInt();
		isMale = data.readBoolean();
		short nameLength = data.readShort();
		if (nameLength > -1) {
			name = data.readBytes(nameLength).toString(Charsets.UTF_8);
		}
		isDrunk = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(age);
		data.writeBoolean(isMale);
		if (name == null) {
			data.writeShort(-1);
		} else {
			byte[] nameBytes = name.getBytes(Charsets.UTF_8);
			data.writeShort(nameBytes.length);
			data.writeBytes(nameBytes);
		}
		data.writeBoolean(isDrunk);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFamilyInfo, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFamilyInfo packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				((LOTREntityNPC) entity).familyInfo.receiveData(packet);
			}
			return null;
		}
	}

}
