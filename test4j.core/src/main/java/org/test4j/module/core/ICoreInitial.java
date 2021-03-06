package org.test4j.module.core;

import org.test4j.module.database.dbop.IDBOperator;
import org.test4j.module.database.dbop.IInsertOp;
import org.test4j.module.spring.util.ISpringHelper;
import org.test4j.spec.ISpecExecutorFactory;
import org.test4j.tools.commons.ConfigHelper;
import org.test4j.tools.commons.Reflector;

/**
 * ICore接口类静态变量初始化工具类
 * 
 * @author darui.wudr 2013-1-15 上午10:07:53
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ICoreInitial {
    private static Class springHelperClazz = null;

    public static ISpringHelper initSpringHelper() {
        try {
            if (springHelperClazz == null) {
                springHelperClazz = Class.forName("org.test4j.module.spring.utility.SprinHelperImpl");
            }
            return (ISpringHelper) springHelperClazz.newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    static Class dbInsertOpClazz = null;

    public static IInsertOp newInsertOp() {
        try {
            if (dbInsertOpClazz == null) {
                dbInsertOpClazz = Class.forName("org.test4j.module.database.dbop.InsertOp");
            }
            Object o = dbInsertOpClazz.newInstance();
            return (IInsertOp) o;
        } catch (Throwable e) {
            return null;
        }
    }

    public static IDBOperator initDBOperator() {
        try {
            Class claz = Class.forName("org.test4j.module.database.dbop.DBOperator");
            Object o = Reflector.instance.newInstance(claz);
            return (IDBOperator) o;
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 返回全局的SpecExecutorFactory
     * 
     * @return
     */
    public static ISpecExecutorFactory initSpecExecutorFactory() {
        try {
            String _default = ConfigHelper.getString("jspec.executor.factory");
            Class claz = Class.forName(_default);
            Object factory = Reflector.instance.newInstance(claz);
            return (ISpecExecutorFactory) factory;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
