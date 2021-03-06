package org.test4j.module.inject;

import org.junit.Test;
import org.test4j.fortest.beans.User;
import org.test4j.module.ICore;
import org.test4j.module.inject.annotations.Inject;
import org.test4j.module.inject.annotations.TestedObject;

/**
 * 类InjectModuleTest.java的实现描述
 * 
 * @author darui.wudr 2013-1-9 下午2:50:26
 */
public class InjectModuleTest implements ICore {
    @TestedObject
    User   user     = new User();

    @Inject
    User   assistor = new User("he", "a111");

    @Inject
    String first    = "wu";

    @Test
    public void testFindTestedObjectTargets() throws Exception {
        want.object(user).propertyEq("first", "wu");
        want.string(user.getAssistor().getFirst()).isEqualTo("he");
        want.string(user.getAssistor().getLast()).isEqualTo("a111");
    }
}
