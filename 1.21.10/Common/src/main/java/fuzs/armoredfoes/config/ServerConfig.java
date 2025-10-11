package fuzs.armoredfoes.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import net.minecraft.world.entity.EquipmentSlotGroup;

public class ServerConfig implements ConfigCore {
    @Config(description = "Equipment slots to clear built-in equipment from before applying equipment from loot table.")
    public EquipmentSlotGroup slotsToClearBeforeApplying = EquipmentSlotGroup.ARMOR;
}
