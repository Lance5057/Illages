package lance5057.Illages.world;

import lance5057.Illages.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_ILLAGE = Structures.ILLAGE.get()
	    .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
	Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
	Registry.register(registry, new ResourceLocation(Reference.MOD_ID, "configured_illage"),
		CONFIGURED_ILLAGE);
	FlatGenerationSettings.STRUCTURES.put(Structures.ILLAGE.get(), CONFIGURED_ILLAGE);
    }
}
