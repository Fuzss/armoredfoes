package fuzs.armoredfoes.fabric.client;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.client.ArmoredFoesClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ArmoredFoesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoesClient::new);
    }
}
