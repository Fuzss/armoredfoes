package fuzs.armoredfoes.client;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.armoredfoes.client.model.LivingArmorModel;
import fuzs.armoredfoes.client.model.geom.ModModelLayers;
import fuzs.armoredfoes.client.model.geom.builders.LayerDefinition;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.LayerDefinitions;

public class ArmoredFoesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        AddLivingEntityRenderLayersCallback.EVENT.register(EquipmentRenderingHandler::addLivingEntityRenderLayers);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayers.HUMANOID_INNER_ARMOR, () -> {
            return LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION),
                    64,
                    32);
        });
        context.registerLayerDefinition(ModModelLayers.HUMANOID_OUTER_ARMOR, () -> {
            return LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION),
                    64,
                    32);
        });
        context.registerLayerDefinition(ModModelLayers.ILLAGER_INNER_ARMOR, () -> {
            return LivingArmorModel.createInnerArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION);
        });
        context.registerLayerDefinition(ModModelLayers.ILLAGER_OUTER_ARMOR, () -> {
            return LivingArmorModel.createArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION);
        });
        context.registerLayerDefinition(ModModelLayers.VILLAGER_INNER_ARMOR, () -> {
            return LivingArmorModel.createInnerVillagerArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION);
        });
        context.registerLayerDefinition(ModModelLayers.VILLAGER_OUTER_ARMOR, () -> {
            return LivingArmorModel.createVillagerArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION);
        });
        context.registerLayerDefinition(ModModelLayers.WITCH_INNER_ARMOR, () -> {
            return LivingArmorModel.createInnerWitchArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION);
        });
        context.registerLayerDefinition(ModModelLayers.WITCH_OUTER_ARMOR, () -> {
            return LivingArmorModel.createWitchArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION);
        });
    }
}
