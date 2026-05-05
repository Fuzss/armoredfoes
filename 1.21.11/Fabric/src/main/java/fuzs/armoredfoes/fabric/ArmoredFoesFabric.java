package fuzs.armoredfoes.fabric;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.fabric.world.entity.EntityLoadData;
import fuzs.armoredfoes.handler.SpawnEquipmentHandler;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ArmoredFoesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoes::new);
        ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerLevel serverLevel) -> {
            SpawnEquipmentHandler.onEntityLoad(entity,
                    serverLevel,
                    !((EntityLoadData) entity).armoredfoes$isLoadedFromDisk());
        });
    }
}
