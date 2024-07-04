package net.hypejet.jet.data;

/**
 * Represents a {@linkplain IdentifiedRegistryEntry identified registry entry}, which is identified with a number.
 *
 * @since 1.0
 * @author Codestech
 * @see IdentifiedRegistryEntry
 */
public interface NumberIdentifiedRegistryEntry extends IdentifiedRegistryEntry {
    /**
     * Gets an extracted numeric identifier of the entry from a registry.
     *
     * @return the extracted identifier
     * @since 1.0
     */
    int numericId();
}