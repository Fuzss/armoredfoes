package fuzs.armoredfoes.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
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

public abstract class ArmorLayer<S extends LivingEntityRenderState, M extends EntityModel<S>, A extends EntityModel<S>> extends RenderLayer<S, M> {
    private final A innerModel;
    private final A outerModel;
    private final A innerModelBaby;
    private final A outerModelBaby;
    private final EquipmentLayerRenderer equipmentRenderer;

    public ArmorLayer(RenderLayerParent<S, M> renderer, A innerModel, A outerModel, EquipmentLayerRenderer equipmentRenderer) {
        this(renderer, innerModel, outerModel, innerModel, outerModel, equipmentRenderer);
    }

    public ArmorLayer(RenderLayerParent<S, M> renderer, A innerModel, A outerModel, A innerModelBaby, A outerModelBaby, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.innerModelBaby = innerModelBaby;
        this.outerModelBaby = outerModelBaby;
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S renderState, float yRot, float xRot) {
        this.renderArmorPiece(poseStack,
                bufferSource,
                renderState,
                EquipmentSlot.CHEST,
                packedLight,
                this.getArmorModel(renderState, EquipmentSlot.CHEST));
        this.renderArmorPiece(poseStack,
                bufferSource,
                renderState,
                EquipmentSlot.LEGS,
                packedLight,
                this.getArmorModel(renderState, EquipmentSlot.LEGS));
        this.renderArmorPiece(poseStack,
                bufferSource,
                renderState,
                EquipmentSlot.FEET,
                packedLight,
                this.getArmorModel(renderState, EquipmentSlot.FEET));
        this.renderArmorPiece(poseStack,
                bufferSource,
                renderState,
                EquipmentSlot.HEAD,
                packedLight,
                this.getArmorModel(renderState, EquipmentSlot.HEAD));
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, S renderState, EquipmentSlot slot, int packedLight, A model) {
        ItemStack armorItem = this.getEquipmentItem(renderState, slot);
        Equippable equippable = armorItem.get(DataComponents.EQUIPPABLE);
        if (equippable != null && HumanoidArmorLayer.shouldRender(equippable, slot)) {
            model.setupAnim(renderState);
            this.setAllVisible(model, false);
            this.setPartVisibility(renderState, model, slot);
            LayerType layerType = this.usesInnerModel(slot) ? LayerType.HUMANOID_LEGGINGS : LayerType.HUMANOID;
            this.equipmentRenderer.renderLayers(layerType,
                    equippable.assetId().orElseThrow(),
                    model,
                    armorItem,
                    poseStack,
                    bufferSource,
                    packedLight);
        }
    }

    protected abstract ItemStack getEquipmentItem(S renderState, EquipmentSlot equipmentSlot);

    protected abstract void setAllVisible(A model, boolean visible);

    protected abstract void setPartVisibility(S renderState, A model, EquipmentSlot equipmentSlot);

    private A getArmorModel(S renderState, EquipmentSlot slot) {
        if (this.usesInnerModel(slot)) {
            return renderState.isBaby ? this.innerModelBaby : this.innerModel;
        } else {
            return renderState.isBaby ? this.outerModelBaby : this.outerModel;
        }
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
