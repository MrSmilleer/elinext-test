package implementation;

import exceptions.BindingNotFoundException;
import interfaces.Provider;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ProviderImpl<T> implements Provider<T> {
    private Map<String, Provider<?>> providerMap;
    private Constructor<?> beanConst;
    private T singlton = null;
    private boolean isSinglton = false;

    /** Создаёт объект Provider, который сохраняет конструктор класса, использующийся в методе getInstance
     */
    public ProviderImpl(Constructor<?> beanConst, boolean isSinglton) {
        this.beanConst = beanConst;
        this.isSinglton = isSinglton;
    }

    /** Создаёт экземпляр с помощью сохранённого конструктора
      */
    @Override
    public T getInstance() throws BindingNotFoundException{
        if (singlton != null){
            return singlton;
        }

        T bean = null;
        if (beanConst.getParameterCount() == 0){
            try {
                bean = (T) beanConst.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {

            Class<?>[] paramTypes = beanConst.getParameterTypes();
            Object[] parameters = new Object[paramTypes.length];
            for (int i = 0; i < parameters.length; i++){
                Class<?> type = paramTypes[i];
                Provider<?> provider = providerMap.get(type.getName());

                if (provider == null)
                    throw new BindingNotFoundException();

                parameters[i] = provider.getInstance();
            }
            try {
                bean = (T) beanConst.newInstance(parameters);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        if (isSinglton == true){
            singlton = bean;
            return singlton;
        }

        return bean;
    }

    public void setProviderMap(Map<String, Provider<?>> providers){
        this.providerMap = providers;
    }
}
