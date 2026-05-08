plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-neoforge")
}

dependencies {
    modCompileOnly(sharedLibs.puzzleslib.common)
    modApi(sharedLibs.puzzleslib.neoforge)
}

multiloader {
    modFile {
        toml {
            extraProperties("lithium:options", mapOf("mixin.world.chunk_access" to false))
        }
    }
}
