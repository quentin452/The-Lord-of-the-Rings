package lotr.common.item;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

import java.util.UUID;

public class LOTRItemLance extends LOTRItemPolearmLong {
	public static UUID lanceSpeedBoost_id = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
	public static AttributeModifier lanceSpeedBoost = new AttributeModifier(lanceSpeedBoost_id, "Lance speed boost", -0.2, 2).setSaved(false);

	public LOTRItemLance(Item.ToolMaterial material) {
		super(material);
	}

	public LOTRItemLance(LOTRMaterial material) {
		this(material.toToolMaterial());
	}
}
