package com.yonyou.databasebootstrap.repository;

import com.yonyou.databasebootstrap.entity.DataBaseSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Ember
 * @Date: 2021/8/13 10:59
 * @Description:
 */
@Repository
public interface DataBaseLogRepository extends JpaRepository<DataBaseSource, Object> {

    /**
     * 添加数据库前确认是否已经存在，并取出主键
     * @param host
     * @param port
     * @param driver
     * @param username
     * @param password
     * @param db
     * @param status
     * @return
     */
    @Query(value = "select db_log.id from DataBaseSource db_log where db_log.host = :host " +
            "and db_log.port = :port and db_log.driver = :type " +
            "and db_log.userName = :username and db_log.password = :password " +
            "and db_log.dataBaseName = :db and db_log.status = :status")
    public Integer isExist(@Param("host") String host,@Param("port") String port,
                           @Param("type") String driver,@Param("username") String username,
                           @Param("password") String password,@Param("db") String db,
                           @Param("status") Integer status);

    /**
     * 根据主键寻找Code
     * @param id
     * @return
     */
    @Query(value = "select db_log.code from DataBaseSource db_log where db_log.id = :id")
    public String selectCode(@Param("id") Integer id);

    /**
     * 新增数据库
     * @param host
     * @param port
     * @param driver
     * @param username
     * @param password
     * @param db
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "insert into db_log(db_host,db_port,db_type,db_username,db_password,db) " +
            "values(:host,:port,:type,:username,:password,:db)"
            ,nativeQuery = true)
    public Integer saveDb(@Param("host") String host,@Param("port") String port,
                          @Param("type") String driver,@Param("username") String username,
                          @Param("password") String password,@Param("db") String db);

    /**
     * 根据code去找到不同环境的ID
     * @param code
     * @return
     */
    @Query(value = "select db_log.id from DataBaseSource db_log where db_log.code = :code")
    public List<Integer> findDbIdByCode(@Param("code") String code);

    /**
     * 获取记录的所有数据源（成功连接过的）
     * @return
     */
    @Query(value = "select new DataBaseSource (db_log.id,db_log.status,db_log.driver,db_log.dataBaseName,db_log.sourceName,db_log.host,db_log.port,db_log.createTime) from DataBaseSource db_log")
    public List<DataBaseSource> getSource();

}
