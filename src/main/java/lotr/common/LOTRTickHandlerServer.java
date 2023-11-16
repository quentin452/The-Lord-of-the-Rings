package lotr.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import lotr.common.block.LOTRBlockPortal;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.item.LOTRItemStructureSpawner;
import lotr.common.world.*;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnerNPCs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;
import java.util.List;

public class LOTRTickHandlerServer {
	public static HashMap playersInPortals = new HashMap();
	public static HashMap playersInElvenPortals = new HashMap();
	public static HashMap playersInMorgulPortals = new HashMap();
	public int fireworkDisplay;

	public LOTRTickHandlerServer() {
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		World world = player.worldObj;
		if (world == null || world.isRemote) {
			return;
		}
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			if (event.phase == TickEvent.Phase.START && entityplayer.playerNetServerHandler != null && !(entityplayer.playerNetServerHandler instanceof LOTRNetHandlerPlayServer && LOTRConfig.enableAttackCooldown)) {
				entityplayer.playerNetServerHandler = new LOTRNetHandlerPlayServer(MinecraftServer.getServer(), entityplayer.playerNetServerHandler.netManager, entityplayer);
			}
			if (event.phase == TickEvent.Phase.END) {
				List items;
				EntityItem item;
				ItemStack heldItem;
				LOTRLevelData.getData(entityplayer).onUpdate(entityplayer, (WorldServer) world);
				NetHandlerPlayServer netHandler = entityplayer.playerNetServerHandler;
				if (netHandler instanceof LOTRNetHandlerPlayServer) {
					((LOTRNetHandlerPlayServer) netHandler).update();
				}
				heldItem = entityplayer.getHeldItem();
				if (heldItem != null && (heldItem.getItem() instanceof ItemWritableBook || heldItem.getItem() instanceof ItemEditableBook)) {
					entityplayer.func_143004_u();
				}
				if (entityplayer.dimension == 0 && LOTRLevelData.madePortal == 0) {
					items = world.getEntitiesWithinAABB(EntityItem.class, entityplayer.boundingBox.expand(16.0, 16.0, 16.0));
					for (Object obj : items) {
						item = (EntityItem) obj;
						if (LOTRLevelData.madePortal != 0 || item.getEntityItem() == null || item.getEntityItem().getItem() != LOTRMod.goldRing || !item.isBurning()) {
							continue;
						}
						LOTRLevelData.setMadePortal(1);
						LOTRLevelData.markOverworldPortalLocation(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY), MathHelper.floor_double(item.posZ));
						item.setDead();
						world.createExplosion(entityplayer, item.posX, item.posY + 3.0, item.posZ, 3.0f, true);
						LOTREntityPortal portal = new LOTREntityPortal(world);
						portal.setLocationAndAngles(item.posX, item.posY + 3.0, item.posZ, 0.0f, 0.0f);
						world.spawnEntityInWorld(portal);
					}
				}
				if (entityplayer.dimension == 0 || entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
					items = world.getEntitiesWithinAABB(EntityItem.class, entityplayer.boundingBox.expand(16.0, 16.0, 16.0));
					for (Object obj : items) {
						boolean foundPortalLocation;
						int k1;
						int[] portalLocation;
						int i1;
						item = (EntityItem) obj;
						if (item.getEntityItem() == null) {
							continue;
						}
						int i = MathHelper.floor_double(item.posX);
						int j = MathHelper.floor_double(item.posY);
						int k = MathHelper.floor_double(item.posZ);
						ItemStack itemstack = item.getEntityItem();
						if ((LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.LOTHLORIEN) >= 1.0f || LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HIGH_ELF) >= 1.0f) && (itemstack.getItem() == Item.getItemFromBlock(LOTRMod.elanor) || itemstack.getItem() == Item.getItemFromBlock(LOTRMod.niphredil))) {
							foundPortalLocation = false;
							portalLocation = new int[3];
							for (i1 = i - 2; !foundPortalLocation && i1 <= i + 2; ++i1) {
								for (k1 = k - 2; !foundPortalLocation && k1 <= k + 2; ++k1) {
									if (!((LOTRBlockPortal) LOTRMod.elvenPortal).isValidPortalLocation(world, i1, j, k1, false)) {
										continue;
									}
									foundPortalLocation = true;
									portalLocation = new int[]{i1, j, k1};
								}
							}
							if (foundPortalLocation) {
								item.setDead();
								for (i1 = -1; i1 <= 1; ++i1) {
									for (k1 = -1; k1 <= 1; ++k1) {
										world.setBlock(portalLocation[0] + i1, portalLocation[1], portalLocation[2] + k1, LOTRMod.elvenPortal, 0, 2);
									}
								}
							}
						}
						if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) < 1.0f && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) < 1.0f && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DOL_GULDUR) < 1.0f || !LOTRMod.isOreNameEqual(itemstack, "bone")) {
							continue;
						}
						foundPortalLocation = false;
						portalLocation = new int[3];
						for (i1 = i - 2; !foundPortalLocation && i1 <= i + 2; ++i1) {
							for (k1 = k - 2; !foundPortalLocation && k1 <= k + 2; ++k1) {
								if (!((LOTRBlockPortal) LOTRMod.morgulPortal).isValidPortalLocation(world, i1, j, k1, false)) {
									continue;
								}
								foundPortalLocation = true;
								portalLocation = new int[]{i1, j, k1};
							}
						}
						if (!foundPortalLocation) {
							continue;
						}
						item.setDead();
						for (i1 = -1; i1 <= 1; ++i1) {
							for (k1 = -1; k1 <= 1; ++k1) {
								world.setBlock(portalLocation[0] + i1, portalLocation[1], portalLocation[2] + k1, LOTRMod.morgulPortal, 0, 2);
							}
						}
					}
				}
				if ((entityplayer.dimension == 0 || entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && playersInPortals.containsKey(entityplayer)) {
					int i;
					List portals = world.getEntitiesWithinAABB(LOTREntityPortal.class, entityplayer.boundingBox.expand(8.0, 8.0, 8.0));
					boolean inPortal = false;
					for (i = 0; i < portals.size(); ++i) {
						LOTREntityPortal portal = (LOTREntityPortal) portals.get(i);
						if (!portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							continue;
						}
						inPortal = true;
						break;
					}
					if (inPortal) {
						i = (Integer) playersInPortals.get(entityplayer);
						i++;
						playersInPortals.put(entityplayer, i);
						if (i >= 100) {
							int dimension = 0;
							if (entityplayer.dimension == 0) {
								dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
							} else if (entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
								dimension = 0;
							}
							MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, new LOTRTeleporter(DimensionManager.getWorld(dimension), true));
							playersInPortals.remove(entityplayer);
						}
					} else {
						playersInPortals.remove(entityplayer);
					}
				}
				updatePlayerInPortal(entityplayer, playersInElvenPortals, (LOTRBlockPortal) LOTRMod.elvenPortal);
				updatePlayerInPortal(entityplayer, playersInMorgulPortals, (LOTRBlockPortal) LOTRMod.morgulPortal);
				if (entityplayer.dimension == LOTRDimension.UTUMNO.dimensionID) {
					int i = MathHelper.floor_double(entityplayer.posX);
					int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
					int k = MathHelper.floor_double(entityplayer.posZ);
					int range = 32;
					for (int l = 0; l < 60; ++l) {
						int i1 = i + world.rand.nextInt(range) - world.rand.nextInt(range);
						int j1 = j + world.rand.nextInt(range) - world.rand.nextInt(range);
						int k1 = k + world.rand.nextInt(range) - world.rand.nextInt(range);
						if (LOTRUtumnoLevel.forY(j1) == LOTRUtumnoLevel.ICE) {
							Block block = world.getBlock(i1, j1, k1);
							int meta = world.getBlockMetadata(i1, j1, k1);
							if (block.getMaterial() == Material.water && meta == 0) {
								world.setBlock(i1, j1, k1, Blocks.ice, 0, 3);
							}
						}
						if (LOTRUtumnoLevel.forY(j1) != LOTRUtumnoLevel.FIRE) {
							continue;
						}
						Block block = world.getBlock(i1, j1, k1);
						int meta = world.getBlockMetadata(i1, j1, k1);
						if (block.getMaterial() != Material.water || meta != 0) {
							continue;
						}
						world.setBlock(i1, j1, k1, Blocks.air, 0, 3);
						LOTRWorldProviderUtumno.doEvaporateFX(world, i1, j1, k1);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if (world.isRemote) {
			return;
		}
		if (event.phase == TickEvent.Phase.START && world == DimensionManager.getWorld(0)) {
			if (LOTRLevelData.needsLoad) {
				LOTRLevelData.load();
			}
			if (LOTRTime.needsLoad) {
				LOTRTime.load();
			}
			if (LOTRFellowshipData.needsLoad) {
				LOTRFellowshipData.loadAll();
			}
			if (LOTRFactionBounties.needsLoad) {
				LOTRFactionBounties.loadAll();
			}
			if (LOTRFactionRelations.needsLoad) {
				LOTRFactionRelations.load();
			}
			if (LOTRConquestGrid.needsLoad) {
				LOTRConquestGrid.loadAllZones();
			}
			for (WorldServer dimWorld : MinecraftServer.getServer().worldServers) {
				WorldInfo prevWorldInfo;
				if (!(dimWorld.provider instanceof LOTRWorldProvider) || (prevWorldInfo = dimWorld.getWorldInfo()).getClass() == LOTRWorldInfo.class) {
					continue;
				}
				LOTRWorldInfo newWorldInfo = new LOTRWorldInfo(world.getWorldInfo());
				newWorldInfo.setWorldName(prevWorldInfo.getWorldName());
				LOTRReflection.setWorldInfo(dimWorld, newWorldInfo);
				FMLLog.info("LOTR: Successfully replaced world info in %s", LOTRDimension.getCurrentDimensionWithFallback(dimWorld).dimensionName);
			}
			LOTRBannerProtection.updateWarningCooldowns();
			LOTRInterModComms.update();
		}
		if (event.phase == TickEvent.Phase.END) {
			if (world == DimensionManager.getWorld(0)) {
				if (LOTRLevelData.anyDataNeedsSave()) {
					LOTRLevelData.save();
				}
				if (LOTRFellowshipData.anyDataNeedsSave()) {
					LOTRFellowshipData.saveAll();
				}
				LOTRFactionBounties.updateAll();
				if (LOTRFactionBounties.anyDataNeedsSave()) {
					LOTRFactionBounties.saveAll();
				}
				if (LOTRFactionRelations.needsSave()) {
					LOTRFactionRelations.save();
				}
				if (LOTRConquestGrid.anyChangedZones()) {
					LOTRConquestGrid.saveChangedZones();
				}
				if (world.getTotalWorldTime() % 600L == 0L) {
					LOTRLevelData.save();
					LOTRFellowshipData.saveAll();
					LOTRFellowshipData.saveAndClearUnusedFellowships();
					LOTRFactionBounties.saveAll();
					LOTRFactionRelations.save();
				}
				int playerDataClearingInterval = LOTRConfig.playerDataClearingInterval;
				playerDataClearingInterval = Math.max(playerDataClearingInterval, 600);
				if (world.getTotalWorldTime() % playerDataClearingInterval == 0L) {
					LOTRLevelData.saveAndClearUnusedPlayerData();
				}
				if (LOTRItemStructureSpawner.lastStructureSpawnTick > 0) {
					--LOTRItemStructureSpawner.lastStructureSpawnTick;
				}
				LOTRTime.update();
				LOTRGreyWandererTracker.updateCooldowns();
			}
			if (world == DimensionManager.getWorld(LOTRDimension.MIDDLE_EARTH.dimensionID)) {
				LOTRDate.update(world);
				if (LOTRMod.canSpawnMobs(world)) {
					LOTRSpawnerNPCs.performSpawning(world);
					LOTREventSpawner.performSpawning(world);
				}
				LOTRConquestGrid.updateZones(world);
				if (!world.playerEntities.isEmpty()) {
					if (LOTRMod.isNewYearsDay()) {
						if (fireworkDisplay == 0 && world.rand.nextInt(4000) == 0) {
							fireworkDisplay = 100 + world.rand.nextInt(300);
						}
						if (fireworkDisplay > 0) {
							--fireworkDisplay;
							if (world.rand.nextInt(50) == 0) {
								int launches = 1 + world.rand.nextInt(7 + world.playerEntities.size() / 2);
								int range = 64;
								for (int l = 0; l < launches; ++l) {
									EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));
									int i = MathHelper.floor_double(entityplayer.posX) + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
									int k = MathHelper.floor_double(entityplayer.posZ) + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
									int fireworks = 1 + world.rand.nextInt(4);
									for (int l1 = 0; l1 < fireworks; ++l1) {
										int j1;
										int k1;
										int groupRange = 8;
										int i1 = i - world.rand.nextInt(groupRange) + world.rand.nextInt(groupRange);
										if (!world.blockExists(i1, 0, k1 = k - world.rand.nextInt(groupRange) + world.rand.nextInt(groupRange)) || !world.getBlock(i1, (j1 = world.getHeightValue(i1, k1)) - 1, k1).isNormalCube()) {
											continue;
										}
										ItemStack itemstack = new ItemStack(Items.fireworks);
										NBTTagCompound itemData = new NBTTagCompound();
										NBTTagCompound fireworkData = new NBTTagCompound();
										NBTTagList explosionsList = new NBTTagList();
										int explosions = 1 + world.rand.nextInt(3);
										for (int l2 = 0; l2 < explosions; ++l2) {
											NBTTagCompound explosionData = new NBTTagCompound();
											if (world.rand.nextBoolean()) {
												explosionData.setBoolean("Flicker", true);
											}
											if (world.rand.nextBoolean()) {
												explosionData.setBoolean("Trail", true);
											}
											int[] colors = new int[1 + world.rand.nextInt(3)];
											for (int l3 = 0; l3 < colors.length; ++l3) {
												colors[l3] = ItemDye.field_150922_c[world.rand.nextInt(ItemDye.field_150922_c.length)];
											}
											explosionData.setIntArray("Colors", colors);
											int effectType = world.rand.nextInt(5);
											if (effectType == 3) {
												effectType = 0;
											}
											explosionData.setByte("Type", (byte) effectType);
											explosionsList.appendTag(explosionData);
										}
										fireworkData.setTag("Explosions", explosionsList);
										int flight = 1 + world.rand.nextInt(3);
										fireworkData.setByte("Flight", (byte) flight);
										itemData.setTag("Fireworks", fireworkData);
										itemstack.setTagCompound(itemData);
										EntityFireworkRocket firework = new EntityFireworkRocket(world, i1 + 0.5, j1 + 0.5, k1 + 0.5, itemstack);
										world.spawnEntityInWorld(firework);
									}
								}
							}
						}
					}
					if (world.getTotalWorldTime() % 20L == 0L) {
						for (Object element : world.playerEntities) {
							EntityPlayer entityplayer = (EntityPlayer) element;
							LOTRLevelData.sendPlayerLocationsToPlayer(entityplayer, world);
						}
					}
				}
			}
			if (world == DimensionManager.getWorld(LOTRDimension.UTUMNO.dimensionID) && !world.playerEntities.isEmpty() && LOTRMod.canSpawnMobs(world)) {
				LOTRSpawnerNPCs.performSpawning(world);
			}
			if (world.provider instanceof LOTRWorldProvider && world.getTotalWorldTime() % 100L == 0L) {
				LOTRBiomeVariantStorage.performCleanup((WorldServer) world);
			}
		}
	}

	public void updatePlayerInPortal(EntityPlayerMP entityplayer, HashMap players, LOTRBlockPortal portalBlock) {
		if ((entityplayer.dimension == 0 || entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
			boolean inPortal;
			inPortal = entityplayer.worldObj.getBlock(MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.boundingBox.minY), MathHelper.floor_double(entityplayer.posZ)) == portalBlock;
			if (inPortal) {
				int i = (Integer) players.get(entityplayer);
				i++;
				players.put(entityplayer, i);
				if (i >= entityplayer.getMaxInPortalTime()) {
					int dimension = 0;
					if (entityplayer.dimension == 0) {
						dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
					} else if (entityplayer.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
						dimension = 0;
					}
					WorldServer newWorld = MinecraftServer.getServer().worldServerForDimension(dimension);
					MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, portalBlock.getPortalTeleporter(newWorld));
					players.remove(entityplayer);
				}
			} else {
				players.remove(entityplayer);
			}
		}
	}
}
