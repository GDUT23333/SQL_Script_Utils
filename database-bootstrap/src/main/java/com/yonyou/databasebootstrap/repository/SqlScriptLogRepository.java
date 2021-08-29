package com.yonyou.databasebootstrap.repository;

import com.yonyou.databasebootstrap.entity.SqlScript;
import com.yonyou.databasebootstrap.service.pojo.ConvertSqlScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Author: Ember
 * @Date: 2021/8/13 12:04
 * @Description: sql脚本记录
 */
@Repository
public interface SqlScriptLogRepository extends JpaRepository<SqlScript,Object> {

    /**
     * 记录sql脚本
     * @param version
     * @param comment
     * @param content
     * @param executeType
     * @param dbId
     * @param exception
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "insert into script_log(script_version,script_comment,script_content,execute_type,db_id,exception) values(:version,:comment,:content,:executeType,:dbId,:exception)",nativeQuery = true)
    public Integer log(@Param("version") String version,@Param("comment") String comment,
                       @Param("content") String content,@Param("executeType") Integer executeType,
                       @Param("dbId") Integer dbId,@Param("exception") String exception);


    /**
     * 获取指定数据库的上传的所有脚本（做了上传动作的，即请求了upload-sqlScript）
     * @param dbId
     * @param executeType
     * @return
     */
    public List<SqlScript> getAllByDbIdAndAndExecuteType(Integer dbId,Integer executeType);

    /**
     * 根据ID寻找脚本
     * @param id
     * @return
     */
    public SqlScript findById(Integer id);

}
