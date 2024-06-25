package net.hypejet.jet.data.generator.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a utility for managing {@linkplain ResourceLocation resource locations}.
 *
 * @since 1.0
 * @author Codestech
 * @see ResourceLocation
 */
public final class ResourceLocationUtil {

    private ResourceLocationUtil() {}

    /**
     * Gets a {@linkplain ResourceLocation resource location} of an object from a {@linkplain Registry registry}.
     *
     * @param registry the registry
     * @param object the object
     * @return the resource location
     * @param <T> a type of the object
     * @throws IllegalArgumentException if the object was not registered in the provided registry
     * @see Registry
     */
    public static <T> @NonNull ResourceLocation getOrThrow(@NonNull Registry<T> registry, @NonNull T object) {
        return registry.wrapAsHolder(object)
                .unwrapKey()
                .map(ResourceKey::location)
                .orElseThrow(() -> new IllegalArgumentException("The object was not registered in the provided " +
                        "registry"));
    }
}