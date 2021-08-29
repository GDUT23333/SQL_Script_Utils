package com.yonyou.databasebootstrap.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.databasebootstrap.entity.DataBaseSource;
import com.yonyou.databasebootstrap.entity.ExecuteLog;
import com.yonyou.databasebootstrap.entity.SqlScript;
import com.yonyou.databasebootstrap.repository.ExecuteLogRepository;
import com.yonyou.databasebootstrap.repository.SqlScriptLogRepository;
import com.yonyou.databasebootstrap.service.dto.DataBaseSourceDto;
import com.yonyou.databasebootstrap.service.mapper.DataBaseSourceMapper;
import com.yonyou.databasebootstrap.service.mapper.SqlScriptMapper;
import com.yonyou.databasebootstrap.service.pojo.ConvertSqlScript;
import com.yonyou.databasebootstrap.repository.DataBaseLogRepository;
import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.template.AbstractPoolSource;
import com.yonyou.databasebootstrap.utils.CodeEnumsUtils;
import com.yonyou.databasebootstrap.utils.PoolSourceFactoryUtils;
import com.yonyou.databasebootstrap.utils.SqlAnalyseUtils;
import com.yonyou.databasebootstrap.utils.VerifyUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.security.acl.LastOwnerException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Ember
 * @Date: 2021/8/11 13:08
 * @Description: 连接池一系列服务
 */
@Service
public class PoolSourceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Integer LOAD_SUCCESS = 0;
    private final Integer LOAD_FAILED = 1;
    private final Integer UP = 1;
    private final Integer DOWN = 2;
    private final Integer EXECUTE_SUCCESS = 0;
    private final Integer EXECUTE_FAILED = 1;
    private final String NOT_EXCEPTION = "没有异常";

    private DataBaseLogRepository dbLogRepository;
    private SqlScriptLogRepository sqlLogRepository;
    private ExecuteLogRepository executeLogRepository;
    private DataBaseSourceMapper mapper;

    @Autowired
    public void setLogRepository(DataBaseLogRepository dbLogRepository) {
        this.dbLogRepository = dbLogRepository;
    }

    @Autowired
    public void setSqlLogRepository(SqlScriptLogRepository sqlLogRepository) {
        this.sqlLogRepository = sqlLogRepository;
    }
    @Autowired
    public void setExecuteLogRepository(ExecuteLogRepository executeLogRepository) {
        this.executeLogRepository = executeLogRepository;
    }

    @Autowired
    public void setMapper(DataBaseSourceMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取当前数据库所有执行的记录（包括未执行）
     * @return
     * @throws FailedException
     */
    public List<ExecuteLog> getAll() throws FailedException {
        Integer id = GlobalSessionFactory.getSessionFactory().getDbId();
        return this.executeLogRepository.findAllByDbIDOrderByExecuteTimeDesc(id);
    }

    /**
     * 测试连接
     * @param source
     * @throws FailedException
     */
    public void testConnect(JSONObject source) throws FailedException {
        try{
            DataPoolSource poolSourceDto = JSON.toJavaObject(source, DataPoolSource.class);
            VerifyUtils.verifySource(poolSourceDto,true);
            AbstractPoolSource poolSource = PoolSourceFactoryUtils.changePoolSource(poolSourceDto);
            GlobalSessionFactory.testConnect(poolSourceDto,poolSource);
        }catch(Exception e){
            //传递异常继续抛出
            throw e;
        }
    }

    /**
     * 清空重新创建单例连接池
     * @param source
     */
    public synchronized void createPool(JSONObject source) throws FailedException {
        try{
            DataPoolSource poolSourceDto = JSON.toJavaObject(source, DataPoolSource.class);
            //判空
            VerifyUtils.verifySource(poolSourceDto,false);
            AbstractPoolSource poolSource = PoolSourceFactoryUtils.changePoolSource(poolSourceDto);
            GlobalSessionFactory.cleanAndReDo(poolSourceDto,poolSource);
            //连接成功进行入库并且生成主键返回
            Integer id = dbLogRepository.isExist(poolSourceDto.getHost(), poolSourceDto.getPort(),
                    poolSourceDto.getDriver(), poolSourceDto.getUserName(),
                    poolSourceDto.getPassword(), poolSourceDto.getDataBaseName(),
                    poolSourceDto.getStatus());
            //数据源配置数据库主键
            //没有主键则为需要进行添加数据库
            if (id == null){
                DataBaseSource dataBaseSource = mapper.poolSourceMapper(poolSourceDto);
                DataBaseSource baseSource = dbLogRepository.saveAndFlush(dataBaseSource);
                id = baseSource.getId();
            }
            //根据主键去寻找Code
            String code = dbLogRepository.selectCode(id);
            GlobalSessionFactory.getSessionFactory().setDbId(id);
            GlobalSessionFactory.getSessionFactory().setStatus(poolSourceDto.getStatus());
            GlobalSessionFactory.getSessionFactory().setCode(code);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 为当前数据库添加其他环境
     * @param source
     */
    public void addEnvironment(JSONObject source) throws FailedException {
        try{
            //获取当前数据源的Code
            String code = GlobalSessionFactory.getSessionFactory().getCode();
            Integer id = GlobalSessionFactory.getSessionFactory().getDbId();
            if(!StringUtils.isEmpty(code)){
                DataPoolSource origin = JSON.toJavaObject(source, DataPoolSource.class);
                VerifyUtils.verifySource(origin,false);
                AbstractPoolSource poolSource = PoolSourceFactoryUtils.changePoolSource(origin);
                //测试连接
                GlobalSessionFactory.testConnect(origin,poolSource);
                //连接通过即保存进数据库
                DataBaseSource dataBaseSource = mapper.poolSourceMapper(origin,code);
                DataBaseSource newSource = dbLogRepository.saveAndFlush(dataBaseSource);
                Integer newId = newSource.getId();
                //将当前的数据库拥有的脚本放入新增加的数据库中（相当于进行重新上传）
                List<SqlScript> allScripts = this.sqlLogRepository.getAllByDbIdAndAndExecuteType(id,LOAD_SUCCESS);
                addScriptsTo(allScripts,newId);
            }
        }catch (FailedException e){
            throw e;
        }
    }

    /**
     * 将脚本添加进指定数据库ID（重新上传）
     * @param scripts
     * @param newId
     */
    protected void addScriptsTo(List<SqlScript> scripts,Integer newId){
        scripts.forEach((item) ->{
            item.setDbId(newId);
            SqlScript script = this.sqlLogRepository.saveAndFlush(item);
            //对应往execute_log添加数据
            this.executeLogRepository.addLog(script.getId(),newId);
        });
    }
    /**
     * sql脚本上传 (text文件)
     * @param sqls
     */
    public void upload(MultipartFile sqls) throws Exception {
        //异常进行传递
        try {
            ConvertSqlScript sqlScript = SqlAnalyseUtils.parseSql(sqls);
            VerifyUtils.verifyScript(sqlScript);
            //进入到这里就转化成功了，可以进行入库
            logScript(null,null,sqlScript,LOAD_SUCCESS);
        } catch (Exception e) {
            //转化失败，入库记录失败
            logScript(e,sqls,null,LOAD_FAILED);
            throw e;
        }
    }

    /**
     * 执行脚本
     * @param executeID
     * @param executeType 1为执行，2为回滚
     * @throws FailedException
     */
    public void execute(Integer executeID,Integer executeType) throws FailedException {
        try{
            //找到对应的待执行脚本
            Integer dbId = GlobalSessionFactory.getSessionFactory().getDbId();
            ExecuteLog executeLog = this.executeLogRepository.findAllByDbIDAndId(dbId, executeID);
            String[] waitLoad = null;
            if(!Objects.isNull(executeLog) && !Objects.isNull(executeLog.getSqlScript())){
                ConvertSqlScript convertSqlScript = JSON.toJavaObject(JSON.parseObject(executeLog.getSqlScript().getContent()), ConvertSqlScript.class);
                if(UP.equals(executeType)){
                    waitLoad = convertSqlScript.getUp();
                }
                else if(DOWN.equals(executeType)){
                    waitLoad = convertSqlScript.getDown();
                }
                else{
                    throw new FailedException(CodeEnumsUtils.PARAM_UN_MATCH);
                }
                execute(waitLoad);
                //执行成功无报错，进行日志记录
                this.executeLogRepository.executeLog(executeID,executeType,EXECUTE_SUCCESS,new Date(),NOT_EXCEPTION);
                return;
            }
        } catch (FailedException e) {
            //捕捉上层传递的异常进行记录
            logger.error("脚本执行异常,原因是:{}",e.getMessage());
            String msg = e.getMessage();
            //进行错误日志记录
            this.executeLogRepository.executeLog(executeID,executeType,EXECUTE_FAILED,new Date(),msg);
            throw e;
        }
        throw new FailedException(CodeEnumsUtils.SCRIPT_UN_EXIST);
    }

    /**
     * sql脚本执行 (数组)
     * @param parseSql
     */
    private synchronized Integer execute(String[] parseSql) throws FailedException {
        int count = 0;
        try {
            Session session = null;
            session = GlobalSessionFactory.getSessionFactory().getSession();
            //开启事务
            Transaction tx = session.beginTransaction();
            for (String sql : parseSql) {
                session.createSQLQuery(sql).executeUpdate();
                count++;
            }
            //清除session的缓存
            session.flush();
            session.clear();
            tx.commit();
            //记录执行情况
            return LOAD_SUCCESS;
        } catch (FailedException e) {
            throw e;
        }catch(PersistenceException e){
            //捕捉执行脚本的异常
            if(e.getCause() instanceof SQLGrammarException){
                SQLGrammarException sqlException = (SQLGrammarException) e.getCause();
                //抛出异常，让上层捕捉进行日志记录
                String msg = "执行Sql脚本失败,失败的sql:"+parseSql[count]+
                        ";原因:"+sqlException.getSQLException();
                throw new FailedException(CodeEnumsUtils.EXECUTE_FAILED,msg);
            }
            throw new FailedException(CodeEnumsUtils.FAILED);
        }
    }


    /**
     * 保存脚本上传记录
     * @param script
     */
    @Transactional(rollbackFor = Exception.class)
    protected void logScript(Exception exception,MultipartFile file,
                           ConvertSqlScript script,
                           Integer executeType) throws FailedException {
        try {
            Integer dbId = GlobalSessionFactory.getSessionFactory().getDbId();
            String version;
            String exc = null;
            if(LOAD_SUCCESS.equals(executeType)){
                SqlScript sqlScript = SqlScriptMapper.sqlScriptMapper(script);
                sqlScript.setExecuteType(LOAD_SUCCESS);
                sqlScript.setDbId(dbId);
                SqlScript newSqlscript = sqlLogRepository.saveAndFlush(sqlScript);
                //拿到ID，对不同环境的数据源放入同样的脚本记录
                Integer newScriptId = newSqlscript.getId();
                String code = GlobalSessionFactory.getSessionFactory().getCode();
                List<Integer> dbIDS = this.dbLogRepository.findDbIdByCode(code);
                dbIDS.forEach((item)->{
                    this.executeLogRepository.addLog(newScriptId,item);
                });
            }else{
                version = file.getName();
                exc = "cause:"+exception.getCause()+";msg:"+exception.getMessage();
                executeType = LOAD_FAILED;
                sqlLogRepository.log(version,null,null,executeType,dbId,exc);
            }
        } catch (FailedException e) {
            String exc = "cause:"+e.getCause()+";msg:"+e.getMessage();
            logger.error("{}:保存日志出现异常:{}",script.getScriptVersion(),exc);
            sqlLogRepository.log(script.getScriptVersion(),null,null, LOAD_FAILED,null,exc);
            throw e;
        }
    }

    /**
     * 获取保存的所有数据源
     * @return
     */
    public List<DataBaseSourceDto> getSource(){
        List<DataBaseSource> source = this.dbLogRepository.getSource();
        return mapper.baseSourceToListDto(source);
    }

}
