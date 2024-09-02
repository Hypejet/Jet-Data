package net.hypejet.jet.data.generator.util;

import net.hypejet.jet.data.generator.adapter.IdentifierAdapter;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a utility for managing {@linkplain Registry Minecraft registries}.
 *
 * @since 1.0
 * @author Codestech
 * @see Registry
 */
public final class RegistryUtil {

    private RegistryUtil() {}

    /**
     * Gets a {@linkplain ResourceLocation resource location} of a {@linkplain Holder holder} specified
     * from a {@linkplain Registry registry}, then converts it to a {@linkplain Key key}.
     *
     * @param holder the holder
     * @param registry the registry
     * @return the key
     * @param <T> a type of the value that the holder contain
     * @since 1.0
     * @throws IllegalArgumentException if the registry does not contain a value from holder specified
     */
    public static <T> @NonNull Key keyOfHolder(@NonNull Holder<T> holder, @NonNull Registry<T> registry) {
        ResourceLocation biomeLocation = registry.getKey(holder.value());
        if (biomeLocation == null)
            throw new IllegalArgumentException("Could not find a resource location of a value from holder specified");
        return IdentifierAdapter.convert(biomeLocation);
    }
}