package lotr.common.network;

import java.util.Random;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRPacketWeaponFX implements IMessage {
	public Type type;
	public int entityID;

	public LOTRPacketWeaponFX() {
	}

	public LOTRPacketWeaponFX(Type t, Entity entity) {
		type = t;
		entityID = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = Type.values()[typeID];
		entityID = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
		data.writeInt(entityID);
	}

	public static class Handler implements IMessageHandler<LOTRPacketWeaponFX, IMessage> {
		@Override
		public IMessage onMessage(LOTRPacketWeaponFX packet, MessageContext context) {
			block4:
			{
				Random rand;
				Entity entity;
				double z;
				double y;
				double x;
				block8:
				{
					World world;
					block7:
					{
						block6:
						{
							block5:
							{
								world = LOTRMod.proxy.getClientWorld();
								entity = world.getEntityByID(packet.entityID);
								if (entity == null) {
									break block4;
								}
								x = entity.posX;
								y = entity.boundingBox.minY;
								z = entity.posZ;
								rand = world.rand;
								if (packet.type != Type.MACE_SAURON) {
									break block5;
								}
								for (int i = 0; i < 360; i += 2) {
									float angle = (float) Math.toRadians(i);
									double dist = 1.5;
									double d = dist * MathHelper.sin(angle);
									double d1 = dist * MathHelper.cos(angle);
									world.spawnParticle("smoke", x + d, y + 0.1, z + d1, d * 0.2, 0.0, d1 * 0.2);
								}
								break block4;
							}
							if (packet.type != Type.STAFF_GANDALF_WHITE) {
								break block6;
							}
							for (int i = 0; i < 360; i += 2) {
								float angle = (float) Math.toRadians(i);
								double dist = 1.5;
								double d = dist * MathHelper.sin(angle);
								double d1 = dist * MathHelper.cos(angle);
								LOTRMod.proxy.spawnParticle("blueFlame", x + d, y + 0.1, z + d1, d * 0.2, 0.0, d1 * 0.2);
							}
							break block4;
						}
						if (packet.type != Type.FIREBALL_GANDALF_WHITE) {
							break block7;
						}
						LOTRMod.proxy.spawnParticle("gandalfFireball", x, y, z, 0.0, 0.0, 0.0);
						break block4;
					}
					if (packet.type != Type.INFERNAL) {
						break block8;
					}
					for (int i = 0; i < 20; ++i) {
						double d1 = y + entity.height * 0.7f;
						float angleXZ = rand.nextFloat() * 3.1415927f * 2.0f;
						float angleY = rand.nextFloat() * 3.1415927f * 2.0f;
						float speed = MathHelper.randomFloatClamp(rand, 0.1f, 0.15f);
						double d3 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
						double d4 = MathHelper.sin(angleY) * speed;
						double d5 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
						d4 += 0.15000000596046448;
						world.spawnParticle("flame", x, d1, z, d3 + (entity.posX - entity.lastTickPosX), d4 + (entity.posY - entity.lastTickPosY), d5 + (entity.posZ - entity.lastTickPosZ));
					}
					break block4;
				}
				if (packet.type != Type.CHILLING) {
					break block4;
				}
				for (int i = 0; i < 40; ++i) {
					double d1 = y + entity.height * 0.7f;
					float angleXZ = rand.nextFloat() * 3.1415927f * 2.0f;
					float angleY = rand.nextFloat() * 3.1415927f * 2.0f;
					float speed = MathHelper.randomFloatClamp(rand, 0.1f, 0.2f);
					double d3 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
					double d4 = MathHelper.sin(angleY) * speed;
					double d5 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
					LOTRMod.proxy.spawnParticle("chill", x, d1, z, d3 + (entity.posX - entity.lastTickPosX), d4 + (entity.posY - entity.lastTickPosY), d5 + (entity.posZ - entity.lastTickPosZ));
				}
			}
			return null;
		}
	}

	public enum Type {
		MACE_SAURON, STAFF_GANDALF_WHITE, FIREBALL_GANDALF_WHITE, INFERNAL, CHILLING

	}

}
