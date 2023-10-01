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

public class LOTRPacketFellowshipToggle extends LOTRPacketFellowshipDo {
	public ToggleFunction function;

	public LOTRPacketFellowshipToggle() {
	}

	public LOTRPacketFellowshipToggle(LOTRFellowshipClient fs, ToggleFunction f) {
		super(fs);
		function = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		function = ToggleFunction.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeByte(function.ordinal());
	}

	public enum ToggleFunction {
		PVP, HIRED_FF, MAP_SHOW
	}

	public static class Handler implements IMessageHandler<LOTRPacketFellowshipToggle, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketFellowshipToggle packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRFellowship fellowship = packet.getActiveFellowship();
			if (fellowship != null) {
				LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
				if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.PVP) {
					boolean current = fellowship.getPreventPVP();
					playerData.setFellowshipPreventPVP(fellowship, !current);
				} else if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF) {
					boolean current = fellowship.getPreventHiredFriendlyFire();
					playerData.setFellowshipPreventHiredFF(fellowship, !current);
				} else if (packet.function == LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW) {
					boolean current = fellowship.getShowMapLocations();
					playerData.setFellowshipShowMapLocations(fellowship, !current);
				}
			}
			return null;
		}
	}
}
