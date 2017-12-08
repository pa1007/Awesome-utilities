package fr.depthdarkcity.sponge_utilitises.creator;

import org.spongepowered.api.text.Text;

public final class CommonException {

    /**
     * Exception hat Throws an exception to the console.
     */
    public static final Text
            CONSOLE_SOURCE_EXCEPTION = Text.of("You can't use that command with the console.");

    /**
     * Exception that Throws a command Permission exeption.
     */
    public static final Text SOURCE_PERMISSION_EXCEPTION = Text.of("You don't have the permission to do this command.");

    public CommonException() {
    }
}
