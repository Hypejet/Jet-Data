package net.hypejet.jet.data.model.registry.registries.wolf;

import net.hypejet.jet.data.model.utils.NullabilityUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a single biome or set of biomes in which wolves can spawn.
 *
 * @since 1.0
 * @author Codestech
 * @see WolfVariant
 */
public sealed interface WolfBiomes {
    /**
     * Represents a {@linkplain WolfBiomes wolf biomes} containing a single biome that should be allowed for wolves
     * to spawn on.
     *
     * @param key an identifier of the biome
     * @since 1.0
     * @author Codestech
     * @see WolfBiomes
     */
    record SingleBiome(@NonNull Key key) implements WolfBiomes, Keyed {
        /**
         * Constructs the {@linkplain SingleBiome single biome}.
         *
         * @param key an identifier of the biome
         * @since 1.0
         */
        public SingleBiome {
            NullabilityUtil.requireNonNull(key, "key");
        }
    }

    /**
     * Represents a {@linkplain WolfBiomes wolf biomes} containing a tag of biomes that should be allowed for wolves
     * to spawn on.
     *
     * @param key an un-hashed identifier of the tag
     * @since 1.0
     * @author Codestech
     * @see WolfBiomes
     */
    record TaggedBiomes(@NonNull Key key) implements WolfBiomes, Keyed {
        /**
         * Constructs the {@linkplain TaggedBiomes tagged biomes}.
         *
         * @param key an un-hashed identifier of the tag
         * @since 1.0
         */
        public TaggedBiomes {
            NullabilityUtil.requireNonNull(key, "key");
        }
    }

    /**
     * Represents a {@linkplain WolfBiomes wolf biomes} containing a {@linkplain Collection collection}
     * of biomes that should be allowed for wolves to spawn on.
     *
     * @param keys the collection
     * @since 1.0
     * @author codestech
     * @see WolfBiomes
     */
    record Biomes(@NonNull Collection<Key> keys) implements WolfBiomes {
        /**
         * Constructs the {@linkplain Biomes biomes}.
         *
         * @param keys the collection
         * @since 1.0
         */
        public Biomes {
            keys = Set.copyOf(NullabilityUtil.requireNonNull(keys, "keys"));
        }
    }
}