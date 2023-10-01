package lotr.common.network;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.internal.EntitySpawnHandler;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lotr.common.util.LOTRLog;

public class LOTRPacketSpawnMob implements IMessage {
	public ByteBuf spawnData;

	public LOTRPacketSpawnMob() {
	}

	public LOTRPacketSpawnMob(ByteBuf data) {
		spawnData = data.copy();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int len = data.readInt();
		spawnData = data.readBytes(len);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(spawnData.readableBytes());
		data.writeBytes(spawnData);
	}

	public static class AdhocEntitySpawnHandler extends EntitySpawnHandler {

		@Override
		public void channelRead0(ChannelHandlerContext ctx, FMLMessage.EntityMessage msg) throws Exception {
			super.channelRead0(ctx, msg);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketSpawnMob, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketSpawnMob packet, MessageContext context) {
			FMLMessage.EntitySpawnMessage msg = new FMLMessage.EntitySpawnMessage();
			new FMLRuntimeCodec().decodeInto(null, packet.spawnData, msg);
			int modEntityID = 999999999;
			double x = 999.0;
			double y = 999.0;
			double z = 999.0;
			try {
				modEntityID = ObfuscationReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, msg, "modEntityTypeId");
				x = ObfuscationReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, msg, "scaledX");
				y = ObfuscationReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, msg, "scaledY");
				z = ObfuscationReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, msg, "scaledZ");
			} catch (Exception exception) {
			}
			LOTRLog.logger.info("LOTR: Received mob spawn packet: " + modEntityID + "[" + x + ", " + y + ", " + z + "]");
			try {
				new AdhocEntitySpawnHandler().channelRead0(null, msg);
			} catch (Exception e) {
				LOTRLog.logger.error("LOTR: FATAL ERROR spawning entity!!!");
				e.printStackTrace();
			}
			return null;
		}
	}

}
