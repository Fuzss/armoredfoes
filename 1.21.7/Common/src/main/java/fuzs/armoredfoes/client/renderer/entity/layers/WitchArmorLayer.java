package fuzs.armoredfoes.client.renderer.entity.layers;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.WitchRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class WitchArmorLayer extends ArmorLayer<WitchRenderState, WitchModel, WitchModel> {

    public WitchArmorLayer(RenderLayerParent<WitchRenderState, WitchModel> renderer, WitchModel innerModel, WitchModel outerModel, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, innerModel, outerModel, equipmentRenderer);
    }

    public WitchArmorLayer(RenderLayerParent<WitchRenderState, WitchModel> renderer, WitchModel innerModel, WitchModel outerModel, WitchModel innerModelBaby, WitchModel outerModelBaby, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, innerModel, outerModel, innerModelBaby, outerModelBaby, equipmentRenderer);
    }

    @Override
    protected ItemStack getEquipmentItem(WitchRenderState renderState, EquipmentSlot equipmentSlot) {
        return RenderPropertyKey.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_RENDER_PROPERTY,
                Collections.emptyMap()).getOrDefault(equipmentSlot, ItemStack.EMPTY);
    }

    @Override
    protected void setAllVisible(WitchModel model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        this.getBody(model).visible = visible;
        model.arms.visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    @Override
    protected void setPartVisibility(WitchRenderState renderState, WitchModel model, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                this.getBody(model).visible = true;
                model.arms.visible = true;
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

    private ModelPart getBody(WitchModel model) {
        return model.root().getChild("body");
    }
}
