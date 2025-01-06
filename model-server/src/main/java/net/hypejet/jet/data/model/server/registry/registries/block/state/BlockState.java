package net.hypejet.jet.data.model.server.registry.registries.block.state;

import net.hypejet.jet.data.model.api.utils.NullabilityUtil;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * Represents a state of a Minecraft block.
 *
 * @param properties a properties of the state
 * @param isAir whether the state should be recognised as an air
 * @since 1.0
 * @author Codestech
 * @see Keyed
 */
public record BlockState(@NonNull Map<String, String> properties, boolean isAir) {
    /**
     * Constructs the {@linkplain BlockState block state}.
     *
     * @param properties a properties of the state
     * @param isAir whether the state should be recognised as an air
     * @since 1.0
     */
    public BlockState {
        properties = Map.copyOf(NullabilityUtil.requireNonNull(properties, "properties"));
    }
}