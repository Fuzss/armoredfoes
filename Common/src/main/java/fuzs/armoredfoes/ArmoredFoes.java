package fuzs.armoredfoes;

import fuzs.armoredfoes.config.ServerConfig;
import fuzs.armoredfoes.handler.SpawnEquipmentHandler;
import fuzs.armoredfoes.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.resources.Identifier;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmoredFoes implements ModConstructor {
    public static final String MOD_ID = "armoredfoes";
    public static final String MOD_NAME = "Armored Foes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ServerEntityLevelEvents.LOAD.register(SpawnEquipmentHandler::onEntityLoad);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
