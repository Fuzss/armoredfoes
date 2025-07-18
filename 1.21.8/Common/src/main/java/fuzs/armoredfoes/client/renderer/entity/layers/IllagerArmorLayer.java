package fuzs.armoredfoes.client.renderer.entity.layers;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class IllagerArmorLayer<S extends IllagerRenderState> extends ArmorLayer<S, IllagerModel<S>, IllagerModel<S>> {

    public IllagerArmorLayer(RenderLayerParent<S, IllagerModel<S>> renderer, IllagerModel<S> innerModel, IllagerModel<S> outerModel, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, innerModel, outerModel, equipmentRenderer);
    }

    public IllagerArmorLayer(RenderLayerParent<S, IllagerModel<S>> renderer, IllagerModel<S> innerModel, IllagerModel<S> outerModel, IllagerModel<S> innerModelBaby, IllagerModel<S> outerModelBaby, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, innerModel, outerModel, innerModelBaby, outerModelBaby, equipmentRenderer);
    }

    @Override
    protected ItemStack getEquipmentItem(S renderState, EquipmentSlot equipmentSlot) {
        return RenderPropertyKey.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_RENDER_PROPERTY,
                Collections.emptyMap()).getOrDefault(equipmentSlot, ItemStack.EMPTY);
    }

    @Override
    protected void setAllVisible(IllagerModel<S> model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        this.getBody(model).visible = visible;
        model.arms.visible = visible;
        model.rightArm.visible = visible;
        model.leftArm.visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    @Override
    protected void setPartVisibility(S renderState, IllagerModel<S> model, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                this.getBody(model).visible = true;
                model.arms.visible = renderState.armPose == AbstractIllager.IllagerArmPose.CROSSED;
                model.rightArm.visible = !model.arms.visible;
                model.leftArm.visible = !model.arms.visible;
                break;
            case LEGS:
                this.getBody(model).visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }
    }

    private ModelPart getBody(IllagerModel<S> model) {
        return model.root().getChild("body");
    }
}
