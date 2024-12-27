package lotr.common.coremod;

import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.block.*;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.util.LOTRCommonIcons;
import lotr.common.util.LOTRLog;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.*;

public class LOTRReplacedMethods {

	public static class Anvil {
        @Deprecated
        public static AxisAlignedBB getCollisionBoundingBoxFromPool(Block block, World world, int i, int j, int k) {
            block.setBlockBoundsBasedOnState(world, i, j, k);
            return AxisAlignedBB.getBoundingBox(i + block.getBlockBoundsMinX(), j + block.getBlockBoundsMinY(), k + block.getBlockBoundsMinZ(), i + block.getBlockBoundsMaxX(), j + block.getBlockBoundsMaxY(), k + block.getBlockBoundsMaxZ());
        }

        public static AxisAlignedBB getCollisionBoundingBoxFromPool(Block block, IBlockAccess world, int i, int j, int k) {
			block.setBlockBoundsBasedOnState(world, i, j, k);
			return AxisAlignedBB.getBoundingBox(i + block.getBlockBoundsMinX(), j + block.getBlockBoundsMinY(), k + block.getBlockBoundsMinZ(), i + block.getBlockBoundsMaxX(), j + block.getBlockBoundsMaxY(), k + block.getBlockBoundsMaxZ());
		}
	}

	public static class BlockRendering {
		public static Random blockRand = new Random();
		public static Map<Class<? extends Block>, boolean[]> naturalBlockClassTable = new HashMap<>();
		public static Map<BlockAndMeta, boolean[]> naturalBlockTable = new HashMap<>();
		public static Map<BlockAndMeta, boolean[]> cachedNaturalBlocks = new HashMap<>();

		public static void add(Block block, int meta) {
			add(block, -1, 0, 1, 2, 3, 4, 5);
		}

		public static void add(Block block, int meta, Integer... sides) {
			naturalBlockTable.put(new BlockAndMeta(block, meta), getSideFlagsFrom(sides));
		}

		public static void add(Class<? extends Block> cls) {
			add(cls, 0, 1, 2, 3, 4, 5);
		}

		public static void add(Class<? extends Block> cls, Integer... sides) {
			naturalBlockClassTable.put(cls, getSideFlagsFrom(sides));
		}

		public static boolean[] getSideFlagsFrom(Integer... sides) {
			List<Integer> sidesAsList = Arrays.asList(sides);
			boolean[] sideFlags = new boolean[6];
			for (int side = 0; side < sideFlags.length; ++side) {
				if (!sidesAsList.contains(side)) {
					continue;
				}
				sideFlags[side] = true;
			}
			return sideFlags;
		}

		public static boolean renderStandardBlock(RenderBlocks renderblocks, Block block, int i, int j, int k) {
			if (naturalBlockClassTable.isEmpty()) {
				setupNaturalBlockTable();
			}
			if (LOTRConfig.naturalBlocks) {
				int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
				BlockAndMeta bam = new BlockAndMeta(block, meta);
				boolean[] sideFlags = null;
				if (cachedNaturalBlocks.containsKey(bam)) {
					sideFlags = cachedNaturalBlocks.get(bam);
				} else if (naturalBlockTable.containsKey(bam)) {
					sideFlags = naturalBlockTable.get(bam);
					cachedNaturalBlocks.put(bam, sideFlags);
				} else {
					for (Map.Entry<Class<? extends Block>, boolean[]> entry : naturalBlockClassTable.entrySet()) {
						if (!entry.getKey().isAssignableFrom(block.getClass())) {
							continue;
						}
						sideFlags = entry.getValue();
						cachedNaturalBlocks.put(bam, sideFlags);
					}
				}
				if (sideFlags != null) {
					int[] randomSides = new int[6];
					for (int l = 0; l < randomSides.length; ++l) {
						int hash = i * 234890405 ^ k * 37383934 ^ j;
						blockRand.setSeed(hash + l * 285502);
						blockRand.setSeed(blockRand.nextLong());
						randomSides[l] = blockRand.nextInt(4);
					}
					if (sideFlags[0]) {
						renderblocks.uvRotateBottom = randomSides[0];
					}
					if (sideFlags[1]) {
						renderblocks.uvRotateTop = randomSides[1];
					}
					if (sideFlags[2]) {
						renderblocks.uvRotateNorth = randomSides[2];
					}
					if (sideFlags[3]) {
						renderblocks.uvRotateSouth = randomSides[3];
					}
					if (sideFlags[4]) {
						renderblocks.uvRotateWest = randomSides[4];
					}
					if (sideFlags[5]) {
						renderblocks.uvRotateEast = randomSides[5];
					}
				}
				boolean flag = renderblocks.renderStandardBlock(block, i, j, k);
				if (sideFlags != null) {
					renderblocks.uvRotateEast = 0;
					renderblocks.uvRotateWest = 0;
					renderblocks.uvRotateSouth = 0;
					renderblocks.uvRotateNorth = 0;
					renderblocks.uvRotateTop = 0;
					renderblocks.uvRotateBottom = 0;
				}
				return flag;
			}
			return renderblocks.renderStandardBlock(block, i, j, k);
		}

		public static void setupNaturalBlockTable() {
			add(BlockGrass.class, 1, 0);
			add(Blocks.dirt, 0);
			add(Blocks.dirt, 1);
			add(Blocks.dirt, 2, 1, 0);
			add(LOTRBlockSlabDirt.class);
			add(LOTRBlockMudGrass.class, 1, 0);
			add(LOTRBlockMud.class);
			add(BlockSand.class);
			add(LOTRBlockSand.class);
			add(LOTRBlockSlabSand.class);
			add(Blocks.sandstone, 0, 1, 0);
			add(Blocks.stone_slab, 1, 1, 0);
			add(Blocks.double_stone_slab, 1, 1, 0);
			add(Blocks.sandstone_stairs, 1, 0);
			add(LOTRMod.wallStoneV, 4, 1, 0);
			add(LOTRBlockSandstone.class, 1, 0);
			add(LOTRMod.slabSingle7, 5, 1, 0);
			add(LOTRMod.slabDouble7, 5, 1, 0);
			add(LOTRMod.stairsRedSandstone, 1, 0);
			add(LOTRMod.wallStoneV, 5, 1, 0);
			add(LOTRMod.slabSingle10, 6, 1, 0);
			add(LOTRMod.slabDouble10, 6, 1, 0);
			add(LOTRMod.stairsWhiteSandstone, 1, 0);
			add(LOTRMod.wall3, 14, 1, 0);
			add(LOTRMod.rock, 0, 1, 0);
			add(BlockGravel.class);
			add(LOTRBlockSlabGravel.class);
			add(BlockClay.class);
			add(BlockSnow.class);
			add(BlockSnowBlock.class);
			add(BlockMycelium.class, 1, 0);
			add(LOTRBlockMordorDirt.class);
			add(LOTRBlockDirtPath.class);
			add(LOTRBlockMordorMoss.class);
		}

		public static class BlockAndMeta {
			public Block block;
			public int meta;

			public BlockAndMeta(Block b, int m) {
				block = b;
				meta = m;
			}

			@Override
			public boolean equals(Object other) {
				if (other instanceof BlockAndMeta) {
					BlockAndMeta otherBM = (BlockAndMeta) other;
					return otherBM.block == block && otherBM.meta == meta;
				}
				return false;
			}

			@Override
			public int hashCode() {
				return (block.getUnlocalizedName() + "_" + meta).hashCode();
			}
		}

	}

	public static class Cauldron {
		public static int getRenderType() {
			if (LOTRMod.proxy == null) {
				return 24;
			}
			return LOTRMod.proxy.getVCauldronRenderID();
		}
	}

	public static class ClientPlayer {
		public static void horseJump(EntityClientPlayerMP entityplayer) {
			int jump = (int) (entityplayer.getHorseJumpPower() * 100.0f);
			Entity mount = entityplayer.ridingEntity;
			if (mount instanceof EntityHorse) {
				((EntityHorse) mount).setJumpPower(jump);
			}
			entityplayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(entityplayer, 6, jump));
		}
	}

	public static class Dirt {
		public static String nameIndex1 = "coarse";

		public static ItemStack createStackedBlock(Block thisBlock, int i) {
			Item item = Item.getItemFromBlock(thisBlock);
			return new ItemStack(item, 1, i);
		}

		public static int damageDropped(int i) {
			if (i == 1) {
				return 1;
			}
			return 0;
		}

        @Deprecated
        public static int getDamageValue(World world, int i, int j, int k) {
            return world.getBlockMetadata(i, j, k);
        }

		public static int getDamageValue(IBlockAccess world, int i, int j, int k) {
			return world.getBlockMetadata(i, j, k);
		}

		public static void getSubBlocks(Block thisBlock, Item item, CreativeTabs tab, List list) {
			list.add(new ItemStack(thisBlock, 1, 0));
			list.add(new ItemStack(thisBlock, 1, 1));
			list.add(new ItemStack(thisBlock, 1, 2));
		}
	}

	public static class Enchants {
		public static boolean attemptDamageItem(ItemStack itemstack, int damages, Random random) {
			if (!itemstack.isItemStackDamageable()) {
				return false;
			}
			if (damages > 0) {
				int l;
				int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemstack);
				int negated = 0;
				if (unbreaking > 0) {
					for (l = 0; l < damages; ++l) {
						if (!EnchantmentDurability.negateDamage(itemstack, unbreaking, random)) {
							continue;
						}
						++negated;
					}
				}
				for (l = 0; l < damages; ++l) {
					if (!LOTREnchantmentHelper.negateDamage(itemstack, random)) {
						continue;
					}
					++negated;
				}
				damages -= negated;
				if (damages <= 0) {
					return false;
				}
			}
			itemstack.setItemDamage(itemstack.getItemDamage() + damages);
			return itemstack.getItemDamage() > itemstack.getMaxDamage();
		}

		public static int c_attemptDamageItem(int unmodified, ItemStack stack, int damages, Random random, EntityLivingBase elb) {
			int ret = unmodified;
			for (int i = 0; i < damages; ++i) {
				if (!LOTREnchantmentHelper.negateDamage(stack, random)) {
					continue;
				}
				--ret;
			}
			return ret;
		}

		public static float func_152377_a(float base, ItemStack itemstack, EnumCreatureAttribute creatureAttribute) {
			return base + LOTREnchantmentHelper.calcBaseMeleeDamageBoost(itemstack);
		}

		public static int getDamageReduceAmount(ItemStack itemstack) {
			return LOTRWeaponStats.getArmorProtection(itemstack);
		}

		public static float getEnchantmentModifierLiving(float base, EntityLivingBase attacker, EntityLivingBase target) {
			return base + LOTREnchantmentHelper.calcEntitySpecificDamage(attacker.getHeldItem(), target);
		}

		public static int getFireAspectModifier(int base, EntityLivingBase entity) {
			return base + LOTREnchantmentHelper.calcFireAspectForMelee(entity.getHeldItem());
		}

		public static int getFortuneModifier(int base, EntityLivingBase entity) {
			return base + LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
		}

		public static int getKnockbackModifier(int base, EntityLivingBase attacker, EntityLivingBase target) {
			int i = base;
			i += LOTRWeaponStats.getBaseExtraKnockback(attacker.getHeldItem());
			return i + LOTREnchantmentHelper.calcExtraKnockback(attacker.getHeldItem());
		}

		public static int getLootingModifier(int base, EntityLivingBase entity) {
			return base + LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
		}

		public static int getMaxFireProtectionLevel(int base, Entity entity) {
			return Math.max(base, LOTREnchantmentHelper.getMaxFireProtectionLevel(entity.getLastActiveItems()));
		}

		public static boolean getSilkTouchModifier(boolean base, EntityLivingBase entity) {
			boolean flag = base;
			if (LOTREnchantmentHelper.isSilkTouch(entity.getHeldItem())) {
				flag = true;
			}
			return flag;
		}

		public static int getSpecialArmorProtection(int base, ItemStack[] armor, DamageSource source) {
			int i = base;
			i += LOTREnchantmentHelper.calcSpecialArmorSetProtection(armor, source);
			return MathHelper.clamp_int(i, 0, 25);
		}

		public static boolean isPlayerMeleeKill(Entity entity, DamageSource source) {
			return entity instanceof EntityPlayer && source.getSourceOfDamage() == entity;
		}
	}

	public static class EntityPackets {
		public static Packet getMobSpawnPacket(Entity entity) {
			EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
			if (er == null || er.usesVanillaSpawning()) {
				return null;
			}
			FMLMessage.EntitySpawnMessage msg = new FMLMessage.EntitySpawnMessage(er, entity, er.getContainer());
			ByteBuf data = Unpooled.buffer();
			data.writeByte(2);
			try {
				new FMLRuntimeCodec().encodeInto(null, msg, data);
			} catch (Exception e) {
				LOTRLog.logger.error("***********************************************");
				LOTRLog.logger.error("LOTR: ERROR sending mob spawn packet to client!");
				LOTRLog.logger.error("***********************************************");
			}
			return new FMLProxyPacket(data, "FML");
		}
	}

	public static class Fence {
        @Deprecated
        public static boolean canConnectFenceTo(World world, int i, int j, int k) {
            Block block = world.getBlock(i, j, k);
            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                return true;
            }
            return block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd;
        }

        public static boolean canConnectFenceTo(IBlockAccess world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
				return true;
			}
			return block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd;
		}

		public static boolean canPlacePressurePlate(Block block) {
			return block instanceof BlockFence;
		}
	}

	public static class Fire {
		public static boolean isFireSpreadDisabled() {
			return LOTRConfig.disableFireSpread;
		}
	}

	public static class Food {
		public static float getExhaustionFactor() {
			if (LOTRConfig.changedHunger) {
				return 0.3f;
			}
			return 1.0f;
		}
	}

	public static class Grass {
		public static int MIN_GRASS_LIGHT = 4;
		public static int MAX_GRASS_OPACITY = 2;
		public static int MIN_SPREAD_LIGHT = 9;

		public static void updateTick_optimised(World world, int i, int j, int k, Random random) {
			if (!world.isRemote) {
				int checkRange = 1;
				if (!world.checkChunksExist(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
					return;
				}
				if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockLightOpacity(i, j + 1, k) > 2) {
					Block block = world.getBlock(i, j, k);
					if (block == Blocks.grass) {
						world.setBlock(i, j, k, Blocks.dirt);
					}
					if (block == LOTRMod.mudGrass) {
						world.setBlock(i, j, k, LOTRMod.mud);
					}
				} else if (world.getBlockLightValue(i, j + 1, k) >= 9) {
					for (int l = 0; l < 4; ++l) {
						int k1;
						int j1;
						int i1 = i + random.nextInt(3) - 1;
						if (!world.blockExists(i1, j1 = j + random.nextInt(5) - 3, k1 = k + random.nextInt(3) - 1) || !world.checkChunksExist(i1 - checkRange, j1 - checkRange, k1 - checkRange, i1 + checkRange, j1 + checkRange, k1 + checkRange) || world.getBlockLightValue(i1, j1 + 1, k1) < 4 || world.getBlockLightOpacity(i1, j1 + 1, k1) > 2) {
							continue;
						}
						Block block = world.getBlock(i1, j1, k1);
						int meta = world.getBlockMetadata(i1, j1, k1);
						if (block == Blocks.dirt && meta == 0) {
							world.setBlock(i1, j1, k1, Blocks.grass, 0, 3);
						}
						if (block != LOTRMod.mud || meta != 0) {
							continue;
						}
						world.setBlock(i1, j1, k1, LOTRMod.mudGrass, 0, 3);
					}
				}
			}
		}
	}

	public static class Lightning {
		public static void doSetBlock(World world, int i, int j, int k, Block block) {
			if (block == Blocks.fire && LOTRConfig.disableLightningGrief) {
				return;
			}
			world.setBlock(i, j, k, block);
		}

		public static void init(EntityLightningBolt bolt) {
			bolt.renderDistanceWeight = 5.0;
		}
	}

	public static class Minecart {
		public static boolean checkForDepoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
			World world = cart.worldObj;
			if (block instanceof LOTRBlockMechanisedRail) {
				LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail) block;
				int meta = world.getBlockMetadata(x, y, z);
				flagIn = !mechRailBlock.isPowerOn(meta);
			}
			return flagIn;
		}

		public static boolean checkForPoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
			World world = cart.worldObj;
			if (block instanceof LOTRBlockMechanisedRail) {
				LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail) block;
				int meta = world.getBlockMetadata(x, y, z);
				flagIn = mechRailBlock.isPowerOn(meta);
			}
			return flagIn;
		}
	}

	public static class NetHandlerClient {
		public static void handleEntityMovement(NetHandlerPlayClient handler, S14PacketEntity packet) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = packet.func_149065_a(world);
			if (entity != null) {
				entity.serverPosX += packet.func_149062_c();
				entity.serverPosY += packet.func_149061_d();
				entity.serverPosZ += packet.func_149064_e();
				if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
					double d0 = entity.serverPosX / 32.0;
					double d1 = entity.serverPosY / 32.0;
					double d2 = entity.serverPosZ / 32.0;
					float f = packet.func_149060_h() ? packet.func_149066_f() * 360 / 256.0f : entity.rotationYaw;
					float f1 = packet.func_149060_h() ? packet.func_149063_g() * 360 / 256.0f : entity.rotationPitch;
					entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
				}
			}
		}

		public static void handleEntityTeleport(NetHandlerPlayClient handler, S18PacketEntityTeleport packet) {
			World world = LOTRMod.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.func_149451_c());
			if (entity != null) {
				entity.serverPosX = packet.func_149449_d();
				entity.serverPosY = packet.func_149448_e();
				entity.serverPosZ = packet.func_149446_f();
				if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
					double d0 = entity.serverPosX / 32.0;
					double d1 = entity.serverPosY / 32.0 + 0.015625;
					double d2 = entity.serverPosZ / 32.0;
					float f = packet.func_149450_g() * 360 / 256.0f;
					float f1 = packet.func_149447_h() * 360 / 256.0f;
					entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
				}
			}
		}
	}

	public static class PathFinder {
		public static boolean isFenceGate(Block block) {
			return block instanceof BlockFenceGate;
		}

		public static boolean isWoodenDoor(Block block) {
			return block instanceof BlockDoor && block.getMaterial() == Material.wood;
		}
	}

	public static class Piston {
		@SuppressWarnings("Convert2Lambda")
		public static boolean canPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
			AxisAlignedBB bannerSearchBox = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 4, k + 1);
			List banners = world.selectEntitiesWithinAABB(LOTREntityBanner.class, bannerSearchBox, new IEntitySelector() {

				@Override
				public boolean isEntityApplicable(Entity entity) {
					LOTREntityBanner banner = (LOTREntityBanner) entity;
					return !banner.isDead && banner.isProtectingTerritory();
				}
			});
			if (!banners.isEmpty()) {
				return false;
			}
			return LOTRReflection.canPistonPushBlock(block, world, i, j, k, flag);
		}

	}

	public static class Player {
		public static boolean canEat(EntityPlayer entityplayer, boolean forced) {
			if (entityplayer.capabilities.disableDamage) {
				return false;
			}
			if (forced || entityplayer.getFoodStats().needFood()) {
				return true;
			}
			boolean feastMode = LOTRConfig.canAlwaysEat;
			if (entityplayer.worldObj.isRemote) {
				feastMode = LOTRLevelData.clientside_thisServer_feastMode;
			}
			return feastMode && entityplayer.ridingEntity == null;
		}
	}

	public static class Potions {
		public static double getStrengthModifier(Potion thisPotion, int level, AttributeModifier modifier) {
			if (thisPotion.id == Potion.weakness.id) {
				return -0.5 * (level + 1);
			}
			return 0.5 * (level + 1);
		}
	}

	public static class Pumpkin {
		public static int alterIconSideParam(Block block, int side, int meta) {
			if (block == Blocks.pumpkin) {
				if (meta == 2 && side == 2) {
					side = 3;
				} else if (meta == 3 && side == 5) {
					side = 4;
				} else if (meta == 0 && side == 3) {
					side = 2;
				} else if (meta == 1 && side == 4) {
					side = 5;
				}
			}
			return side;
		}
	}

	public static class Spawner {
		public static int performSpawning_optimised(WorldServer world, boolean hostiles, boolean peacefuls, boolean rareTick) {
			return LOTRSpawnerAnimals.performSpawning(world, hostiles, peacefuls, rareTick);
		}
	}

	public static class StaticLiquid {
		public static boolean isFlammable(World world, int i, int j, int k) {
			return world.blockExists(i, j, k) && world.getBlock(i, j, k).getMaterial().getCanBurn();
		}

		public static void updateTick_optimised(Block thisBlock, World world, int i, int j, int k, Random random) {
			if (thisBlock.getMaterial() == Material.lava) {
				int tries = random.nextInt(3);
				for (int l = 0; l < tries; ++l) {
					if (world.blockExists(i += random.nextInt(3) - 1, ++j, k += random.nextInt(3) - 1)) {
						Block block = world.getBlock(i, j, k);
						if (block.getMaterial() == Material.air) {
							if (!isFlammable(world, i - 1, j, k) && !isFlammable(world, i + 1, j, k) && !isFlammable(world, i, j, k - 1) && !isFlammable(world, i, j, k + 1) && !isFlammable(world, i, j - 1, k) && !isFlammable(world, i, j + 1, k)) {
								continue;
							}
							world.setBlock(i, j, k, Blocks.fire);
							return;
						}
						if (!block.getMaterial().blocksMovement()) {
							continue;
						}
					}
					return;
				}
				if (tries == 0) {
					int i1 = i;
					int k1 = k;
					for (int l = 0; l < 3; ++l) {
						i = i1 + random.nextInt(3) - 1;
						if (!world.blockExists(i, j, k = k1 + random.nextInt(3) - 1) || !world.isAirBlock(i, j + 1, k) || !isFlammable(world, i, j, k)) {
							continue;
						}
						world.setBlock(i, j + 1, k, Blocks.fire);
					}
				}
			}
		}
	}

	public static class Stone {
		public static IIcon getIconSideMeta(Block block, IIcon defaultIcon, int side, int meta) {
			if (LOTRConfig.snowyStone && block == Blocks.stone && meta == 1000) {
				if (side == 1) {
					return Blocks.snow.getIcon(1, 0);
				}
				if (side != 0) {
					return LOTRCommonIcons.iconStoneSnow;
				}
			}
			return defaultIcon;
		}

        @Deprecated
        public static IIcon getIconWorld(Block block, World world, int i, int j, int k, int side) {
            Material aboveMat;
            if (LOTRConfig.snowyStone && block == Blocks.stone && side != 0 && side != 1 && ((aboveMat = world.getBlock(i, j + 1, k).getMaterial()) == Material.snow || aboveMat == Material.craftedSnow)) {
                return LOTRCommonIcons.iconStoneSnow;
            }
            return block.getIcon(side, world.getBlockMetadata(i, j, k));
        }

		public static IIcon getIconWorld(Block block, IBlockAccess world, int i, int j, int k, int side) {
			Material aboveMat;
			if (LOTRConfig.snowyStone && block == Blocks.stone && side != 0 && side != 1 && ((aboveMat = world.getBlock(i, j + 1, k).getMaterial()) == Material.snow || aboveMat == Material.craftedSnow)) {
				return LOTRCommonIcons.iconStoneSnow;
			}
			return block.getIcon(side, world.getBlockMetadata(i, j, k));
		}
	}

	public static class Trapdoor {
		public static boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side) {
			return true;
		}

		public static int getRenderType(Block block) {
			if (LOTRMod.proxy != null) {
				return LOTRMod.proxy.getTrapdoorRenderID();
			}
			return 0;
		}

		public static boolean isValidSupportBlock(Block block) {
			return true;
		}
	}

	public static class Wall {
        @Deprecated
        public static boolean canConnectWallTo(World world, int i, int j, int k) {
            return Fence.canConnectFenceTo(world, i, j, k);
        }

        public static boolean canConnectWallTo(IBlockAccess world, int i, int j, int k) {
			return Fence.canConnectFenceTo(world, i, j, k);
		}
	}

}
