package com.yonyou.databasebootstrap.repository;

import com.yonyou.databasebootstrap.entity.ExecuteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ember
 * @Date: 2021/8/25 12:50
 * @Description: 脚本执行情况记录
 */
@Repository
public interface ExecuteLogRepository extends JpaRepository<ExecuteLog,Object> {

    /**
     * 给指定数据库添加脚本执行情况记录
     * @param scriptId
     * @param dbId
     */
    @Transactional
    @Modifying
    @Query(value = "insert into execute_log(s_id,db_id) values(:s_id,:db_id) ",nativeQuery = true)
    public void addLog(@Param("s_id") Integer scriptId,@Param("db_id") Integer dbId);

    /**
     * 执行脚本进行日志记录
     * @param scriptId
     * @param executeType
     * @param executeResult
     * @param currentTime
     * @param exception
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "update execute_log set execute_type = :execute_type,execute_result = :execute_result," +
            "execute_time = :execute_time,exception = :exception where id = :id",nativeQuery = true)
    public Integer executeLog(@Param("id") Integer scriptId,@Param("execute_type") Integer executeType,
                            @Param("execute_result") Integer executeResult,@Param("execute_time") Date currentTime,
                            @Param("exception") String exception);

    /**
     * 获取当前数据库执行与未执行过的所有脚本记录
     * @param dbId
     * @return
     */
    public List<ExecuteLog> findAllByDbIDOrderByExecuteTimeDesc(Integer dbId);

    /**
     * 根据数据库ID和id找到执行的脚本
     * @param dbId
     * @param id
     * @return
     */
    public ExecuteLog findAllByDbIDAndId(Integer dbId,Integer id );
}
