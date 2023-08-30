package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityBreeFarmhand extends LOTREntityBreeMan implements LOTRFarmhand {
	public Item seedsItem;

	public LOTREntityBreeFarmhand(World world) {
		super(world);
		targetTasks.taskEntries.clear();
		addTargetTasks(false);
	}

	@Override
	public void addBreeHiringAI(int prio) {
		super.addBreeHiringAI(prio);
		tasks.addTask(prio, new LOTREntityAIFarm(this, 1.0, 1.0f));
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
			return "bree/farmhand/hired";
		}
		return super.getSpeechBank(entityplayer);
	}

	@Override
	public IPlantable getUnhiredSeeds() {
		if (seedsItem == null) {
			return (IPlantable) Items.wheat_seeds;
		}
		return (IPlantable) seedsItem;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		Item item;
		super.readEntityFromNBT(nbt);
		if (nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
			seedsItem = item;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if (seedsItem != null) {
			nbt.setInteger("SeedsID", Item.getIdFromItem(seedsItem));
		}
	}
}
