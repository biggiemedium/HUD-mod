package dev.px.hud.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandManifest {
    String name();
    String[] aliases() default {};
    String description() default "";
    String usage() default "";
}
