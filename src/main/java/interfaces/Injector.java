package interfaces;

import exceptions.ConstructorNotFoundException;
import exceptions.TooManyConstructorsException;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type);

    <T> void bind(Class<T> intf, Class<? extends T> impl)
            throws TooManyConstructorsException, ConstructorNotFoundException;


    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl)
            throws TooManyConstructorsException, ConstructorNotFoundException;

}
