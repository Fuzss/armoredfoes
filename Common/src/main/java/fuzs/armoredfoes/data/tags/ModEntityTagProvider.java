package fuzs.armoredfoes.data.tags;

import fuzs.armoredfoes.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class ModEntityTagProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTagProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        this.tag(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)
                .add(EntityType.EVOKER,
                        EntityType.VINDICATOR,
                        EntityType.ILLUSIONER,
                        EntityType.PILLAGER,
                        EntityType.WITCH,
                        EntityType.VILLAGER,
                        EntityType.WANDERING_TRADER);
        // Piglins have some unique armour spawn rules in vanilla; we replace that with the same rules used by all other mobs.
        this.tag(ModRegistry.DISCARDS_ORIGINAL_EQUIPMENT_ENTITY_TAG).add(EntityType.PIGLIN);
    }
}
