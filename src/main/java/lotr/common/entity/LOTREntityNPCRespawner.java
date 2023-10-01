package lotr.common.entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCRespawner;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class LOTREntityNPCRespawner extends Entity {
	public static int MAX_SPAWN_BLOCK_RANGE = 64;
	public float spawnerSpin;
	public float prevSpawnerSpin;
	public int spawnInterval = 3600;
	public int noPlayerRange = 24;
	public Class spawnClass1;
	public Class spawnClass2;
	public int checkHorizontalRange = 8;
	public int checkVerticalMin = -4;
	public int checkVerticalMax = 4;
	public int spawnCap = 4;
	public int spawnHorizontalRange = 4;
	public int spawnVerticalMin = -2;
	public int spawnVerticalMax = 2;
	public int homeRange = -1;
	public boolean setHomePosFromSpawn;
	public int mountSetting;
	public int blockEnemySpawns;

	public LOTREntityNPCRespawner(World world) {
		super(world);
		setSize(1.0f, 1.0f);
		spawnerSpin = rand.nextFloat() * 360.0f;
	}

	public static boolean isSpawnBlocked(Entity entity, LOTRFaction spawnFaction) {
		int j;
		int k;
		int range;
		World world = entity.worldObj;
		int i = MathHelper.floor_double(entity.posX);
		AxisAlignedBB originBB = AxisAlignedBB.getBoundingBox(i, j = MathHelper.floor_double(entity.boundingBox.minY), k = MathHelper.floor_double(entity.posZ), i + 1, j + 1, k + 1);
		AxisAlignedBB searchBB = originBB.expand(range = 64, range, range);
		List spawners = world.getEntitiesWithinAABB(LOTREntityNPCRespawner.class, searchBB);
		if (!spawners.isEmpty()) {
			for (Object obj : spawners) {
				AxisAlignedBB spawnBlockBB;
				LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) obj;
				if (!spawner.blockEnemySpawns() || !(spawnBlockBB = spawner.createSpawnBlockRegion()).intersectsWith(searchBB) || !spawnBlockBB.intersectsWith(originBB) || !spawner.isEnemySpawnBlocked(spawnFaction)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean isSpawnBlocked(LOTREntityNPC npc) {
		return isSpawnBlocked(npc, npc.getFaction());
	}

	@Override
	public void applyEntityCollision(Entity entity) {
	}

	public boolean blockEnemySpawns() {
		return blockEnemySpawns > 0;
	}

	@Override
	public boolean canBeCollidedWith() {
		if (!worldObj.isRemote) {
			return false;
		}
		EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
		if (entityplayer == null) {
			return false;
		}
		return entityplayer.capabilities.isCreativeMode;
	}

	public AxisAlignedBB createSpawnBlockRegion() {
		if (!blockEnemySpawns()) {
			return null;
		}
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		int range = blockEnemySpawns;
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
	}

	@Override
	public void entityInit() {
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(LOTRMod.npcRespawner);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte b) {
		if (b == 16) {
			for (int l = 0; l < 16; ++l) {
				double d = posX + (rand.nextDouble() - 0.5) * width;
				double d1 = posY + rand.nextDouble() * height;
				double d2 = posZ + (rand.nextDouble() - 0.5) * width;
				worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.npcRespawner), d, d1, d2, 0.0, 0.0, 0.0);
			}
		} else {
			super.handleHealthUpdate(b);
		}
	}

	public boolean hasHomeRange() {
		return homeRange >= 0;
	}

	@Override
	public boolean interactFirst(EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			if (!worldObj.isRemote) {
				IMessage packet = new LOTRPacketNPCRespawner(this);
				LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
			return true;
		}
		return false;
	}

	public boolean isEnemySpawnBlocked(LOTREntityNPC npc) {
		return isEnemySpawnBlocked(npc.getFaction());
	}

	public boolean isEnemySpawnBlocked(LOTRFaction spawnFaction) {
		LOTRFaction faction1;
		LOTRFaction faction2;
		if (spawnClass1 != null && (faction1 = ((LOTREntityNPC) EntityList.createEntityByName(LOTREntities.getStringFromClass(spawnClass1), worldObj)).getFaction()) != null && faction1.isBadRelation(spawnFaction)) {
			return true;
		}
		return spawnClass2 != null && (faction2 = ((LOTREntityNPC) EntityList.createEntityByName(LOTREntities.getStringFromClass(spawnClass2), worldObj)).getFaction()) != null && faction2.isBadRelation(spawnFaction);
	}

	@Override
	public boolean isInvisible() {
		if (!worldObj.isRemote) {
			return super.isInvisible();
		}
		EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
		return entityplayer == null || !entityplayer.capabilities.isCreativeMode;
	}

	public void onBreak() {
		worldObj.playSoundAtEntity(this, Blocks.glass.stepSound.getBreakSound(), (Blocks.glass.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.glass.stepSound.getPitch() * 0.8f);
		worldObj.setEntityState(this, (byte) 16);
		setDead();
	}

	@SuppressWarnings("Convert2Lambda")
	@Override
	public void onUpdate() {
		int maxX;
		int entities;
		int maxY;
		int minZ;
		int minX;
		int minY;
		int i;
		int j;
		int maxZ;
		int k;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		prevSpawnerSpin = spawnerSpin;
		spawnerSpin += 6.0f;
		prevSpawnerSpin = MathHelper.wrapAngleTo180_float(prevSpawnerSpin);
		spawnerSpin = MathHelper.wrapAngleTo180_float(spawnerSpin);
		motionX = 0.0;
		motionY = 0.0;
		motionZ = 0.0;
		moveEntity(motionX, motionY, motionZ);
		if (!worldObj.isRemote && ticksExisted % spawnInterval == 0 && (spawnClass1 != null || spawnClass2 != null) && worldObj.checkChunksExist(minX = (i = MathHelper.floor_double(posX)) - checkHorizontalRange, minY = (j = MathHelper.floor_double(boundingBox.minY)) + checkVerticalMin, minZ = (k = MathHelper.floor_double(posZ)) - checkHorizontalRange, maxX = i + checkHorizontalRange, maxY = j + checkVerticalMax, maxZ = k + checkHorizontalRange) && worldObj.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, noPlayerRange) == null && (entities = worldObj.selectEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1), new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				if (!entity.isEntityAlive()) {
					return false;
				}
				Class<?> entityClass = entity.getClass();
				return spawnClass1 != null && spawnClass1.isAssignableFrom(entityClass) || spawnClass2 != null && spawnClass2.isAssignableFrom(entityClass);
			}
		}).size()) < spawnCap) {
			int attempts = 16;
			for (int l = 0; l < attempts; ++l) {
				int spawnX = i + MathHelper.getRandomIntegerInRange(rand, -spawnHorizontalRange, spawnHorizontalRange);
				int spawnY = j + MathHelper.getRandomIntegerInRange(rand, spawnVerticalMin, spawnVerticalMax);
				int spawnZ = k + MathHelper.getRandomIntegerInRange(rand, -spawnHorizontalRange, spawnHorizontalRange);
				Block belowBlock = worldObj.getBlock(spawnX, spawnY - 1, spawnZ);
				worldObj.getBlockMetadata(spawnX, spawnY - 1, spawnZ);
				boolean belowSolid = belowBlock.isSideSolid(worldObj, spawnX, spawnY - 1, spawnZ, ForgeDirection.UP);
				if (!belowSolid || worldObj.getBlock(spawnX, spawnY, spawnZ).isNormalCube() || worldObj.getBlock(spawnX, spawnY + 1, spawnZ).isNormalCube()) {
					continue;
				}
				Class entityClass = null;
				if (spawnClass1 != null && spawnClass2 != null) {
					entityClass = rand.nextInt(3) == 0 ? spawnClass2 : spawnClass1;
				} else if (spawnClass1 != null) {
					entityClass = spawnClass1;
				} else if (spawnClass2 != null) {
					entityClass = spawnClass2;
				}
				String entityName = LOTREntities.getStringFromClass(entityClass);
				LOTREntityNPC entity = (LOTREntityNPC) EntityList.createEntityByName(entityName, worldObj);
				entity.setLocationAndAngles(spawnX + 0.5, spawnY, spawnZ + 0.5, rand.nextFloat() * 360.0f, 0.0f);
				entity.isNPCPersistent = true;
				entity.liftSpawnRestrictions = true;
				if (!entity.getCanSpawnHere()) {
					continue;
				}
				entity.liftSpawnRestrictions = false;
				worldObj.spawnEntityInWorld(entity);
				if (mountSetting == 0) {
					entity.spawnRidingHorse = false;
				} else if (mountSetting == 1) {
					entity.spawnRidingHorse = true;
				}
				entity.onSpawnWithEgg(null);
				if (hasHomeRange()) {
					if (setHomePosFromSpawn) {
						entity.setHomeArea(spawnX, spawnY, spawnZ, homeRange);
					} else {
						entity.setHomeArea(i, j, k, homeRange);
					}
				} else {
					entity.detachHome();
				}
				entities++;
				if (entities >= spawnCap) {
					break;
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		readSpawnerDataFromNBT(nbt);
	}

	public void readSpawnerDataFromNBT(NBTTagCompound nbt) {
		spawnInterval = nbt.getInteger("SpawnInterval");
		if (spawnInterval <= 0) {
			spawnInterval = 3600;
		}
		noPlayerRange = nbt.getByte("NoPlayerRange");
		spawnClass1 = LOTREntities.getClassFromString(nbt.getString("SpawnClass1"));
		spawnClass2 = LOTREntities.getClassFromString(nbt.getString("SpawnClass2"));
		if (spawnClass1 != null && !LOTREntityNPC.class.isAssignableFrom(spawnClass1)) {
			spawnClass1 = null;
		}
		if (spawnClass2 != null && !LOTREntityNPC.class.isAssignableFrom(spawnClass2)) {
			spawnClass2 = null;
		}
		checkHorizontalRange = nbt.getByte("CheckHorizontal");
		checkVerticalMin = nbt.getByte("CheckVerticalMin");
		checkVerticalMax = nbt.getByte("CheckVerticalMax");
		spawnCap = nbt.getByte("SpawnCap");
		spawnHorizontalRange = nbt.getByte("SpawnHorizontal");
		spawnVerticalMin = nbt.getByte("SpawnVerticalMin");
		spawnVerticalMax = nbt.getByte("SpawnVerticalMax");
		homeRange = nbt.getByte("HomeRange");
		setHomePosFromSpawn = nbt.getBoolean("HomeSpawn");
		mountSetting = nbt.getByte("MountSetting");
		blockEnemySpawns = nbt.getByte("BlockEnemy");
	}

	public void setBlockEnemySpawnRange(int i) {
		blockEnemySpawns = Math.min(i, 64);
	}

	public void setCheckRanges(int xz, int y, int y1, int l) {
		checkHorizontalRange = xz;
		checkVerticalMin = y;
		checkVerticalMax = y1;
		spawnCap = l;
	}

	public void setHomePosFromSpawn() {
		setHomePosFromSpawn = true;
	}

	public void setMountSetting(int i) {
		mountSetting = i;
	}

	public void setNoPlayerRange(int i) {
		noPlayerRange = i;
	}

	public void setSpawnClass(Class c) {
		spawnClass1 = c;
	}

	public void setSpawnClasses(Class c1, Class c2) {
		spawnClass1 = c1;
		spawnClass2 = c2;
	}

	public void setSpawnInterval(int i) {
		spawnInterval = i;
	}

	public void setSpawnIntervalMinutes(int m) {
		int s = m * 60;
		spawnInterval = s * 20;
	}

	public void setSpawnRanges(int xz, int y, int y1, int h) {
		spawnHorizontalRange = xz;
		spawnVerticalMin = y;
		spawnVerticalMax = y1;
		homeRange = h;
	}

	public void toggleMountSetting() {
		mountSetting = mountSetting == 0 ? 1 : mountSetting == 1 ? 2 : 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		writeSpawnerDataToNBT(nbt);
	}

	public void writeSpawnerDataToNBT(NBTTagCompound nbt) {
		nbt.setInteger("SpawnInterval", spawnInterval);
		nbt.setByte("NoPlayerRange", (byte) noPlayerRange);
		String class1String = "";
		String class2String = "";
		if (spawnClass1 != null) {
			class1String = LOTREntities.getStringFromClass(spawnClass1);
		}
		if (spawnClass2 != null) {
			class2String = LOTREntities.getStringFromClass(spawnClass2);
		}
		nbt.setString("SpawnClass1", class1String == null ? "" : class1String);
		nbt.setString("SpawnClass2", class2String == null ? "" : class2String);
		nbt.setByte("CheckHorizontal", (byte) checkHorizontalRange);
		nbt.setByte("CheckVerticalMin", (byte) checkVerticalMin);
		nbt.setByte("CheckVerticalMax", (byte) checkVerticalMax);
		nbt.setByte("SpawnCap", (byte) spawnCap);
		nbt.setByte("SpawnHorizontal", (byte) spawnHorizontalRange);
		nbt.setByte("SpawnVerticalMin", (byte) spawnVerticalMin);
		nbt.setByte("SpawnVerticalMax", (byte) spawnVerticalMax);
		nbt.setByte("HomeRange", (byte) homeRange);
		nbt.setBoolean("HomeSpawn", setHomePosFromSpawn);
		nbt.setByte("MountSetting", (byte) mountSetting);
		nbt.setByte("BlockEnemy", (byte) blockEnemySpawns);
	}

}
