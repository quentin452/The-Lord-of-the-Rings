package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFarm;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityHobbitFarmhand extends LOTREntityHobbit implements LOTRFarmhand {
	public Item seedsItem;

	public LOTREntityHobbitFarmhand(World world) {
		super(world);
		tasks.addTask(4, new LOTREntityAIAttackOnCollide(this, 1.3, false));
		tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
		tasks.addTask(6, new LOTREntityAIFarm(this, 1.0, 1.5f));
		targetTasks.taskEntries.clear();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
			return "hobbit/farmhand/hired";
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
