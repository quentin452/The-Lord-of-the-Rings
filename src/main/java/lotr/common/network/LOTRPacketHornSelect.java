package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class LOTRPacketHornSelect implements IMessage {
	public int hornType;

	public LOTRPacketHornSelect() {
	}

	public LOTRPacketHornSelect(int h) {
		hornType = h;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		hornType = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(hornType);
	}

	public static class Handler implements IMessageHandler<LOTRPacketHornSelect, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketHornSelect packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if (itemstack != null && itemstack.getItem() == LOTRMod.commandHorn && itemstack.getItemDamage() == 0) {
				itemstack.setItemDamage(packet.hornType);
			}
			return null;
		}
	}

}
