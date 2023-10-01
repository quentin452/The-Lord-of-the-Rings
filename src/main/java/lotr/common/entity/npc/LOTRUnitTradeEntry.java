package lotr.common.entity.npc;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTRUnitTradeEntry {
	public Class entityClass;
	public Class mountClass;
	public Item mountArmor;
	public float mountArmorChance;
	public String name;
	public int initialCost;
	public float alignmentRequired;
	public PledgeType pledgeType = PledgeType.NONE;
	public LOTRHiredNPCInfo.Task task = LOTRHiredNPCInfo.Task.WARRIOR;
	public String extraInfo;

	public LOTRUnitTradeEntry(Class c, Class c1, String s, int cost, float alignment) {
		this(c, cost, alignment);
		mountClass = c1;
		name = s;
	}

	public LOTRUnitTradeEntry(Class c, int cost, float alignment) {
		entityClass = c;
		initialCost = cost;
		alignmentRequired = alignment;
		if (LOTRBannerBearer.class.isAssignableFrom(entityClass)) {
			setExtraInfo("Banner");
		}
	}

	public EntityLiving createHiredMount(World world) {
		if (mountClass == null) {
			return null;
		}
		EntityLiving entity = (EntityLiving) EntityList.createEntityByName(LOTREntities.getStringFromClass(mountClass), world);
		if (entity instanceof LOTREntityNPC) {
			((LOTREntityNPC) entity).initCreatureForHire(null);
			((LOTREntityNPC) entity).refreshCurrentAttackMode();
		} else {
			entity.onSpawnWithEgg(null);
		}
		if (mountArmor != null && world.rand.nextFloat() < mountArmorChance) {
			if (entity instanceof LOTREntityHorse) {
				((LOTREntityHorse) entity).setMountArmor(new ItemStack(mountArmor));
			} else if (entity instanceof LOTREntityWarg) {
				((LOTREntityWarg) entity).setWargArmor(new ItemStack(mountArmor));
			}
		}
		return entity;
	}

	public int getCost(EntityPlayer entityplayer, LOTRHireableBase trader) {
		float f;
		float cost = initialCost;
		LOTRFaction fac = trader.getFaction();
		LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
		float alignment = pd.getAlignment(fac);
		boolean pledged = pd.isPledgedTo(fac);
		float alignSurplus = Math.max(alignment - alignmentRequired, 0.0f);
		if (pledged) {
			f = alignSurplus / 1500.0f;
		} else {
			cost *= 2.0f;
			f = alignSurplus / 2000.0f;
		}
		f = MathHelper.clamp_float(f, 0.0f, 1.0f);
		cost *= 1.0f - f * 0.5f;
		int costI = Math.round(cost);
		return Math.max(costI, 1);
	}

	public String getFormattedExtraInfo() {
		return StatCollector.translateToLocal("lotr.unitinfo." + extraInfo);
	}

	public LOTREntityNPC getOrCreateHiredNPC(World world) {
		LOTREntityNPC entity = (LOTREntityNPC) EntityList.createEntityByName(LOTREntities.getStringFromClass(entityClass), world);
		entity.initCreatureForHire(null);
		entity.refreshCurrentAttackMode();
		return entity;
	}

	public PledgeType getPledgeType() {
		return pledgeType;
	}

	public LOTRUnitTradeEntry setPledgeType(PledgeType t) {
		pledgeType = t;
		return this;
	}

	public String getUnitTradeName() {
		if (mountClass == null) {
			String entityName = LOTREntities.getStringFromClass(entityClass);
			return StatCollector.translateToLocal("entity." + entityName + ".name");
		}
		return StatCollector.translateToLocal("lotr.unit." + name);
	}

	public boolean hasExtraInfo() {
		return extraInfo != null;
	}

	public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, LOTRHireableBase trader) {
		int coins = LOTRItemCoin.getInventoryValue(entityplayer, false);
		if (coins < getCost(entityplayer, trader)) {
			return false;
		}
		LOTRFaction fac = trader.getFaction();
		if (!pledgeType.canAcceptPlayer(entityplayer, fac)) {
			return false;
		}
		float alignment = LOTRLevelData.getData(entityplayer).getAlignment(fac);
		return alignment >= alignmentRequired;
	}

	public void hireUnit(EntityPlayer entityplayer, LOTRHireableBase trader, String squadron) {
		if (hasRequiredCostAndAlignment(entityplayer, trader)) {
			trader.onUnitTrade(entityplayer);
			int cost = getCost(entityplayer, trader);
			LOTRItemCoin.takeCoins(cost, entityplayer);
			((LOTREntityNPC) trader).playTradeSound();
			World world = entityplayer.worldObj;
			LOTREntityNPC hiredNPC = getOrCreateHiredNPC(world);
			if (hiredNPC != null) {
				boolean unitExists;
				EntityLiving mount = null;
				if (mountClass != null) {
					mount = createHiredMount(world);
				}
				hiredNPC.hiredNPCInfo.hireUnit(entityplayer, !(unitExists = world.loadedEntityList.contains(hiredNPC)), trader.getFaction(), this, squadron, mount);
				if (!unitExists) {
					world.spawnEntityInWorld(hiredNPC);
					if (mount != null) {
						world.spawnEntityInWorld(mount);
					}
				}
			}
		}
	}

	public LOTRUnitTradeEntry setExtraInfo(String s) {
		extraInfo = s;
		return this;
	}

	public LOTRUnitTradeEntry setMountArmor(Item item) {
		return setMountArmor(item, 1.0f);
	}

	public LOTRUnitTradeEntry setMountArmor(Item item, float chance) {
		mountArmor = item;
		mountArmorChance = chance;
		return this;
	}

	public LOTRUnitTradeEntry setPledgeExclusive() {
		return setPledgeType(PledgeType.FACTION);
	}

	public LOTRUnitTradeEntry setTask(LOTRHiredNPCInfo.Task t) {
		task = t;
		return this;
	}

	public enum PledgeType {
		NONE(0), FACTION(1), ANY_ELF(2), ANY_DWARF(3);

		public int typeID;

		PledgeType(int i) {
			typeID = i;
		}

		public static PledgeType forID(int i) {
			for (PledgeType t : values()) {
				if (t.typeID != i) {
					continue;
				}
				return t;
			}
			return NONE;
		}

		public boolean canAcceptPlayer(EntityPlayer entityplayer, LOTRFaction fac) {
			LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
			LOTRFaction pledged = pd.getPledgeFaction();
			if (this == NONE) {
				return true;
			}
			if (this == FACTION) {
				return pd.isPledgedTo(fac);
			}
			if (this == ANY_ELF) {
				return pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_ELF) && !pledged.isOfType(LOTRFaction.FactionType.TYPE_MAN);
			}
			if (this == ANY_DWARF) {
				return pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_DWARF);
			}
			return false;
		}

		public String getCommandReqText(LOTRFaction fac) {
			if (this == NONE) {
				return null;
			}
			return StatCollector.translateToLocalFormatted("lotr.hiredNPC.commandReq.pledge." + name(), fac.factionName());
		}
	}

}
