package fuzs.armoredfoes.client;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.armoredfoes.client.model.LivingArmorModel;
import fuzs.armoredfoes.client.model.geom.ModModelLayers;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ExtractRenderStateCallback;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ArmoredFoesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractRenderStateCallback.EVENT.register(EquipmentRenderingHandler::onExtractRenderState);
        AddLivingEntityRenderLayersCallback.EVENT.register(EquipmentRenderingHandler::addLivingEntityRenderLayers);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerArmorDefinition(ModModelLayers.HUMANOID_ARMOR, LivingArmorModel::createHumanoidArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.HUMANOID_BABY_ARMOR,
                () -> LivingArmorModel.createHumanoidArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.ILLAGER_ARMOR, LivingArmorModel::createIllagerArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.ILLAGER_BABY_ARMOR,
                () -> LivingArmorModel.createIllagerArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.ILLAGER_CROSSED_ARMOR,
                LivingArmorModel::createArmedArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.ILLAGER_BABY_CROSSED_ARMOR,
                () -> LivingArmorModel.createArmedArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.VILLAGER_ARMOR, LivingArmorModel::createArmedArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.VILLAGER_BABY_ARMOR,
                () -> LivingArmorModel.createArmedArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.WITCH_ARMOR, LivingArmorModel::createWitchArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.WITCH_BABY_ARMOR,
                () -> LivingArmorModel.createWitchArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
    }
}
