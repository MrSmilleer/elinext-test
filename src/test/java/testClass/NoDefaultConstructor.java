package testClass;

import annotations.Inject;

public class NoDefaultConstructor implements Dictionary{

    public NoDefaultConstructor(Word word) {

    }

    public NoDefaultConstructor(Word word, Name name) {

    }

    @Override
    public int size(){
        return 1;
    }

    @Override
    public boolean isInjected() {
        return false;
    }
}
