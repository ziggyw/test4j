package org.test4j.module.dbfit;

import java.sql.SQLException;
import java.util.Map;

import org.test4j.module.dbfit.IDbFit;
import org.test4j.module.dbfit.utility.SymbolUtilTest.PoJoExample;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@Test(groups = "test4j")
@SuppressWarnings("rawtypes")
public class Test4JFitnesseTest extends Test4J implements IDbFit {

    @Test
    public void testExecute_NoSymbols() throws SQLException {
        fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

        Map map = fit.query("select * from tdd_user", Map.class);
        want.map(map).hasEntry("id", 1, "first_name", "darui.wu");
    }

    @Test
    public void testExecute_HasSymbols() throws SQLException {
        fit.cleanSymbols();
        fit.setSymbol("firstName", "darui.wu");
        fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

        Map map = fit.query("select * from tdd_user where first_name='@{firstName}'", Map.class);
        want.map(map).hasEntry("id", 1, "first_name", "darui.wu");
    }

    @Test
    public void testExecute_HasSymbols2() throws SQLException {
        fit.cleanSymbols();
        fit.setSymbol("pojo", new PoJoExample("darui.wu", null));
        fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

        Map map = fit.query("select * from tdd_user where first_name='@{pojo[name]}'", Map.class);
        want.map(map).hasEntry("id", 1, "first_name", "darui.wu");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testExecute_HasSymbols_exception() throws SQLException {
        fit.execute("delete from tdd_user", "insert into tdd_user(id,first_name) values(1,'darui.wu')", "commit");

        Map map = fit.query("select * from tdd_user where first_name='@{pojo[]}'", Map.class);
        want.map(map).hasEntry("id", 1, "first_name", "darui.wu");
    }
}
