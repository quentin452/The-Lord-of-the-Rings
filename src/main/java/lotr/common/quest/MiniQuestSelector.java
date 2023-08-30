package lotr.common.quest;

import java.util.UUID;

import com.google.common.base.Supplier;

import lotr.common.fac.LOTRFaction;

public interface MiniQuestSelector {
	boolean include(LOTRMiniQuest var1);

	class BountyActiveAnyFaction extends OptionalActive {
		public BountyActiveAnyFaction() {
			setActiveOnly();
		}

		@Override
		public boolean include(LOTRMiniQuest quest) {
			if (super.include(quest) && quest instanceof LOTRMiniQuestBounty) {
				LOTRMiniQuestBounty bQuest = (LOTRMiniQuestBounty) quest;
				return !bQuest.killed;
			}
			return false;
		}
	}

	class BountyActiveFaction extends BountyActiveAnyFaction {
		public Supplier<LOTRFaction> factionGet;

		public BountyActiveFaction(Supplier<LOTRFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(LOTRMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	class EntityId extends OptionalActive {
		public UUID entityID;

		public EntityId(UUID id) {
			entityID = id;
		}

		@Override
		public boolean include(LOTRMiniQuest quest) {
			return super.include(quest) && quest.entityUUID.equals(entityID);
		}
	}

	class Faction extends OptionalActive {
		public Supplier<LOTRFaction> factionGet;

		public Faction(Supplier<LOTRFaction> sup) {
			factionGet = sup;
		}

		@Override
		public boolean include(LOTRMiniQuest quest) {
			return super.include(quest) && quest.entityFaction == factionGet.get();
		}
	}

	class OptionalActive implements MiniQuestSelector {
		public boolean activeOnly;

		@Override
		public boolean include(LOTRMiniQuest quest) {
			if (activeOnly) {
				return quest.isActive();
			}
			return true;
		}

		public OptionalActive setActiveOnly() {
			activeOnly = true;
			return this;
		}
	}

}
