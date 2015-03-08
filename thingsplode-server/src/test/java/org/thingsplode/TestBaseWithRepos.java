/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@Ignore()
public class TestBaseWithRepos extends TestBase {

    @Autowired(required = true)
    private EntityManager entityManager;

    public TestBaseWithRepos() {
        super();
        File f = new File(System.getProperty("java.io.tmpdir") + "/thingsplode_h2db_test.h2.db");
        System.out.println("\n\n ***** \n REMOVING PREVIOUS DATABASE: (" + f.getAbsolutePath() + ")\n *****");
        if (f.exists()) {
            try {
                boolean delete = f.delete();
            } catch (Exception e) {
                System.out.println("*************\n\n\n WARNING!!!! \n\n\n DATABASE couldn't be removed due to:" + e.getMessage());
            }
        } else {
            System.out.println("\n\n **** WARNING: test database file was not found and not deleted. (" + f.getAbsolutePath() + ")");
        }
    }

    /**
     * A very resource efficient counter method (it will not return all the
     * objects from the database and count them, but execute <b>select count(*)
     * from " + tableName</b> and return the result.
     *
     * @param tableName the table for which you want to count the elements
     * @return the number of rows in that table.
     */
    protected Integer getCount(String tableName) {
        Query q = entityManager.createNativeQuery("select count(*) from " + tableName);
        return  ((BigInteger) q.getResultList().get(0)).intValue();
    }
    
    /**
     * Executes a native SQL query for checking test results.
     *
     * @param sqlQuery the native sql query you need to execute (use "?" for
     * parameters)
     * @param maxResults the max. number of results to be returned (in case the
     * database contains 100 rows, but you limit here to 10, it will return the
     * first 10 rows)
     * @param paramValues the parameter values for the query. you can use null,
     * if the query has no parameters, or you need to specify in the order the
     * query references them.
     * @return
     */
    protected List<Object> getNativeSQLQueryResult(final String sqlQuery, final int maxResults, final String... paramValues) {
        Query q = entityManager.createNativeQuery(sqlQuery);
        if (paramValues != null && paramValues.length > 0) {
            int i = 0;
            for (String param : paramValues) {
                q.setParameter(i, param);
                i += 1;
            }
        }
        q.setMaxResults(maxResults);
        return q.getResultList();
    }
}
