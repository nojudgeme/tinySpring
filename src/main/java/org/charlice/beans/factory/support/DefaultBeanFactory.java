package org.charlice.beans.factory.support;

import org.charlice.beans.BeanDefinition;
import org.charlice.beans.exception.BeanCreationException;
import org.charlice.beans.exception.BeanDefinitionException;
import org.charlice.beans.factory.BeanFactory;
import org.charlice.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream inputStream = null;

        try {
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();//通过Spring
            inputStream = classLoader.getResourceAsStream(configFile);

            //通过DOM4J 读取配置文件
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();//获取根节点-> beans
            Iterator iterator = rootElement.elementIterator();//通过迭代器遍历beans下所有元素

            while(iterator.hasNext()){
                Element element = (Element)iterator.next(); //获取到每个节点元素
                String beanId = element.attributeValue(ID_ATTRIBUTE);//每个节点的ID
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);//每个节点对应的类
                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition(beanId,beanClassName);
                this.beanDefinitionMap.put(beanId,genericBeanDefinition);//放入到map存 方便取
            }
        } catch (Exception e) {
            throw new BeanDefinitionException(configFile+" source config can't be found");
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = getBeanDefinition(beanId);//先获取到该bean的className
        if (beanDefinition == null) {
            throw new BeanCreationException("beanId:" + beanId + " can't be found");
        }
        String beanClassName = beanDefinition.getBeanClassName();
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();//通过Spring.ClassUtils获取到类加载器
        try {
            Class<?> loadClass = defaultClassLoader.loadClass(beanClassName);//Spring的类加载器去取得该bean的实例
            return loadClass.newInstance();//前提是 目标类必须含有无参构造器 不然获取不到该实例会报错
        } catch (Exception e) {
            throw new BeanCreationException("beanClassName:" + beanClassName + " can't be found");
        }
    }
}
