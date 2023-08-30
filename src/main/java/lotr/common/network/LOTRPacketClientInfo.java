package lotr.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.LOTRDimension.DimensionRegion;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketClientInfo implements IMessage {
	public LOTRFaction viewingFaction;
	public Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap;
	public boolean showWP;
	public boolean showCWP;
	public boolean showHiddenSWP;

	public LOTRPacketClientInfo() {
	}

	public LOTRPacketClientInfo(LOTRFaction f, Map<LOTRDimension.DimensionRegion, LOTRFaction> crMap, boolean w, boolean cw, boolean h) {
		viewingFaction = f;
		changedRegionMap = crMap;
		showWP = w;
		showCWP = cw;
		showHiddenSWP = h;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int changedRegionsSize;
		byte factionID = data.readByte();
		if (factionID >= 0) {
			viewingFaction = LOTRFaction.forID(factionID);
		}
		changedRegionsSize = data.readByte();
		if (changedRegionsSize > 0) {
			changedRegionMap = new EnumMap<>(LOTRDimension.DimensionRegion.class);
			for (int l = 0; l < changedRegionsSize; ++l) {
				LOTRDimension.DimensionRegion reg = LOTRDimension.DimensionRegion.forID(data.readByte());
				LOTRFaction fac = LOTRFaction.forID(data.readByte());
				changedRegionMap.put(reg, fac);
			}
		}
		showWP = data.readBoolean();
		showCWP = data.readBoolean();
		showHiddenSWP = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (viewingFaction == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(viewingFaction.ordinal());
		}
		int changedRegionsSize = changedRegionMap != null ? changedRegionMap.size() : 0;
		data.writeByte(changedRegionsSize);
		if (changedRegionsSize > 0) {
			for (Map.Entry<LOTRDimension.DimensionRegion, LOTRFaction> e : changedRegionMap.entrySet()) {
				LOTRDimension.DimensionRegion reg = e.getKey();
				LOTRFaction fac = e.getValue();
				data.writeByte(reg.ordinal());
				data.writeByte(fac.ordinal());
			}
		}
		data.writeBoolean(showWP);
		data.writeBoolean(showCWP);
		data.writeBoolean(showHiddenSWP);
	}

	public static class Handler implements IMessageHandler<LOTRPacketClientInfo, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketClientInfo packet, MessageContext context) {
			Map<DimensionRegion, LOTRFaction> changedRegionMap;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			if (packet.viewingFaction != null) {
				LOTRFaction prevFac = pd.getViewingFaction();
				LOTRFaction newFac = packet.viewingFaction;
				pd.setViewingFaction(newFac);
				if (prevFac != newFac && prevFac.factionRegion == newFac.factionRegion) {
					pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignment());
				}
				if (prevFac.factionRegion != newFac.factionRegion) {
					pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignmentRegion());
				}
			}
			changedRegionMap = packet.changedRegionMap;
			if (changedRegionMap != null) {
				for (Map.Entry<DimensionRegion, LOTRFaction> entry : changedRegionMap.entrySet()) {
					LOTRFaction fac = entry.getValue();
					pd.setRegionLastViewedFaction(entry.getKey(), fac);
				}
			}
			pd.setShowWaypoints(packet.showWP);
			pd.setShowCustomWaypoints(packet.showCWP);
			pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
			return null;
		}
	}

}
