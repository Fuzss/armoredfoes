package fuzs.armoredfoes.fabric.client;

import fuzs.armoredfoes.common.ArmoredFoes;
import fuzs.armoredfoes.common.client.ArmoredFoesClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ArmoredFoesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoesClient::new);
    }
}
