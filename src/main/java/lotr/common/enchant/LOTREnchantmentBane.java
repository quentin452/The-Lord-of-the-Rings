package lotr.common.enchant;

import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LOTREnchantmentBane extends LOTREnchantmentDamage {
	public List<Class<? extends EntityLivingBase>> entityClasses;
	public EnumCreatureAttribute entityAttribute;
	public float baneDamage;
	public boolean isAchievable = true;

	public LOTREnchantmentBane(String s, float boost) {
		super(s, 0.0f);
		baneDamage = boost;
		setValueModifier((10.0f + baneDamage) / 10.0f);
		setPersistsReforge();
		setBypassAnvilLimit();
	}

	public LOTREnchantmentBane(String s, float boost, Class<? extends EntityLivingBase>... classes) {
		this(s, boost);
		entityClasses = Arrays.asList(classes);
	}

	public LOTREnchantmentBane(String s, float boost, EnumCreatureAttribute attr) {
		this(s, boost);
		entityAttribute = attr;
	}

	public boolean doesEntityKillCountTowardsBane(EntityLivingBase entity, World world) {
		if (!LOTRConfig.hiredUnitKillsCountForBane && entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).hiredNPCInfo.isActive || world.provider instanceof LOTRWorldProviderUtumno) {
			return false;
		}
		return isEntityType(entity);
	}

	@Override
	public float getBaseDamageBoost() {
		return 0.0f;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("lotr.enchant." + enchantName + ".desc", formatAdditive(baneDamage));
	}

	@Override
	public float getEntitySpecificDamage(EntityLivingBase entity) {
		if (isEntityType(entity)) {
			return baneDamage;
		}
		return 0.0f;
	}

	public int getRandomKillsRequired(Random random) {
		return MathHelper.getRandomIntegerInRange(random, 100, 250);
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	public boolean isEntityType(EntityLivingBase entity) {
		if (entityClasses != null) {
			for (Class<? extends EntityLivingBase> cls : entityClasses) {
				if (!cls.isAssignableFrom(entity.getClass())) {
					continue;
				}
				return true;
			}
		} else if (entityAttribute != null) {
			return entity.getCreatureAttribute() == entityAttribute;
		}
		return false;
	}

	public LOTREnchantmentBane setUnachievable() {
		isAchievable = false;
		return this;
	}
}
