package net.hypejet.jet.data.generator.generators;

import net.hypejet.jet.data.codecs.JetDataJson;
import net.hypejet.jet.data.generator.Generator;
import net.hypejet.jet.data.generator.constant.ConstantContainer;
import net.hypejet.jet.data.generator.util.RegistryUtil;
import net.hypejet.jet.data.model.api.event.GameEvent;
import net.hypejet.jet.data.model.server.registry.registries.registry.DataRegistryEntry;
import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Represents a {@linkplain Generator generator} of {@linkplain GameEvent game events}.
 *
 * @since 1.0
 * @author Codestech
 * @see Generator
 * @see GameEvent
 */
public final class GameEventGenerator extends Generator<GameEvent> {

    private final RegistryAccess registryAccess;

    /**
     * Constructs the {@linkplain GameEventGenerator game event generator}.
     *
     * @param registryAccess access to all Minecraft registries
     * @since 1.0
     */
    public GameEventGenerator(@NonNull RegistryAccess registryAccess) {
        super(new GeneratorName("Game", "Event", "Generator"),
                new ResourceFileSettings("game-events", JetDataJson.createGameEventsGson()),
                new JavaFileSettings(ConstantContainer.JavaFileDestination.API, "GameEvents"));
        this.registryAccess = NullabilityUtil.requireNonNull(registryAccess, "registry access");
    }

    @Override
    public @NonNull List<DataRegistryEntry<GameEvent>> generate() {
        return RegistryUtil.createEntries(this.registryAccess.lookupOrThrow(Registries.GAME_EVENT), gameEvent ->
                new GameEvent(gameEvent.notificationRadius())
        );
    }
}