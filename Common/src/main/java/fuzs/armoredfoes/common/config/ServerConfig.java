package fuzs.armoredfoes.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import net.minecraft.world.entity.EquipmentSlotGroup;

public class ServerConfig implements ConfigCore {
    @Config(description = "Equipment slots to clear built-in equipment from before applying equipment from loot table.")
    public EquipmentSlotGroup clearedEquipmentSlots = EquipmentSlotGroup.ARMOR;
}
