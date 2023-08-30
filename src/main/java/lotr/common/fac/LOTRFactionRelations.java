package lotr.common.fac;

import java.io.File;
import java.util.*;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.LOTRLevelData;
import lotr.common.network.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

public class LOTRFactionRelations {
	public static Map<FactionPair, Relation> defaultMap = new HashMap<>();
	public static Map<FactionPair, Relation> overrideMap = new HashMap<>();
	public static boolean needsLoad = true;
	public static boolean needsSave;

	public static Relation getFromDefaultMap(FactionPair key) {
		if (defaultMap.containsKey(key)) {
			return defaultMap.get(key);
		}
		return Relation.NEUTRAL;
	}

	public static Relation getRelations(LOTRFaction f1, LOTRFaction f2) {
		if (f1 == LOTRFaction.UNALIGNED || f2 == LOTRFaction.UNALIGNED) {
			return Relation.NEUTRAL;
		}
		if (f1 == LOTRFaction.HOSTILE || f2 == LOTRFaction.HOSTILE) {
			return Relation.MORTAL_ENEMY;
		}
		if (f1 == f2) {
			return Relation.ALLY;
		}
		FactionPair key = new FactionPair(f1, f2);
		if (overrideMap.containsKey(key)) {
			return overrideMap.get(key);
		}
		return getFromDefaultMap(key);
	}

	public static File getRelationsFile() {
		return new File(LOTRLevelData.getOrCreateLOTRDir(), "faction_relations.dat");
	}

	public static void load() {
		try {
			NBTTagCompound facData = LOTRLevelData.loadNBTFromFile(getRelationsFile());
			overrideMap.clear();
			NBTTagList relationTags = facData.getTagList("Overrides", 10);
			for (int i = 0; i < relationTags.tagCount(); ++i) {
				NBTTagCompound nbt = relationTags.getCompoundTagAt(i);
				FactionPair pair = FactionPair.readFromNBT(nbt);
				Relation rel = Relation.forName(nbt.getString("Rel"));
				if (pair == null || rel == null) {
					continue;
				}
				overrideMap.put(pair, rel);
			}
			needsLoad = false;
			save();
		} catch (Exception e) {
			FMLLog.severe("Error loading LOTR faction relations");
			e.printStackTrace();
		}
	}

	public static void markDirty() {
		needsSave = true;
	}

	public static boolean needsSave() {
		return needsSave;
	}

	public static void overrideRelations(LOTRFaction f1, LOTRFaction f2, Relation relation) {
		setRelations(f1, f2, relation, false);
	}

	public static void resetAllRelations() {
		boolean wasEmpty = overrideMap.isEmpty();
		overrideMap.clear();
		if (!wasEmpty) {
			markDirty();
			LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.reset();
			sendPacketToAll(pkt);
		}
	}

	public static void save() {
		try {
			File datFile = getRelationsFile();
			if (!datFile.exists()) {
				LOTRLevelData.saveNBTToFile(datFile, new NBTTagCompound());
			}
			NBTTagCompound facData = new NBTTagCompound();
			NBTTagList relationTags = new NBTTagList();
			for (Map.Entry<FactionPair, Relation> e : overrideMap.entrySet()) {
				FactionPair pair = e.getKey();
				Relation rel = e.getValue();
				NBTTagCompound nbt = new NBTTagCompound();
				pair.writeToNBT(nbt);
				nbt.setString("Rel", rel.codeName());
				relationTags.appendTag(nbt);
			}
			facData.setTag("Overrides", relationTags);
			LOTRLevelData.saveNBTToFile(datFile, facData);
			needsSave = false;
		} catch (Exception e) {
			FMLLog.severe("Error saving LOTR faction relations");
			e.printStackTrace();
		}
	}

	public static void sendAllRelationsTo(EntityPlayerMP entityplayer) {
		LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.fullMap(overrideMap);
		LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
	}

	public static void sendPacketToAll(IMessage packet) {
		MinecraftServer srv = MinecraftServer.getServer();
		if (srv != null) {
			for (Object obj : srv.getConfigurationManager().playerEntityList) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
				LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
			}
		}
	}

	public static void setDefaultRelations(LOTRFaction f1, LOTRFaction f2, Relation relation) {
		setRelations(f1, f2, relation, true);
	}

	public static void setRelations(LOTRFaction f1, LOTRFaction f2, Relation relation, boolean isDefault) {
		if (f1 == LOTRFaction.UNALIGNED || f2 == LOTRFaction.UNALIGNED) {
			throw new IllegalArgumentException("Cannot alter UNALIGNED!");
		}
		if (f1 == LOTRFaction.HOSTILE || f2 == LOTRFaction.HOSTILE) {
			throw new IllegalArgumentException("Cannot alter HOSTILE!");
		}
		if (f1 == f2) {
			throw new IllegalArgumentException("Cannot alter a faction's relations with itself!");
		}
		FactionPair key = new FactionPair(f1, f2);
		if (isDefault) {
			if (relation == Relation.NEUTRAL) {
				defaultMap.remove(key);
			} else {
				defaultMap.put(key, relation);
			}
		} else {
			Relation defaultRelation = getFromDefaultMap(key);
			if (relation == defaultRelation) {
				overrideMap.remove(key);
			} else {
				overrideMap.put(key, relation);
			}
			markDirty();
			LOTRPacketFactionRelations pkt = LOTRPacketFactionRelations.oneEntry(key, relation);
			sendPacketToAll(pkt);
		}
	}

	public static class FactionPair {
		public LOTRFaction fac1;
		public LOTRFaction fac2;

		public FactionPair(LOTRFaction f1, LOTRFaction f2) {
			fac1 = f1;
			fac2 = f2;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof FactionPair) {
				FactionPair pair = (FactionPair) obj;
				return fac1 == pair.fac1 && fac2 == pair.fac2 || fac1 == pair.fac2 && fac2 == pair.fac1;
			}
			return false;
		}

		public LOTRFaction getLeft() {
			return fac1;
		}

		public LOTRFaction getRight() {
			return fac2;
		}

		@Override
		public int hashCode() {
			int f1 = fac1.ordinal();
			int f2 = fac2.ordinal();
			int lower = Math.min(f1, f2);
			int upper = Math.max(f1, f2);
			return upper << 16 | lower;
		}

		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setString("FacPair1", fac1.codeName());
			nbt.setString("FacPair2", fac2.codeName());
		}

		public static FactionPair readFromNBT(NBTTagCompound nbt) {
			LOTRFaction f1 = LOTRFaction.forName(nbt.getString("FacPair1"));
			LOTRFaction f2 = LOTRFaction.forName(nbt.getString("FacPair2"));
			if (f1 != null && f2 != null) {
				return new FactionPair(f1, f2);
			}
			return null;
		}
	}

	public enum Relation {
		ALLY, FRIEND, NEUTRAL, ENEMY, MORTAL_ENEMY;

		public String codeName() {
			return name();
		}

		public String getDisplayName() {
			return StatCollector.translateToLocal("lotr.faction.rel." + codeName());
		}

		public static Relation forID(int id) {
			for (Relation rel : values()) {
				if (rel.ordinal() != id) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static Relation forName(String name) {
			for (Relation rel : values()) {
				if (!rel.codeName().equals(name)) {
					continue;
				}
				return rel;
			}
			return null;
		}

		public static List<String> listRelationNames() {
			List<String> names = new ArrayList<>();
			for (Relation rel : values()) {
				names.add(rel.codeName());
			}
			return names;
		}
	}

}
