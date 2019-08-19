package org.charlice.beans.xml;

import org.charlice.beans.core.io.Resource;
import org.charlice.beans.exception.BeanDefinitionException;
import org.charlice.beans.factory.support.BeanDefinitionRegistry;
import org.charlice.beans.factory.support.GenericBeanDefinition;
import org.charlice.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XMLBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE_ATTRIBUTE = "scope";

    private BeanDefinitionRegistry register;

    public XMLBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.register = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream inputStream = null;

        try {
            inputStream = resource.getInputStream();

            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();//获取根节点-> beans
            Iterator iterator = rootElement.elementIterator();//通过迭代器遍历beans下所有元素

            while(iterator.hasNext()){
                Element element = (Element)iterator.next();
                String beanId = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                String scope = element.attributeValue(SCOPE_ATTRIBUTE);
                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition(beanId,beanClassName,scope);
                genericBeanDefinition.setScope(scope);

                this.register.register(beanId,genericBeanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionException(resource.getDescription()+" source config can't be found");
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
}
