package lotr.common.tileentity;

import java.util.Arrays;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class LOTRTileEntitySign extends TileEntity {
	public static int MAX_LINE_LENGTH = 15;
	public String[] signText = new String[getNumLines()];
	public int lineBeingEdited = -1;
	public boolean editable = true;
	public EntityPlayer editingPlayer;
	public boolean isFakeGuiSign;

	protected LOTRTileEntitySign() {
		Arrays.fill(signText, "");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeSignText(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public EntityPlayer getEditingPlayer() {
		return editingPlayer;
	}

	public abstract int getNumLines();

	public boolean isEditable() {
		return editable;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readSignText(data);
	}

	public void openEditGUI(EntityPlayerMP entityplayer) {
		editingPlayer = entityplayer;
		IMessage packet = new LOTRPacketOpenSignEditor(this);
		LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		editable = false;
		super.readFromNBT(nbt);
		readSignText(nbt);
	}

	public void readSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			signText[i] = nbt.getString("Text" + (i + 1));
			if (signText[i].length() <= 15) {
				continue;
			}
			signText[i] = signText[i].substring(0, 15);
		}
	}

	public void setEditable(boolean flag) {
		editable = flag;
		if (!flag) {
			editingPlayer = null;
		}
	}

	public void setEditingPlayer(EntityPlayer entityplayer) {
		editingPlayer = entityplayer;
	}

	public void writeSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			nbt.setString("Text" + (i + 1), signText[i]);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeSignText(nbt);
	}
}
