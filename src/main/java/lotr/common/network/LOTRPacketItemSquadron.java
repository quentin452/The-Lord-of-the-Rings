package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class LOTRPacketItemSquadron implements IMessage {
	public String squadron;

	public LOTRPacketItemSquadron() {
	}

	public LOTRPacketItemSquadron(String s) {
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int length = data.readInt();
		if (length > -1) {
			squadron = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeInt(-1);
		} else {
			byte[] sqBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(sqBytes.length);
			data.writeBytes(sqBytes);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketItemSquadron, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketItemSquadron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
				String squadron = packet.squadron;
				if (StringUtils.isNullOrEmpty(squadron)) {
					LOTRSquadrons.setSquadron(itemstack, "");
				} else {
					squadron = LOTRSquadrons.checkAcceptableLength(squadron);
					LOTRSquadrons.setSquadron(itemstack, squadron);
				}
			}
			return null;
		}
	}

}
