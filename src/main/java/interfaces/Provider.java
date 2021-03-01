package interfaces;

import exceptions.BindingNotFoundException;

public interface Provider<T> {
    T getInstance() throws BindingNotFoundException;
}
