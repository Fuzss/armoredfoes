package fuzs.armoredfoes.client;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.armoredfoes.client.model.ArmorModels;
import fuzs.armoredfoes.client.model.geom.ModModelLayers;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.event.v1.renderer.AddLivingEntityRenderLayersCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ExtractRenderStateCallback;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;

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
        LayerDefinition humanoidInnerArmor = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION),
                64,
                32);
        LayerDefinition humanoidOuterArmor = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION),
                64,
                32);
        MeshTransformer villagerScaling = MeshTransformer.scaling(0.9375F);
        LayerDefinition illagerInnerArmor = ArmorModels.createInnerArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        LayerDefinition illagerOuterArmor = ArmorModels.createArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        LayerDefinition villagerInnerArmor = ArmorModels.createInnerVillagerArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        LayerDefinition villagerOuterArmor = ArmorModels.createVillagerArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        LayerDefinition witchInnerArmor = ArmorModels.createInnerWitchArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        LayerDefinition witchOuterArmor = ArmorModels.createWitchArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION)
                .apply(villagerScaling);
        context.registerLayerDefinition(ModModelLayers.HUMANOID_INNER_ARMOR, () -> humanoidInnerArmor);
        context.registerLayerDefinition(ModModelLayers.HUMANOID_OUTER_ARMOR, () -> humanoidOuterArmor);
        context.registerLayerDefinition(ModModelLayers.HUMANOID_BABY_INNER_ARMOR,
                () -> humanoidInnerArmor.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.HUMANOID_BABY_OUTER_ARMOR,
                () -> humanoidOuterArmor.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.ILLAGER_INNER_ARMOR, () -> illagerInnerArmor);
        context.registerLayerDefinition(ModModelLayers.ILLAGER_OUTER_ARMOR, () -> illagerOuterArmor);
        context.registerLayerDefinition(ModModelLayers.ILLAGER_BABY_INNER_ARMOR,
                () -> illagerInnerArmor.apply(VillagerModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.ILLAGER_BABY_OUTER_ARMOR,
                () -> illagerOuterArmor.apply(VillagerModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.VILLAGER_INNER_ARMOR, () -> villagerInnerArmor);
        context.registerLayerDefinition(ModModelLayers.VILLAGER_OUTER_ARMOR, () -> villagerOuterArmor);
        context.registerLayerDefinition(ModModelLayers.VILLAGER_BABY_INNER_ARMOR,
                () -> villagerInnerArmor.apply(VillagerModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.VILLAGER_BABY_OUTER_ARMOR,
                () -> villagerOuterArmor.apply(VillagerModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.WITCH_INNER_ARMOR, () -> witchInnerArmor);
        context.registerLayerDefinition(ModModelLayers.WITCH_OUTER_ARMOR, () -> witchOuterArmor);
        context.registerLayerDefinition(ModModelLayers.WITCH_BABY_INNER_ARMOR,
                () -> witchInnerArmor.apply(VillagerModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.WITCH_BABY_OUTER_ARMOR,
                () -> witchOuterArmor.apply(VillagerModel.BABY_TRANSFORMER));
    }
}
