package org.test4j.module.spring.strategy.register.types;

import java.util.Queue;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.test4j.module.spring.exception.FindBeanImplClassException;
import org.test4j.module.spring.strategy.register.RegisterBeanDefinition;
import org.test4j.module.spring.strategy.register.RegisterDynamicBean;
import org.test4j.tools.commons.ClazzConst;
import org.test4j.tools.commons.ClazzHelper;

/**
 * 属性依赖注册
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class PropertiesRegister {
	protected final Class ownerClazz;
	protected final RegisterBeanDefinition definitionRegister;

	protected PropertiesRegister(final Class ownerClazz, final RegisterBeanDefinition definitionRegister) {
		this.ownerClazz = ownerClazz;
		this.definitionRegister = definitionRegister;
	}

	public abstract void registerProperties(final Queue<Class> registedBeanClazz);

	/**
	 * 注册ownerClazz的属性bean
	 * 
	 * @param ownerClazz
	 * @param definitionRegister
	 * @param registedBeanClazz
	 *            以注册的spring bean实现类列表
	 */
	public static void registerPropertiesBean(final Class ownerClazz, final RegisterBeanDefinition definitionRegister,
			final Queue<Class> registedBeanClazz) {
		new MethodPropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);

		boolean isResourceAvailable = ClazzHelper.isClassAvailable(ClazzConst.Javax_Resource_Annotation);
		if (isResourceAvailable) {
			new ResourcePropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);
		}

		boolean isAutowiredAvailable = ClazzHelper.isClassAvailable(ClazzConst.Spring_Autowired_Annotation);
		if (isAutowiredAvailable) {
			new AutowiredPropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);
		}
	}

	protected void registerBean(final String beanName, final Class propClazz, final Queue<Class> registedBeanClazz) {
		try {
			boolean doesRegisted = definitionRegister.doesHaveRegisted(beanName);
			if (doesRegisted) {
				return;
			}
			Class impl = definitionRegister.findImplementClass(ownerClazz, beanName, propClazz);
			if (impl == null) {
				return;
			}
			// AbstractBeanDefinition beanDefinition =
			// SpringBeanRegister.getAnnotatedGenericBeanDefinition(beanName,
			// impl, true);
			AbstractBeanDefinition beanDefinition = RegisterDynamicBean
					.getRootBeanDefinition(beanName, impl, null, true);
			definitionRegister.register(beanName, beanDefinition);

			registedBeanClazz.offer(impl);
		} catch (FindBeanImplClassException e) {
			definitionRegister.ignoreNotFoundException(e);
		}
	}
}
