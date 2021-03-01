package annotations;

import java.lang.annotation.*;

@Target(value=ElementType.CONSTRUCTOR)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Inject {
}
