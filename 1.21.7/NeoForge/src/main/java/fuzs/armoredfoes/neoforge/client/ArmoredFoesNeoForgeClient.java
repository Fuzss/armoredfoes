package fuzs.armoredfoes.neoforge.client;

import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.client.ArmoredFoesClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ArmoredFoes.MOD_ID, dist = Dist.CLIENT)
public class ArmoredFoesNeoForgeClient {

    public ArmoredFoesNeoForgeClient() {
        ClientModConstructor.construct(ArmoredFoes.MOD_ID, ArmoredFoesClient::new);
    }
}
