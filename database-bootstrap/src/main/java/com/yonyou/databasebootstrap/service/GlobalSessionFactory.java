package com.yonyou.databasebootstrap.service;

import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.template.AbstractPoolSource;
import com.yonyou.databasebootstrap.utils.CodeEnumsUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: Ember
 * @Date: 2021/8/12 9:40
 * @Description: 全局SessionFactory
 */
public class GlobalSessionFactory {

    private static Logger logger = LoggerFactory.getLogger(GlobalSessionFactory.class);

    /**
     * 配置
     */
    private Configuration configuration;
    private SessionFactory factory;
    private DataPoolSource origin;
    private AbstractPoolSource source;

    /**
     * 记录当前连接数据库的状态
     * 1. 当前数据库ID
     * 2. 当前数据库所属环境
     * 3. 当前数据库的code（用来关联不同环境的数据库）
     */
    private Integer dbId;
    private Integer status;
    private String code;

    /**
     * 1. globalSessionFactory：当前连接的数据库
     * 2. backup：备用的数据库（先放在这里，以后可能用到）
     */
    private static GlobalSessionFactory globalSessionFactory = null;
    private static GlobalSessionFactory backUp = null;

    private GlobalSessionFactory() {
    }

    private GlobalSessionFactory(DataPoolSource origin, AbstractPoolSource source){
        this.origin = origin;
        this.source = source;
        this.configuration = createConfiguration();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(this.configuration.getProperties()).build();
        this.factory = this.configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * 生成配置文件
     * @return
     */
    private Configuration createConfiguration(){
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class",this.source.getDriver());
        configuration.setProperty("hibernate.connection.url",this.source.getUrl());
        configuration.setProperty("hibernate.connection.username",this.source.getUserName());
        configuration.setProperty("hibernate.connection.password",this.source.getPassword());
        //common
        configuration.setProperty("hibernate.c3p0.max_size" , "20")
                .setProperty("hibernate.c3p0.min_size" , "1")
                .setProperty("hibernate.c3p0.timeout" , "5000")
                .setProperty("hibernate.c3p0.max_statements" , "100")
                .setProperty("hibernate.c3p0.idle_test_period" , "3000")
                .setProperty("hibernate.c3p0.acquire_increment" , "2")
                .setProperty("hibernate.c3p0.validate" , "true")
                .setProperty("hibernate.dialect"
                        , source.buildDialog())
                .setProperty("hibernate.hbm2ddl.auto" , "update")
                .setProperty("hibernate.show_sql","true");
        return configuration;
    }

    /**
     * 是否已经有数据源
     * @return
     */
    public static Boolean isExist(){
        return globalSessionFactory != null;
    }

    /**
     * 测试连接
     * @param origin
     * @param source
     * @return
     */
    public static void testConnect(DataPoolSource origin, AbstractPoolSource source) throws FailedException {
        try{
            GlobalSessionFactory globalSessionFactory = new GlobalSessionFactory(origin, source);
            // let gc work
            globalSessionFactory = null;
        }catch(Exception e){
            e.printStackTrace();
            logger.error("{}:测试连接失败",origin.getDriver());
            throw new FailedException(CodeEnumsUtils.CONNECT_FAILED);
        }
    }

    /**
     * 判断是否重复建造数据源，先清空再重建
     * @return
     */
    public synchronized static GlobalSessionFactory cleanAndReDo(DataPoolSource origin,
                                                                 AbstractPoolSource source){
        clean();
        return createFactory(origin,source);
    }

    private static void clean(){
        globalSessionFactory = null;
    }

    /**
     * 获取数据源
     * @return
     */
    public static GlobalSessionFactory getSessionFactory() throws FailedException {
        if(isExist()){
            return globalSessionFactory;
        }
        throw new FailedException(CodeEnumsUtils.DATASOURCE_IS_EMPTY);
    }

    /**
     * DCL 全局单例SessionFactory
     * @param source
     * @return
     */
    public static GlobalSessionFactory createFactory(DataPoolSource origin,
                                                     AbstractPoolSource source){
        if(!isExist()){
            synchronized (GlobalSessionFactory.class){
                if(!isExist()){
                    globalSessionFactory = new GlobalSessionFactory(origin,source);
                }
            }
        }
        return globalSessionFactory;
    }

    /**
     * 获取Session
     * @return
     */
    public Session getSession(){
        return this.factory.openSession();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public DataPoolSource getOrigin() {
        return origin;
    }

    public void setOrigin(DataPoolSource origin) {
        this.origin = origin;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
