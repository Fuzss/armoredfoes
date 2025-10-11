package fuzs.armoredfoes.client.handler;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armoredfoes.ArmoredFoes;
import fuzs.armoredfoes.client.model.geom.ModModelLayers;
import fuzs.armoredfoes.client.renderer.entity.layers.IllagerArmorLayer;
import fuzs.armoredfoes.client.renderer.entity.layers.VillagerArmorLayer;
import fuzs.armoredfoes.client.renderer.entity.layers.WitchArmorLayer;
import fuzs.armoredfoes.init.ModRegistry;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class EquipmentRenderingHandler {
    public static final RenderPropertyKey<Map<EquipmentSlot, ItemStack>> ARMOR_EQUIPMENT_RENDER_PROPERTY = new RenderPropertyKey<>(
            ArmoredFoes.id("armor_equipment"));

    public static void onExtractRenderState(Entity entity, EntityRenderState renderState, float partialTick) {
        if (!(renderState instanceof HumanoidRenderState) && entity instanceof LivingEntity livingEntity
                && entity.getType().is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
            ImmutableMap.Builder<EquipmentSlot, ItemStack> builder = ImmutableMap.builder();
            for (EquipmentSlot equipmentSlot : EquipmentSlotGroup.ARMOR) {
                builder.put(equipmentSlot, HumanoidMobRenderer.getEquipmentIfRenderable(livingEntity, equipmentSlot));
            }
            RenderPropertyKey.set(renderState, ARMOR_EQUIPMENT_RENDER_PROPERTY, builder.build());
        }
    }

    @SuppressWarnings("unchecked")
    public static void addLivingEntityRenderLayers(EntityType<?> entityType, LivingEntityRenderer<?, ?, ?> entityRenderer, EntityRendererProvider.Context context) {
        if (entityRenderer.getModel() instanceof IllagerModel<?>) {
            ((LivingEntityRenderer<?, IllagerRenderState, IllagerModel<IllagerRenderState>>) entityRenderer).addLayer(
                    new IllagerArmorLayer<>((LivingEntityRenderer<?, IllagerRenderState, IllagerModel<IllagerRenderState>>) entityRenderer,
                            new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_INNER_ARMOR)),
                            new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_OUTER_ARMOR)),
                            new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_INNER_ARMOR)),
                            new IllagerModel<>(context.bakeLayer(ModModelLayers.ILLAGER_BABY_OUTER_ARMOR)),
                            context.getEquipmentRenderer()) {
                        @Override
                        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, IllagerRenderState renderState, float yRot, float xRot) {
                            if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                                super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                            }
                        }
                    });
        } else if (entityRenderer.getModel() instanceof WitchModel) {
            ((LivingEntityRenderer<?, WitchRenderState, WitchModel>) entityRenderer).addLayer(new WitchArmorLayer((LivingEntityRenderer<?, WitchRenderState, WitchModel>) entityRenderer,
                    new WitchModel(context.bakeLayer(ModModelLayers.WITCH_INNER_ARMOR)),
                    new WitchModel(context.bakeLayer(ModModelLayers.WITCH_OUTER_ARMOR)),
                    new WitchModel(context.bakeLayer(ModModelLayers.WITCH_BABY_INNER_ARMOR)),
                    new WitchModel(context.bakeLayer(ModModelLayers.WITCH_BABY_OUTER_ARMOR)),
                    context.getEquipmentRenderer()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, WitchRenderState renderState, float yRot, float xRot) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                        super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                    }
                }
            });
        } else if (entityRenderer.getModel() instanceof VillagerModel) {
            ((LivingEntityRenderer<?, VillagerRenderState, VillagerModel>) entityRenderer).addLayer(new VillagerArmorLayer(
                    (LivingEntityRenderer<?, VillagerRenderState, VillagerModel>) entityRenderer,
                    new VillagerModel(context.bakeLayer(ModModelLayers.VILLAGER_INNER_ARMOR)),
                    new VillagerModel(context.bakeLayer(ModModelLayers.VILLAGER_OUTER_ARMOR)),
                    new VillagerModel(context.bakeLayer(ModModelLayers.VILLAGER_BABY_INNER_ARMOR)),
                    new VillagerModel(context.bakeLayer(ModModelLayers.VILLAGER_BABY_OUTER_ARMOR)),
                    context.getEquipmentRenderer()) {
                @Override
                public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, VillagerRenderState renderState, float yRot, float xRot) {
                    if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                        super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                    }
                }
            });
        } else if (entityRenderer.getModel() instanceof HumanoidModel<?>) {
            ((LivingEntityRenderer<?, HumanoidRenderState, HumanoidModel<HumanoidRenderState>>) entityRenderer).addLayer(
                    new HumanoidArmorLayer<>((LivingEntityRenderer<?, HumanoidRenderState, HumanoidModel<HumanoidRenderState>>) entityRenderer,
                            new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_INNER_ARMOR)),
                            new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_OUTER_ARMOR)),
                            new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_BABY_INNER_ARMOR)),
                            new HumanoidArmorModel<>(context.bakeLayer(ModModelLayers.HUMANOID_BABY_OUTER_ARMOR)),
                            context.getEquipmentRenderer()) {
                        @Override
                        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HumanoidRenderState renderState, float yRot, float xRot) {
                            if (entityType.is(ModRegistry.SHOWS_WORN_ARMOR_ENTITY_TAG)) {
                                super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                            }
                        }
                    });
        }
    }
}
