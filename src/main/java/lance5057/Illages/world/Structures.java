package lance5057.Illages.world;

import java.util.Locale;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lance5057.Illages.Reference;
import lance5057.Illages.world.structures.IllageStructure;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Structures {
    // Registry
    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister
	    .create(ForgeRegistries.STRUCTURE_FEATURES, Reference.MOD_ID);

    private static <T extends Structure<?>> RegistryObject<T> registerStructure(String name, Supplier<T> structure) {
	return DEFERRED_REGISTRY_STRUCTURE.register(name, structure);
    }

    // Structures Start
    public static final RegistryObject<Structure<NoFeatureConfig>> ILLAGE = registerStructure("illage",
	    () -> (new IllageStructure(NoFeatureConfig.field_236558_a_)));

    public static void setupStructures() {
	setupMapSpacingAndLand(ILLAGE.get(), new StructureSeparationSettings(32, 8, 50575057), false);

    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure,
	    StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {

	Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

	if (transformSurroundingLand) {
	    Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_)
		    .add(structure).build();
	}

	DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
		.putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings)
		.build();
    }

    public static void register(IEventBus modBus) {
	DEFERRED_REGISTRY_STRUCTURE.register(modBus);
    }
}
