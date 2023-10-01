package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketBannerValidate implements IMessage {
	public int entityID;
	public int slot;
	public String prevText;
	public boolean valid;

	public LOTRPacketBannerValidate() {
	}

	public LOTRPacketBannerValidate(int id, int s, String pre, boolean val) {
		entityID = id;
		slot = s;
		prevText = pre;
		valid = val;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		slot = data.readInt();
		byte length = data.readByte();
		ByteBuf textBytes = data.readBytes(length);
		prevText = textBytes.toString(Charsets.UTF_8);
		valid = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(slot);
		byte[] textBytes = prevText.getBytes(Charsets.UTF_8);
		data.writeByte(textBytes.length);
		data.writeBytes(textBytes);
		data.writeBoolean(valid);
	}

	public static class Handler implements IMessageHandler<LOTRPacketBannerValidate, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBannerValidate packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityBanner) {
				LOTREntityBanner banner = (LOTREntityBanner) entity;
				LOTRMod.proxy.validateBannerUsername(banner, packet.slot, packet.prevText, packet.valid);
			}
			return null;
		}
	}

}
