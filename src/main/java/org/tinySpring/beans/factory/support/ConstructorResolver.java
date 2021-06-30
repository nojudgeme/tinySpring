package org.tinySpring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tinySpring.beans.BeanDefinition;
import org.tinySpring.beans.ConstructorArgument;
import org.tinySpring.beans.SimpleTypeConverter;
import org.tinySpring.beans.exception.BeanCreationException;
import org.tinySpring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {

    private static final Log LOGGER = LogFactory.getLog(ConstructorResolver.class);

    private ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = null;
        try {
            beanClass = this.beanFactory.getBeanClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getID(),"Instantiation of bean failed,can't find "+beanDefinition.getBeanClassName() +" class");
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        ConstructorArgument constructorArgs = beanDefinition.getConstructorArgument();
        Constructor<?>[] candidates = beanClass.getConstructors();
        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            //检查参数个数是否匹配,不匹配的直接跳过
            if(parameterTypes.length!=constructorArgs.getArgumentCount()){
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(parameterTypes,constructorArgs.getArgumentsValues(),argsToUse,valueResolver,typeConverter);
            if(result){//如果参数类型匹配,则将构造器赋值给constructorToUse
                constructorToUse = candidates[i];
                break;
            }
        }
        //如果constructorToUse为空,说明没有找到对应的参数类型
        if(constructorToUse == null){
            throw new BeanCreationException(beanDefinition.getID(),"can't find a appropriate constructor");
        }
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getID(),"can't find a create instance using "+argsToUse);
        }
    }

    //逐个遍历bean的构造函数的参数类型与XMLBeanDefinitionReader中存放的beanDefinition.ConstructorArgument保存的参数类型是否一致
    private boolean valuesMatchTypes(Class<?>[] parameterTypes,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //runtimeReference/typedStringValue
            Object originalValue = valueHolder.getValue();
            try {
                //看实际是哪种类型: ref/string
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //如果记录的是string,但是其他的类型,则做一次类型转换
                Object convertValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //类型转换成功,则记录下来;失败,则会抛出异常
                argsToUse[i] = convertValue;
            }catch (Exception e){
                LOGGER.error(e);
                return false;
            }
        }
        return true;
    }
}
