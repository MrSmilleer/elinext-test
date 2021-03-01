package implementation;

import annotations.Inject;
import exceptions.ConstructorNotFoundException;
import exceptions.TooManyConstructorsException;
import interfaces.Injector;
import interfaces.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class InjectorImpl implements Injector {

    //коллекция, которая хранит в себе провайдеры классов
    //в качестве key используется имя класса, полученное методом getName()
    private Map<String, Provider<?>> providerMap = Collections.synchronizedMap(new HashMap());


    /** Возвращает Provider по классу. Перед этим извлекает Provider из коллекции providerMap,
     * которая является потокобезопасной.
     */
    @Override
    public <T> Provider<T> getProvider(Class<T> type) {

        Provider<T> provider = (Provider<T>) providerMap.get(type.getName());
        return provider;
    }

    /** Метод для биндинга Prototype класса
     */
    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl)
            throws TooManyConstructorsException, ConstructorNotFoundException {

        bind(intf, impl, false);
    }

    /** Метод для биндинга Singletone класса
     */
    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl)
            throws TooManyConstructorsException, ConstructorNotFoundException  {

        bind(intf, impl, true);
    }


    /** Вспомогательный метод, который создаёт новый Provider<T> и добавляет его в коллекцию providerMap
     */
    private <T>  void bind(Class<T> inf, Class<? extends T> impl, boolean isSingltone)
            throws TooManyConstructorsException, ConstructorNotFoundException{

        Constructor<?>  constructor = null;

        constructor = findConstructor(impl);


        ProviderImpl<T> provider = new ProviderImpl<>(constructor, isSingltone);
        providerMap.put(inf.getName(),provider);
        provider.setProviderMap(providerMap);
    }


    /** Вспомогательный метод, который возвращает конструктор класса
     * или выкидывает исключения:
     * TooManyConstructorsException - если в классе несколько конструкторов с аннотацие @Inject;
     * ConstructorNotFoundException - если в классе нет конструктора с @Inject и нет конструктора по умолчанию
    */
    private Constructor<?> findConstructor(Class<?> classToCheck)
            throws TooManyConstructorsException, ConstructorNotFoundException{

        Constructor<?> constructor = null;
        boolean annFound = false;
        boolean defFound = false;

        Constructor<?>[] constructors = classToCheck.getConstructors();

        for (Constructor<?> cons : constructors){
            Annotation ann = cons.getAnnotation(Inject.class);
            if(ann != null){
                if (annFound == true)
                    throw new TooManyConstructorsException();
                else {
                    annFound = true;
                    constructor = cons;
                }
            }

            if (defFound != true && annFound != true && cons.getParameterCount() == 0){
                defFound = true;
                constructor = cons;
            }
        }

        if (constructor == null){
            throw new ConstructorNotFoundException();
        }

        return constructor;
    }
}
