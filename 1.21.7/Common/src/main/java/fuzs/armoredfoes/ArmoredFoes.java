package fuzs.armoredfoes;

import fuzs.armoredfoes.handler.SpawnEquipmentHandler;
import fuzs.armoredfoes.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmoredFoes implements ModConstructor {
    public static final String MOD_ID = "armoredfoes";
    public static final String MOD_NAME = "Armored Foes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ServerEntityLevelEvents.LOAD.register(SpawnEquipmentHandler::onEntityLoad);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
