package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRPacketOpenSignEditor implements IMessage {
	public int posX;
	public int posY;
	public int posZ;
	public Block blockType;
	public int blockMeta;

	public LOTRPacketOpenSignEditor() {
	}

	public LOTRPacketOpenSignEditor(LOTRTileEntitySign sign) {
		posX = sign.xCoord;
		posY = sign.yCoord;
		posZ = sign.zCoord;
		blockType = sign.getBlockType();
		blockMeta = sign.getBlockMetadata();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readInt();
		posY = data.readInt();
		posZ = data.readInt();
		blockType = Block.getBlockById(data.readShort());
		blockMeta = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(posX);
		data.writeInt(posY);
		data.writeInt(posZ);
		data.writeShort(Block.getIdFromBlock(blockType));
		data.writeByte(blockMeta);
	}

	public static class Handler implements IMessageHandler<LOTRPacketOpenSignEditor, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketOpenSignEditor packet, MessageContext context) {
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			World world = LOTRMod.proxy.getClientWorld();
			world.setBlock(packet.posX, packet.posY, packet.posZ, packet.blockType, packet.blockMeta, 3);
			entityplayer.openGui(LOTRMod.instance, 47, world, packet.posX, packet.posY, packet.posZ);
			return null;
		}
	}

}
