package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class LOTRPacketFellowshipSetIcon extends LOTRPacketFellowshipDo {
	public LOTRPacketFellowshipSetIcon() {
	}

	public LOTRPacketFellowshipSetIcon(LOTRFellowshipClient fs) {
		super(fs);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipSetIcon, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipSetIcon packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRFellowship fellowship = packet.getActiveFellowship();
			if (fellowship != null) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				ItemStack itemstack = entityplayer.getHeldItem();
				if (itemstack != null) {
					ItemStack newStack = itemstack.copy();
					newStack.stackSize = 1;
					playerData.setFellowshipIcon(fellowship, newStack);
				} else {
					playerData.setFellowshipIcon(fellowship, null);
				}
			}
			return null;
		}
	}

}
