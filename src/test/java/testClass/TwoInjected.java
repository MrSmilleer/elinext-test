package testClass;

import annotations.Inject;

public class TwoInjected implements Dictionary
{
	

	public TwoInjected() {

	}

    @Inject
    public TwoInjected(Word word) {

    }

    @Inject
    public TwoInjected(Word word, Name name) {

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
