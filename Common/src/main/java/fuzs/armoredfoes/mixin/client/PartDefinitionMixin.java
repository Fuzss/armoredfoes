package fuzs.armoredfoes.mixin.client;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(PartDefinition.class)
abstract class PartDefinitionMixin {

    @ModifyArg(method = "<init>(Lnet/minecraft/client/model/geom/builders/PartDefinition;)V",
               at = @At(value = "INVOKE", target = "Ljava/util/Map;putAll(Ljava/util/Map;)V"))
    public Map<String, net.minecraft.client.model.geom.builders.PartDefinition> init(Map<String, net.minecraft.client.model.geom.builders.PartDefinition> children) {
        return Maps.transformValues(children, PartDefinition::new);
    }

    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/model/geom/builders/PartDefinition;)V",
                           at = @At(value = "FIELD",
                                    target = "Lnet/minecraft/client/model/geom/builders/PartDefinition;partPose:Lnet/minecraft/client/model/geom/PartPose;",
                                    opcode = Opcodes.GETFIELD))
    private static PartPose init(PartPose partPose) {
        return new fuzs.puzzleslib.api.client.renderer.v1.model.geom.PartPose(partPose);
    }
}
