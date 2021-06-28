package org.charlice.beans.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.charlice.beans.BeanDefinition;
import org.charlice.beans.PropertyValue;
import org.charlice.beans.core.io.Resource;
import org.charlice.beans.exception.BeanDefinitionException;
import org.charlice.beans.factory.config.RuntimeBeanReference;
import org.charlice.beans.factory.config.TypedStringValue;
import org.charlice.beans.factory.support.BeanDefinitionRegistry;
import org.charlice.beans.factory.support.GenericBeanDefinition;
import org.charlice.utils.StringUtils;
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

    private static final String PROPERTY_ELEMENT = "property";

    private static final String REF_ATTRIBUTE = "ref";

    private static final String VALUE_ATTRIBUTE = "value";

    private static final String NAME_ATTRIBUTE = "name";

    private static final Log LOGGER = LogFactory.getLog(XMLBeanDefinitionReader.class);

    private BeanDefinitionRegistry register;

    public XMLBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.register = beanDefinitionRegistry;
    }

    //解析xml以及bean属性.注入beanDefinition
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
                BeanDefinition beanDefinition = new GenericBeanDefinition(beanId,beanClassName);
                if(StringUtils.hasText(scope)){
                    beanDefinition.setScope(scope);
                }
                parsePropertyElement(element,beanDefinition);
                this.register.register(beanId,beanDefinition);
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

    //解析bean属性 ,注入beanDefinition
    public void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) {
        Iterator iterator = beanElement.elementIterator(PROPERTY_ELEMENT);
        while(iterator.hasNext()){
            Element propertyElement = (Element)iterator.next();
            String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if(!StringUtils.hasLength(propertyName)){
                LOGGER.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }
            Object value = parsePropertyValue(propertyElement,beanDefinition,propertyName);
            PropertyValue propertyValue = new PropertyValue(propertyName, value);
            beanDefinition.getPropertyValues().add(propertyValue);
        }
    }

    //解析属性的值(暂只支持两种属性-ref/value)
    public Object parsePropertyValue(Element propertyElement, BeanDefinition beanDefinition, String propertyName) {
        String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'" : "<constructor-arg> element";

        boolean hasRefAttribute = propertyElement.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = propertyElement.attribute(VALUE_ATTRIBUTE) != null;
        if(hasRefAttribute){
            String refName = propertyElement.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)){
                LOGGER.error(elementName+" contains empty 'ref' attributes");
            }
            RuntimeBeanReference reference = new RuntimeBeanReference(refName);
            return reference;
        }else if(hasValueAttribute){
            String value = propertyElement.attributeValue(VALUE_ATTRIBUTE);
            TypedStringValue valueHolder = new TypedStringValue(value);
            //valueHolder.setSource(extractSource(propertyElement));
            return valueHolder;
        }else {
            throw new RuntimeException(elementName+" must specify a ref or value");
        }
    }
}
