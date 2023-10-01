package lotr.client.render.entity;

import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTickHandlerClient;
import lotr.client.fx.LOTREntityAlignmentBonus;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class LOTRRenderAlignmentBonus extends Render {
	public LOTRRenderAlignmentBonus() {
		shadowSize = 0.0f;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		EntityClientPlayerMP entityplayer = Minecraft.getMinecraft().thePlayer;
		LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
		LOTRFaction viewingFaction = playerData.getViewingFaction();
		LOTREntityAlignmentBonus bonusParticle = (LOTREntityAlignmentBonus) entity;
		LOTRFaction mainFaction = bonusParticle.mainFaction;
		LOTRAlignmentBonusMap factionBonusMap = bonusParticle.factionBonusMap;
		LOTRFaction renderFaction = null;
		boolean showConquest = false;
		if (bonusParticle.conquestBonus > 0.0f && playerData.isPledgedTo(viewingFaction)) {
			renderFaction = viewingFaction;
			showConquest = true;
		} else if (bonusParticle.conquestBonus < 0.0f && (viewingFaction == mainFaction || playerData.isPledgedTo(viewingFaction))) {
			renderFaction = viewingFaction;
			showConquest = true;
		} else if (!factionBonusMap.isEmpty()) {
			if (factionBonusMap.containsKey(viewingFaction)) {
				renderFaction = viewingFaction;
			} else if (factionBonusMap.size() == 1 && mainFaction.isPlayableAlignmentFaction()) {
				renderFaction = mainFaction;
			} else if (mainFaction.isPlayableAlignmentFaction() && bonusParticle.prevMainAlignment >= 0.0f && factionBonusMap.get(mainFaction) < 0.0f) {
				renderFaction = mainFaction;
			} else {
				float alignment;
				for (Map.Entry<LOTRFaction, Float> entry : factionBonusMap.entrySet()) {
					LOTRFaction faction = entry.getKey();
					if (!faction.isPlayableAlignmentFaction() || entry.getValue() <= 0.0f) {
						continue;
					}
					alignment = playerData.getAlignment(faction);
					if (renderFaction != null && alignment <= playerData.getAlignment(renderFaction)) {
						continue;
					}
					renderFaction = faction;
				}
				if (renderFaction == null) {
					if (mainFaction.isPlayableAlignmentFaction() && factionBonusMap.get(mainFaction) < 0.0f) {
						renderFaction = mainFaction;
					} else {
						for (Map.Entry<LOTRFaction, Float> entry : factionBonusMap.entrySet()) {
							LOTRFaction faction = entry.getKey();
							if (!faction.isPlayableAlignmentFaction() || entry.getValue() >= 0.0f) {
								continue;
							}
							alignment = playerData.getAlignment(faction);
							if (renderFaction != null && alignment <= playerData.getAlignment(renderFaction)) {
								continue;
							}
							renderFaction = faction;
						}
					}
				}
			}
		}
		if (renderFaction != null) {
			float alignBonus = factionBonusMap.getOrDefault(renderFaction, 0.0f);
			boolean showAlign = alignBonus != 0.0f;
			float conqBonus = bonusParticle.conquestBonus;
			if (showAlign || showConquest) {
				String title = bonusParticle.name;
				boolean isViewingFaction = renderFaction == viewingFaction;
				boolean showTitle = showAlign || !bonusParticle.isHiredKill;
				float particleHealth = (float) bonusParticle.particleAge / bonusParticle.particleMaxAge;
				float alpha = particleHealth < 0.75f ? 1.0f : (1.0f - particleHealth) / 0.25f;
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d, (float) d1, (float) d2);
				GL11.glNormal3f(0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
				GL11.glScalef(-0.025f, -0.025f, 0.025f);
				GL11.glDisable(2896);
				GL11.glDepthMask(false);
				GL11.glDisable(2929);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				renderBonusText(isViewingFaction, renderFaction, title, showTitle, alignBonus, showAlign, conqBonus, showConquest, alpha);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glDisable(3042);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glEnable(2896);
				GL11.glPopMatrix();
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return LOTRClientProxy.alignmentTexture;
	}

	public void renderBonusText(boolean isViewingFaction, LOTRFaction renderFaction, String title, boolean showTitle, float align, boolean showAlign, float conq, boolean showConq, float alpha) {
		Minecraft mc = Minecraft.getMinecraft();
		FontRenderer fr = mc.fontRenderer;
		String strAlign = LOTRAlignmentValues.formatAlignForDisplay(align);
		String strConq = LOTRAlignmentValues.formatConqForDisplay(conq, true);
		boolean negativeConq = conq < 0.0f;
		GL11.glPushMatrix();
		if (!isViewingFaction) {
			float scale = 0.5f;
			GL11.glScalef(scale, scale, 1.0f);
			strAlign = strAlign + " (" + renderFaction.factionName() + "...)";
		}
		int x = -MathHelper.floor_double((fr.getStringWidth(strAlign) + 18) / 2.0);
		int y = -16;
		if (showAlign) {
			bindTexture(LOTRClientProxy.alignmentTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			LOTRTickHandlerClient.drawTexturedModalRect(x, y - 5, 0, 36, 16, 16);
			LOTRTickHandlerClient.drawAlignmentText(fr, x + 18, y, strAlign, alpha);
			y += 14;
		}
		if (showTitle) {
			x = -MathHelper.floor_double(fr.getStringWidth(title) / 2.0);
			if (showAlign) {
				LOTRTickHandlerClient.drawAlignmentText(fr, x, y, title, alpha);
			} else {
				LOTRTickHandlerClient.drawConquestText(fr, x, y, title, negativeConq, alpha);
			}
			y += 16;
		}
		if (showConq) {
			x = -MathHelper.floor_double((fr.getStringWidth(strConq) + 18) / 2.0);
			bindTexture(LOTRClientProxy.alignmentTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
			LOTRTickHandlerClient.drawTexturedModalRect(x, y - 5, negativeConq ? 16 : 0, 228, 16, 16);
			LOTRTickHandlerClient.drawConquestText(fr, x + 18, y, strConq, negativeConq, alpha);
		}
		GL11.glPopMatrix();
	}
}
