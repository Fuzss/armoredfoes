package fuzs.armoredfoes.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import net.minecraft.world.entity.EquipmentSlotGroup;

public class ServerConfig implements ConfigCore {
    @Config(description = "Equipment slots to clear built-in equipment from before applying equipment from loot table.")
    public EquipmentSlotGroup clearedEquipmentSlots = EquipmentSlotGroup.ARMOR;
    @Config(description = {
            "Do not put equipment on monsters spawned via the /summon command.",
            "This option serves as a safety precaution, as there is no way for the mod to determine if any specific data was specified like custom equipment which should not be overridden."
    })
    public boolean ignoreSummonedMobs = true;
}
