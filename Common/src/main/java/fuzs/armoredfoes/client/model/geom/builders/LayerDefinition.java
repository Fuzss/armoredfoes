package fuzs.armoredfoes.client.model.geom.builders;

import fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class LayerDefinition extends net.minecraft.client.model.geom.builders.LayerDefinition {

    public LayerDefinition(net.minecraft.client.model.geom.builders.LayerDefinition other) {
        this(other.mesh, other.material);
    }

    public LayerDefinition(MeshDefinition mesh, MaterialDefinition material) {
        super(mesh, material);
    }

    public LayerDefinition apply(MeshTransformer transformer) {
        return new LayerDefinition(transformer.apply(new fuzs.puzzleslib.api.client.renderer.v1.model.geom.builders.MeshDefinition(
                this.mesh)), this.material);
    }

    public static LayerDefinition create(MeshDefinition mesh, int texWidth, int texHeight) {
        return new LayerDefinition(mesh, new MaterialDefinition(texWidth, texHeight));
    }
}
