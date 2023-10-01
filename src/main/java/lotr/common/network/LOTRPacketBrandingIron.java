package lotr.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.item.LOTRItemBrandingIron;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class LOTRPacketBrandingIron implements IMessage {
	public String brandName;

	public LOTRPacketBrandingIron() {
	}

	public LOTRPacketBrandingIron(String s) {
		brandName = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int length = data.readInt();
		if (length > -1) {
			brandName = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (StringUtils.isBlank(brandName)) {
			data.writeInt(-1);
		} else {
			byte[] brandBytes = brandName.getBytes(Charsets.UTF_8);
			data.writeInt(brandBytes.length);
			data.writeBytes(brandBytes);
		}
	}

	public static class Handler implements IMessageHandler<LOTRPacketBrandingIron, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketBrandingIron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
				String brandName = packet.brandName;
				if (!StringUtils.isBlank(brandName = LOTRItemBrandingIron.trimAcceptableBrandName(brandName)) && !LOTRItemBrandingIron.hasBrandName(itemstack)) {
					LOTRItemBrandingIron.setBrandName(itemstack, brandName);
				}
			}
			return null;
		}
	}

}
