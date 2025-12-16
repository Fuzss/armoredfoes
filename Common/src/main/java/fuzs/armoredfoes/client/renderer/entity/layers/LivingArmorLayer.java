package fuzs.armoredfoes.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo.LayerType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

import java.util.Collections;

/**
 * @see HumanoidArmorLayer
 */
public class LivingArmorLayer<S extends LivingEntityRenderState, M extends EntityModel<S>, A extends EntityModel<S>> extends RenderLayer<S, M> {
    private final ArmorModelSet<A> modelSet;
    private final ArmorModelSet<A> babyModelSet;
    private final EquipmentLayerRenderer equipmentRenderer;

    public LivingArmorLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        this(renderer, modelSet, modelSet, equipmentRenderer);
    }

    public LivingArmorLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, ArmorModelSet<A> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.modelSet = modelSet;
        this.babyModelSet = babyModelSet;
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, S renderState, float yRot, float xRot) {
        this.renderArmorPiece(poseStack, nodeCollector, EquipmentSlot.CHEST, packedLight, renderState);
        this.renderArmorPiece(poseStack, nodeCollector, EquipmentSlot.LEGS, packedLight, renderState);
        this.renderArmorPiece(poseStack, nodeCollector, EquipmentSlot.FEET, packedLight, renderState);
        this.renderArmorPiece(poseStack, nodeCollector, EquipmentSlot.HEAD, packedLight, renderState);
    }

    private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector nodeCollector, EquipmentSlot slot, int packedLight, S renderState) {
        ItemStack item = this.getItem(renderState, slot);
        Equippable equippable = item.get(DataComponents.EQUIPPABLE);
        if (equippable != null && HumanoidArmorLayer.shouldRender(equippable, slot)) {
            A humanoidModel = this.getArmorModel(renderState, slot);
            LayerType layerType = this.usesInnerModel(slot) ? LayerType.HUMANOID_LEGGINGS : LayerType.HUMANOID;
            this.equipmentRenderer.renderLayers(layerType,
                    equippable.assetId().orElseThrow(),
                    humanoidModel,
                    renderState,
                    item,
                    poseStack,
                    nodeCollector,
                    packedLight,
                    renderState.outlineColor);
        }
    }

    private ItemStack getItem(S renderState, EquipmentSlot slot) {
        return RenderStateExtraData.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_KEY,
                Collections.emptyMap()).getOrDefault(slot, ItemStack.EMPTY);
    }

    private A getArmorModel(S renderState, EquipmentSlot slot) {
        return (renderState.isBaby ? this.babyModelSet : this.modelSet).get(slot);
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
