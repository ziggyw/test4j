package org.test4j.module.spring.strategy.injector;

import java.lang.annotation.Annotation;

import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringBeanByType;
import org.test4j.module.spring.strategy.Test4JBeanFactory;

/**
 * 查找spring策略接口，以及策略工厂类
 * 
 * @author darui.wudr
 */
public abstract class SpringBeanInjector {

    private final static SpringBeanInjector byName = new SpringBeanInjectorByName();

    private final static SpringBeanInjector byType = new SpringBeanInjectorByType();

    /**
     * 往测试类实例中注入spring bean
     * 
     * @param testedObject
     */
    public static void injectSpringBeans(Object beanFactory, Object testedObject) {
        if (beanFactory instanceof Test4JBeanFactory) {
            byName.injectBy((Test4JBeanFactory) beanFactory, testedObject, SpringBeanByName.class);
            byType.injectBy((Test4JBeanFactory) beanFactory, testedObject, SpringBeanByType.class);
        } else {
            throw new RuntimeException(String.format(
                    "the type error, object[%s] isn't an instance of Test4JBeanFactory.", beanFactory == null ? null
                            : beanFactory.getClass().getName()));
        }
    }

    /**
     * 按Annotation注释注入spring bean到测试实例中
     * 
     * @param context spring容器
     * @param testedObject 测试类
     * @param annotation 字段声明的Annotation
     * @return
     */
    public abstract void injectBy(Test4JBeanFactory beanFactory, Object testedObject,
                                  Class<? extends Annotation> annotation);
}
