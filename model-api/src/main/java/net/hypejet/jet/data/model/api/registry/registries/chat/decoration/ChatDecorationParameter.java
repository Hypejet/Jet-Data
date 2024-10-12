package net.hypejet.jet.data.model.api.registry.registries.chat.decoration;

/**
 * Represents a parameter used by {@linkplain ChatDecoration chat decorations}.
 *
 * @since 1.0
 * @author Codestech
 * @see ChatDecoration
 */
public enum ChatDecorationParameter {
    /**
     * A parameter replacing placeholders with a name of a sender of a message.
     *
     * @since 1.0
     */
    SENDER,
    /**
     * A parameter replacing placeholders with a name of a target of a message.
     *
     * @since 1.0
     */
    TARGET,
    /**
     * A parameter replacing placeholders with contents of a message.
     *
     * @since 1.0
     */
    CONTENT
}