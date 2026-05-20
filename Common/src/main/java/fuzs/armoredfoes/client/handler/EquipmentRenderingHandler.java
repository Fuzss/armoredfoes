package fuzs.armoredfoes.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armoredfoes.client.model.geom.ModModelLayers;
import fuzs.armoredfoes.client.renderer.entity.layers.IllagerArmorLayer;
import fuzs.armoredfoes.client.renderer.entity.layers.VillagerArmorLayer;
import fuzs.armoredfoes.init.ModRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;

public class EquipmentRenderingHandler {

    @SuppressWarnings("unchecked")
    public static void addLivingEntityRenderLayers(EntityType<?> entityType, LivingEntityRenderer<?, ?> entityRenderer, EntityRendererProvider.Context context) {
        if (entityRenderer.getModel() instanceof IllagerModel<?>) {
            ((LivingEntityRenderer<AbstractIllager, IllagerModel<AbstractIllager>>) entityRenderer).addLayer(new IllagerArmorLayer<>(
                    (LivingEntityRenderer<AbstractIllager, IllagerModel<AbstractIllager>>) entityRenderer,
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_INNER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_OUTER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_INNER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_OUTER_ARMOR)),
                    context.getModelManager()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, AbstractIllager entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)
                            && entity.getArmPose() == AbstractIllager.IllagerArmPose.CROSSED) {
                        super.render(poseStack,
                                bufferSource,
                                lightCoords,
                                entity,
                                limbSwing,
                                limbSwingAmount,
                                partialTick,
                                ageInTicks,
                                netHeadYaw,
                                headPitch);
                    }
                }
            });
            ((LivingEntityRenderer<AbstractIllager, IllagerModel<AbstractIllager>>) entityRenderer).addLayer(new IllagerArmorLayer<>(
                    (LivingEntityRenderer<AbstractIllager, IllagerModel<AbstractIllager>>) entityRenderer,
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_INNER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_OUTER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_INNER_ARMOR)),
                    new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_OUTER_ARMOR)),
                    context.getModelManager()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, AbstractIllager entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)
                            && entity.getArmPose() != AbstractIllager.IllagerArmPose.CROSSED) {
                        super.render(poseStack,
                                bufferSource,
                                lightCoords,
                                entity,
                                limbSwing,
                                limbSwingAmount,
                                partialTick,
                                ageInTicks,
                                netHeadYaw,
                                headPitch);
                    }
                }
            });
        } else if (entityRenderer.getModel() instanceof WitchModel<?>) {
            ((LivingEntityRenderer<LivingEntity, WitchModel<LivingEntity>>) entityRenderer).addLayer(new VillagerArmorLayer<>(
                    (LivingEntityRenderer<LivingEntity, WitchModel<LivingEntity>>) entityRenderer,
                    new WitchModel<>(context.bakeLayer(ModModelLayers.WITCH_INNER_ARMOR)),
                    new WitchModel<>(context.bakeLayer(ModModelLayers.WITCH_OUTER_ARMOR)),
                    new WitchModel<>(context.bakeLayer(ModModelLayers.WITCH_BABY_INNER_ARMOR)),
                    new WitchModel<>(context.bakeLayer(ModModelLayers.WITCH_BABY_OUTER_ARMOR)),
                    context.getModelManager()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                        super.render(poseStack,
                                bufferSource,
                                lightCoords,
                                entity,
                                limbSwing,
                                limbSwingAmount,
                                partialTick,
                                ageInTicks,
                                netHeadYaw,
                                headPitch);
                    }
                }
            });
        } else if (entityRenderer.getModel() instanceof VillagerModel<?>) {
            ((LivingEntityRenderer<LivingEntity, VillagerModel<LivingEntity>>) entityRenderer).addLayer(new VillagerArmorLayer<>(
                    (LivingEntityRenderer<LivingEntity, VillagerModel<LivingEntity>>) entityRenderer,
                    new VillagerModel<>(context.bakeLayer(ModModelLayers.VILLAGER_INNER_ARMOR)),
                    new VillagerModel<>(context.bakeLayer(ModModelLayers.VILLAGER_OUTER_ARMOR)),
                    new VillagerModel<>(context.bakeLayer(ModModelLayers.VILLAGER_BABY_INNER_ARMOR)),
                    new VillagerModel<>(context.bakeLayer(ModModelLayers.VILLAGER_BABY_OUTER_ARMOR)),
                    context.getModelManager()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int lightCoords, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                        super.render(poseStack,
                                bufferSource,
                                lightCoords,
                                entity,
                                limbSwing,
                                limbSwingAmount,
                                partialTick,
                                ageInTicks,
                                netHeadYaw,
                                headPitch);
                    }
                }
            });
        } else if (entityRenderer.getModel() instanceof HumanoidModel<?>) {
            ((LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>>) entityRenderer).addLayer(new HumanoidArmorLayer<>(
                    (LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>>) entityRenderer,
                    new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_INNER_ARMOR)),
                    new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_OUTER_ARMOR)),
                    context.getModelManager()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                        super.render(poseStack,
                                buffer,
                                packedLight,
                                livingEntity,
                                limbSwing,
                                limbSwingAmount,
                                partialTicks,
                                ageInTicks,
                                netHeadYaw,
                                headPitch);
                    }
                }
            });
        }
    }
}
