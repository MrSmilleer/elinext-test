package testClass;

import annotations.Inject;

public class OneInjected implements Dictionary{

    Word word = null;
    Name name = null;

    public OneInjected() {

    }


    public OneInjected(Word word) {

    }

    @Inject
    public OneInjected(Word word, Name name) {
        this.word = word;
        this.name = name;
    }

    @Override
    public int size() {
        return 3;
    }

    public boolean isInjected(){
        if (word != null && name != null){
            return true;
        }
        return false;
    }

}
