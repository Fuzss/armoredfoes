package fuzs.armoredfoes.client.renderer.entity.layers;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.WitchRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class WitchArmorLayer extends LivingArmorLayer<WitchRenderState, WitchModel, WitchModel> {

    public WitchArmorLayer(RenderLayerParent<WitchRenderState, WitchModel> renderer, ArmorModelSet<WitchModel> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, equipmentRenderer);
    }

    public WitchArmorLayer(RenderLayerParent<WitchRenderState, WitchModel> renderer, ArmorModelSet<WitchModel> modelSet, ArmorModelSet<WitchModel> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, babyModelSet, equipmentRenderer);
    }

    @Override
    protected ItemStack getItem(WitchRenderState renderState, EquipmentSlot slot) {
        return RenderStateExtraData.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_KEY,
                Collections.emptyMap()).getOrDefault(slot, ItemStack.EMPTY);
    }
}
