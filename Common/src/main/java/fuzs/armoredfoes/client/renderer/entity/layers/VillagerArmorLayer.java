package fuzs.armoredfoes.client.renderer.entity.layers;

import fuzs.armoredfoes.client.handler.EquipmentRenderingHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class VillagerArmorLayer extends LivingArmorLayer<VillagerRenderState, VillagerModel, VillagerModel> {

    public VillagerArmorLayer(RenderLayerParent<VillagerRenderState, VillagerModel> renderer, ArmorModelSet<VillagerModel> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, equipmentRenderer);
    }

    public VillagerArmorLayer(RenderLayerParent<VillagerRenderState, VillagerModel> renderer, ArmorModelSet<VillagerModel> modelSet, ArmorModelSet<VillagerModel> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, babyModelSet, equipmentRenderer);
    }

    @Override
    protected ItemStack getItem(VillagerRenderState renderState, EquipmentSlot slot) {
        return RenderStateExtraData.getOrDefault(renderState,
                EquipmentRenderingHandler.ARMOR_EQUIPMENT_KEY,
                Collections.emptyMap()).getOrDefault(slot, ItemStack.EMPTY);
    }
}
