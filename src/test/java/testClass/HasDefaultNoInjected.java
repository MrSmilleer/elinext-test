package testClass;

import annotations.Inject;

public class HasDefaultNoInjected implements Dictionary{
    Word word = null;
    Name name = null;

    public HasDefaultNoInjected() {

    }


    public HasDefaultNoInjected(Word word) {

    }


    public HasDefaultNoInjected(Word word, Name name) {
        this.word = word;
        this.name = name;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public boolean isInjected() {
        return false;
    }
}
