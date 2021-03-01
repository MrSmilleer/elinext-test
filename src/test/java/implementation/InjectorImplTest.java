package implementation;

import exceptions.BindingNotFoundException;
import exceptions.ConstructorNotFoundException;
import exceptions.TooManyConstructorsException;
import interfaces.Injector;
import interfaces.Provider;
import junit.framework.TestCase;
import org.junit.*;
import testClass.*;

public class InjectorImplTest extends TestCase {

    @Test
    public void testBindExceptions()     {
        Injector injector = new InjectorImpl(); //создаём имплементацию инжектора

        try {
            injector.bind(Dictionary.class, TwoInjected.class); //биндим класс у которого два конструктуа @Inject
            fail("Check too many constructors");
        } catch (TooManyConstructorsException e) {

        }  catch (ConstructorNotFoundException e) {

        }

        try {
            injector.bind(Dictionary.class, NoDefaultConstructor.class); // биндим класс у которого
                                                                         // нет конструктора по умолчанию и @Inject
            fail("Check is default constructor exist");

        } catch (ConstructorNotFoundException e) {

        } catch (TooManyConstructorsException e){

        }
    }

    @Test
    public void testExistingBindings(){
        Injector injector = new InjectorImpl();
        try {
            injector.bind(Dictionary.class, OneInjected.class); //биндим класс с конструктором @Inject.
            injector.bind(Name.class, NameImpl.class);         //Биндим один из двух необходимых классов для конструктора
                                                                //предыдущего забинженого класса.
        } catch (TooManyConstructorsException e) {
            e.printStackTrace();
        } catch (ConstructorNotFoundException e) {
            e.printStackTrace();
        }

        Provider<Word> wordProvider = injector.getProvider(Word.class);  //получаем провайдера класса который не был
        assertNull("getProvider: Check provider is null", wordProvider); // забинжен

        Provider<Dictionary> dictionaryProvider = injector.getProvider(Dictionary.class);
        try {
            Dictionary dictionary = dictionaryProvider.getInstance(); //создаём экземпляр у которого нет всех
            fail("Check there is no binding");                        // биндингов для параметров
        } catch (BindingNotFoundException e) {

        }



        try {
            injector.bind(Word.class, WordImpl.class); // добавляем необходимый класс, чтобы было достаточно параметров
            dictionaryProvider = injector.getProvider(Dictionary.class);

            assertSame("getInstance: Check is it correct class returned"
                    , OneInjected.class, dictionaryProvider.getInstance().getClass()
            );

            assertTrue("getInstance: Check is it injected constructor"
                    , dictionaryProvider.getInstance().isInjected()
            );

            injector.bind(Dictionary.class, HasDefaultNoInjected.class); //биндим класс с конструктором по умолчанию
            dictionaryProvider = injector.getProvider(Dictionary.class);
            assertSame("getInstance: Check is it correct class(with default constructor), after rebinding"
                    , HasDefaultNoInjected.class, dictionaryProvider.getInstance().getClass()
            );

            injector.bindSingleton(Word.class, WordImpl.class); //биндим синглтон класс
            wordProvider = injector.getProvider(Word.class);
            Word wordOne = wordProvider.getInstance();
            Word wordTwo = wordProvider.getInstance();
            assertSame("bindSingletone: Check singltone", wordOne, wordTwo);

            Provider<Name> nameProvider = injector.getProvider(Name.class); //проверяем prototype класс
            Name nameOne = nameProvider.getInstance();
            Name nameTwo = nameProvider.getInstance();
            assertNotSame("bind: Check prototype", nameOne, nameTwo);

        } catch (BindingNotFoundException e) {
            e.printStackTrace();
        } catch (TooManyConstructorsException e) {
            e.printStackTrace();
        } catch (ConstructorNotFoundException e) {
            e.printStackTrace();
    }

    }

}