package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityMan extends LOTREntityNPC {
	protected LOTREntityMan(World world) {
		super(world);
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && LOTRMod.canDropLoot(worldObj) && rand.nextInt(5) == 0) {
			List<LOTRFaction> manFleshFactions = LOTRItemManFlesh.getManFleshFactions();
			Entity damager = damagesource.getSourceOfDamage();
			if (damager instanceof EntityLivingBase) {
				ItemStack itemstack;
				EntityLivingBase entity = (EntityLivingBase) damager;
				boolean isAligned = false;
				if (entity instanceof EntityPlayer) {
					LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer) entity);
					for (LOTRFaction f : manFleshFactions) {
						if (playerData.getAlignment(f) <= 0.0f) {
							continue;
						}
						isAligned = true;
					}
				} else {
					LOTRFaction npcFaction = LOTRMod.getNPCFaction(entity);
					isAligned = manFleshFactions.contains(npcFaction);
				}
				if (isAligned && (itemstack = entity.getHeldItem()) != null) {
					Item item = itemstack.getItem();
					Item.ToolMaterial material = null;
					if (item instanceof ItemSword) {
						ItemSword sword = (ItemSword) item;
						material = LOTRMaterial.getToolMaterialByName(sword.getToolMaterialName());
					} else if (item instanceof ItemTool) {
						ItemTool tool = (ItemTool) item;
						material = tool.func_150913_i();
					} else if (item instanceof LOTRItemThrowingAxe) {
						LOTRItemThrowingAxe axe = (LOTRItemThrowingAxe) item;
						material = axe.getAxeMaterial();
					}
					if (material != null) {
						boolean canHarvest = false;
						for (LOTRMaterial lotrMaterial : LOTRMaterial.allLOTRMaterials) {
							if (lotrMaterial.toToolMaterial() != material || !lotrMaterial.canHarvestManFlesh()) {
								continue;
							}
							canHarvest = true;
							break;
						}
						if (canHarvest) {
							ItemStack flesh = new ItemStack(LOTRMod.manFlesh, 1 + rand.nextInt(2));
							entityDropItem(flesh, 0.0f);
						}
					}
				}
			}
		}
	}
}
