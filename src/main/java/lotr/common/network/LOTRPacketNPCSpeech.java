package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRPacketNPCSpeech implements IMessage {
	public int entityID;
	public String speech;
	public boolean forceChatMsg;

	public LOTRPacketNPCSpeech() {
	}

	public LOTRPacketNPCSpeech(int id, String s, boolean forceChat) {
		entityID = id;
		speech = s;
		forceChatMsg = forceChat;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		int length = data.readInt();
		speech = data.readBytes(length).toString(Charsets.UTF_8);
		forceChatMsg = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		byte[] speechBytes = speech.getBytes(Charsets.UTF_8);
		data.writeInt(speechBytes.length);
		data.writeBytes(speechBytes);
		data.writeBoolean(forceChatMsg);
	}

	public static class Handler implements IMessageHandler<LOTRPacketNPCSpeech, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketNPCSpeech packet, MessageContext context) {
			World world = LOTRMod.proxy.getClientWorld();
			EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof LOTREntityNPC) {
				LOTREntityNPC npc = (LOTREntityNPC) entity;
				if (LOTRConfig.immersiveSpeech) {
					LOTRMod.proxy.clientReceiveSpeech(npc, packet.speech);
				}
				if (!LOTRConfig.immersiveSpeech || LOTRConfig.immersiveSpeechChatLog || packet.forceChatMsg) {
					String name = npc.getCommandSenderName();
					String message = EnumChatFormatting.YELLOW + "<" + name + "> " + EnumChatFormatting.WHITE + packet.speech;
					entityplayer.addChatMessage(new ChatComponentText(message));
				}
			}
			return null;
		}
	}

}
