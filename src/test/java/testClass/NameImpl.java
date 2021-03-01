package testClass;

public class NameImpl implements Name{
    String name = null;

    public NameImpl(){
        this.name = "Name";
    }

    public NameImpl(Word word){
        this.name = "Not a name";
    }

    @Override
    public void setName(String name) {

    }
}
