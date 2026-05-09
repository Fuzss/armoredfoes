package fuzs.armoredfoes.common.client;

import fuzs.armoredfoes.common.client.handler.EquipmentRenderingHandler;
import fuzs.armoredfoes.common.client.model.LivingArmorModel;
import fuzs.armoredfoes.common.client.model.geom.ModModelLayers;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ArmoredFoesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractEntityRenderStateCallback.EVENT.register(EquipmentRenderingHandler::onExtractRenderState);
        AddLivingEntityRenderLayersCallback.EVENT.register(EquipmentRenderingHandler::addLivingEntityRenderLayers);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerArmorDefinition(ModModelLayers.HUMANOID_ARMOR,
                LivingArmorModel::createHumanoidAdultArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.HUMANOID_BABY_ARMOR,
                LivingArmorModel::createHumanoidBabyArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.ILLAGER_ARMOR, LivingArmorModel::createIllagerArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.ILLAGER_BABY_ARMOR,
                () -> LivingArmorModel.createIllagerArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.ILLAGER_CROSSED_ARMOR,
                LivingArmorModel::createArmedAdultArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.ILLAGER_BABY_CROSSED_ARMOR,
                () -> LivingArmorModel.createArmedAdultArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
        context.registerArmorDefinition(ModModelLayers.VILLAGER_ARMOR, LivingArmorModel::createArmedAdultArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.VILLAGER_BABY_ARMOR,
                LivingArmorModel::createArmedBabyArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.WITCH_ARMOR, LivingArmorModel::createWitchArmorLayerSet);
        context.registerArmorDefinition(ModModelLayers.WITCH_BABY_ARMOR,
                () -> LivingArmorModel.createWitchArmorLayerSet().map((LayerDefinition layerDefinition) -> {
                    return layerDefinition.apply(HumanoidModel.BABY_TRANSFORMER);
                }));
    }
}
