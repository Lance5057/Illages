package lance5057.Illages;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import lance5057.Illages.world.ConfiguredStructures;
import lance5057.Illages.world.Structures;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MOD_ID)
public class Illages {
    public static Logger logger = LogManager.getLogger();

    public Illages() {
	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	Structures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
	modEventBus.addListener(this::modSetup);

	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
	forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

	forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    public void modSetup(final FMLCommonSetupEvent event) {
	event.enqueueWork(() -> {
	    Structures.setupStructures();
	    ConfiguredStructures.registerConfiguredStructures();

	    WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
		Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures()
			.func_236195_a_();

		if (structureMap instanceof ImmutableMap) {
		    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
		    tempMap.put(Structures.ILLAGE.get(),
			    DimensionStructuresSettings.field_236191_b_.get(Structures.ILLAGE.get()));
		    settings.getValue().getStructures().field_236193_d_ = tempMap;
		} else {
		    structureMap.put(Structures.ILLAGE.get(),
			    DimensionStructuresSettings.field_236191_b_.get(Structures.ILLAGE.get()));
		}
	    });
	});
    }

    public void biomeModification(final BiomeLoadingEvent event) {
	event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_ILLAGE);
    }

    public void addDimensionalSpacing(final WorldEvent.Load event) {
	if (event.getWorld() instanceof ServerWorld) {
	    ServerWorld serverWorld = (ServerWorld) event.getWorld();

	    if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator
		    && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
		return;
	    }

	    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(
		    serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());

	    tempMap.putIfAbsent(Structures.ILLAGE.get(),
		    DimensionStructuresSettings.field_236191_b_.get(Structures.ILLAGE.get()));
	    serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
	}
    }
}
