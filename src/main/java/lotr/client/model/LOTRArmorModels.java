package lotr.client.model;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemSling;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class LOTRArmorModels {
	public static LOTRArmorModels INSTANCE;
	public Map<ModelBiped, Map<Item, ModelBiped>> specialArmorModels = new HashMap<>();

	public LOTRArmorModels() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void setupArmorModels() {
		INSTANCE = new LOTRArmorModels();
	}

	public void copyBoxRotations(ModelRenderer target, ModelRenderer src) {
		target.rotationPointX = src.rotationPointX;
		target.rotationPointY = src.rotationPointY;
		target.rotationPointZ = src.rotationPointZ;
		target.rotateAngleX = src.rotateAngleX;
		target.rotateAngleY = src.rotateAngleY;
		target.rotateAngleZ = src.rotateAngleZ;
	}

	public void copyModelRotations(ModelBiped target, ModelBiped src) {
		copyBoxRotations(target.bipedHead, src.bipedHead);
		copyBoxRotations(target.bipedHeadwear, src.bipedHeadwear);
		copyBoxRotations(target.bipedBody, src.bipedBody);
		copyBoxRotations(target.bipedRightArm, src.bipedRightArm);
		copyBoxRotations(target.bipedLeftArm, src.bipedLeftArm);
		copyBoxRotations(target.bipedRightLeg, src.bipedRightLeg);
		copyBoxRotations(target.bipedLeftLeg, src.bipedLeftLeg);
	}

	public int getEntityArmorModel(RendererLivingEntity renderer, ModelBiped mainModel, EntityLivingBase entity, ItemStack armor, int slot) {
		ModelBiped armorModel = getSpecialArmorModel(armor, slot, entity, mainModel);
		if (armorModel != null) {
			int color;
			Item armorItem;
			armorItem = armor == null ? null : armor.getItem();
			if (armorItem instanceof ItemArmor) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderBiped.getArmorResource(entity, armor, slot, null));
			}
			renderer.setRenderPassModel(armorModel);
			setupModelForRender(armorModel, mainModel, entity);
			if (armorItem instanceof ItemArmor && (color = ((ItemArmor) armorItem).getColor(armor)) != -1) {
				float r = (color >> 16 & 0xFF) / 255.0f;
				float g = (color >> 8 & 0xFF) / 255.0f;
				float b = (color & 0xFF) / 255.0f;
				GL11.glColor3f(r, g, b);
				if (armor.isItemEnchanted()) {
					return 31;
				}
				return 16;
			}
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			if (armor.isItemEnchanted()) {
				return 15;
			}
			return 1;
		}
		return 0;
	}

	@SubscribeEvent
	public void getPlayerArmorModel(RenderPlayerEvent.SetArmorModel event) {
		RenderPlayer renderer = event.renderer;
		ModelBiped mainModel = renderer.modelBipedMain;
		EntityPlayer entityplayer = event.entityPlayer;
		ItemStack armor = event.stack;
		int slot = event.slot;
		int result = getEntityArmorModel(renderer, mainModel, entityplayer, armor, 3 - slot);
		if (result > 0) {
			event.result = result;
		}
	}

	public ModelBiped getSpecialArmorModel(ItemStack itemstack, int slot, EntityLivingBase entity, ModelBiped mainModel) {
		if (itemstack == null) {
			return null;
		}
		Item item = itemstack.getItem();
		ModelBiped model = getSpecialModels(mainModel).get(item);
		if (model == null) {
			return null;
		}
		if (model instanceof LOTRModelLeatherHat) {
			((LOTRModelLeatherHat) model).setHatItem(itemstack);
		}
		if (model instanceof LOTRModelHaradRobes) {
			((LOTRModelHaradRobes) model).setRobeItem(itemstack);
		}
		if (model instanceof LOTRModelPartyHat) {
			((LOTRModelPartyHat) model).setHatItem(itemstack);
		}
		if (model instanceof LOTRModelHeadPlate) {
			((LOTRModelHeadPlate) model).setPlateItem(itemstack);
		}
		copyModelRotations(model, mainModel);
		setupArmorForSlot(model, slot);
		return model;
	}

	public Map<Item, ModelBiped> getSpecialModels(ModelBiped key) {
		Map<Item, ModelBiped> map = specialArmorModels.get(key);
		if (map == null) {
			map = new HashMap<>();
			map.put(LOTRMod.leatherHat, new LOTRModelLeatherHat());
			map.put(LOTRMod.helmetGondor, new LOTRModelGondorHelmet(1.0f));
			map.put(LOTRMod.helmetElven, new LOTRModelGaladhrimHelmet(1.0f));
			map.put(LOTRMod.helmetGondorWinged, new LOTRModelWingedHelmet(1.0f));
			map.put(LOTRMod.helmetMorgul, new LOTRModelMorgulHelmet(1.0f));
			map.put(LOTRMod.helmetGemsbok, new LOTRModelGemsbokHelmet(1.0f));
			map.put(LOTRMod.helmetHighElven, new LOTRModelHighElvenHelmet(1.0f));
			map.put(LOTRMod.helmetBlackUruk, new LOTRModelBlackUrukHelmet(1.0f));
			map.put(LOTRMod.helmetUruk, new LOTRModelUrukHelmet(1.0f));
			map.put(LOTRMod.helmetNearHaradWarlord, new LOTRModelNearHaradWarlordHelmet(1.0f));
			map.put(LOTRMod.helmetDolAmroth, new LOTRModelSwanHelmet(1.0f));
			map.put(LOTRMod.bodyDolAmroth, new LOTRModelSwanChestplate(1.0f));
			map.put(LOTRMod.helmetMoredainLion, new LOTRModelMoredainLionHelmet(1.0f));
			map.put(LOTRMod.helmetHaradRobes, new LOTRModelHaradTurban());
			map.put(LOTRMod.bodyHaradRobes, new LOTRModelHaradRobes(1.0f));
			map.put(LOTRMod.legsHaradRobes, new LOTRModelHaradRobes(0.5f));
			map.put(LOTRMod.bootsHaradRobes, new LOTRModelHaradRobes(1.0f));
			map.put(LOTRMod.helmetGondolin, new LOTRModelGondolinHelmet(1.0f));
			map.put(LOTRMod.helmetRohanMarshal, new LOTRModelRohanMarshalHelmet(1.0f));
			map.put(LOTRMod.helmetTauredainChieftain, new LOTRModelTauredainChieftainHelmet(1.0f));
			map.put(LOTRMod.helmetTauredainGold, new LOTRModelTauredainGoldHelmet(1.0f));
			map.put(LOTRMod.helmetGundabadUruk, new LOTRModelGundabadUrukHelmet(1.0f));
			map.put(LOTRMod.helmetUrukBerserker, new LOTRModelUrukHelmet(1.0f));
			map.put(LOTRMod.helmetDorwinionElf, new LOTRModelDorwinionElfHelmet(1.0f));
			map.put(LOTRMod.partyHat, new LOTRModelPartyHat(0.6f));
			map.put(LOTRMod.helmetArnor, new LOTRModelArnorHelmet(1.0f));
			map.put(LOTRMod.helmetRhunGold, new LOTRModelEasterlingHelmet(1.0f, false));
			map.put(LOTRMod.helmetRhunWarlord, new LOTRModelEasterlingHelmet(1.0f, true));
			map.put(LOTRMod.helmetRivendell, new LOTRModelHighElvenHelmet(1.0f));
			map.put(LOTRMod.bodyGulfHarad, new LOTRModelGulfChestplate(1.0f));
			map.put(LOTRMod.helmetUmbar, new LOTRModelUmbarHelmet(1.0f));
			map.put(LOTRMod.helmetHarnedor, new LOTRModelHarnedorHelmet(1.0f));
			map.put(LOTRMod.bodyHarnedor, new LOTRModelHarnedorChestplate(1.0f));
			map.put(LOTRMod.helmetBlackNumenorean, new LOTRModelBlackNumenoreanHelmet(1.0f));
			map.put(LOTRMod.plate, new LOTRModelHeadPlate());
			map.put(LOTRMod.woodPlate, new LOTRModelHeadPlate());
			map.put(LOTRMod.ceramicPlate, new LOTRModelHeadPlate());
			for (ModelBiped armorModel : map.values()) {
				copyModelRotations(armorModel, key);
			}
			specialArmorModels.put(key, map);
		}
		return map;
	}

	@SubscribeEvent
	public void preRenderEntity(RenderLivingEvent.Pre event) {
		EntityLivingBase entity = event.entity;
		RendererLivingEntity renderer = event.renderer;
		if (entity instanceof EntityPlayer && renderer instanceof RenderPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			RenderPlayer renderplayer = (RenderPlayer) renderer;
			ModelBiped mainModel = renderplayer.modelBipedMain;
			ModelBiped armorModel1 = renderplayer.modelArmorChestplate;
			ModelBiped armorModel2 = renderplayer.modelArmor;
			setupModelForRender(mainModel, mainModel, entityplayer);
			setupModelForRender(armorModel1, mainModel, entityplayer);
			setupModelForRender(armorModel2, mainModel, entityplayer);
		}
	}

	public void setupArmorForSlot(ModelBiped model, int slot) {
		model.bipedHead.showModel = slot == 0;
		model.bipedHeadwear.showModel = slot == 0;
		model.bipedBody.showModel = slot == 1 || slot == 2;
		model.bipedRightArm.showModel = slot == 1;
		model.bipedLeftArm.showModel = slot == 1;
		model.bipedRightLeg.showModel = slot == 2 || slot == 3;
		model.bipedLeftLeg.showModel = slot == 2 || slot == 3;
	}

	public void setupHeldItem(ModelBiped model, EntityLivingBase entity, ItemStack itemstack, boolean rightArm) {
		int value = 0;
		boolean aimBow = false;
		if (itemstack != null) {
			value = 1;
			Item item = itemstack.getItem();
			boolean isRanged = false;
			if (itemstack.getItemUseAction() == EnumAction.bow) {
				isRanged = item instanceof LOTRItemSpear ? entity instanceof EntityPlayer : !(item instanceof ItemSword);
			}
			if (item instanceof LOTRItemSling) {
				isRanged = true;
			}
			if (isRanged) {
				boolean aiming = model.aimedBow;
				if (entity instanceof EntityPlayer && LOTRItemCrossbow.isLoaded(itemstack)) {
					aiming = true;
				}
				if (entity instanceof LOTREntityNPC) {
					aiming = ((LOTREntityNPC) entity).clientCombatStance;
				}
				if (aiming) {
					value = 3;
					aimBow = true;
				}
			}
			if (item instanceof LOTRItemBanner) {
				value = 3;
			}
			if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getItemInUseCount() > 0 && itemstack.getItemUseAction() == EnumAction.block) {
				value = 3;
			}
			if (entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).clientIsEating) {
				value = 3;
			}
		}
		if (rightArm) {
			model.heldItemRight = value;
			model.aimedBow = aimBow;
		} else {
			model.heldItemLeft = value;
		}
	}

	public void setupModelForRender(ModelBiped model, ModelBiped mainModel, EntityLivingBase entity) {
		if (mainModel != null) {
			model.onGround = mainModel.onGround;
			model.isRiding = mainModel.isRiding;
			model.isChild = mainModel.isChild;
			model.isSneak = mainModel.isSneak;
		} else {
			model.onGround = 0.0f;
			model.isRiding = false;
			model.isChild = false;
			model.isSneak = false;
		}
		if (entity instanceof LOTREntityNPC) {
			model.bipedHeadwear.showModel = ((LOTREntityNPC) entity).shouldRenderNPCHair();
		}
		if (entity instanceof EntityPlayer) {
			ItemStack heldRight = entity.getHeldItem();
			model.aimedBow = mainModel.aimedBow;
			setupHeldItem(model, entity, heldRight, true);
		} else {
			ItemStack heldRight;
			heldRight = entity == null ? null : entity.getHeldItem();
			ItemStack heldLeft = entity == null ? null : entity instanceof LOTREntityNPC ? ((LOTREntityNPC) entity).getHeldItemLeft() : null;
			setupHeldItem(model, entity, heldRight, true);
			setupHeldItem(model, entity, heldLeft, false);
		}
	}
}
