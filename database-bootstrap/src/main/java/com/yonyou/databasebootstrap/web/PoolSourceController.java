package com.yonyou.databasebootstrap.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.databasebootstrap.service.PoolSourceService;
import com.yonyou.databasebootstrap.service.exception.FailedException;
import com.yonyou.databasebootstrap.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ember
 * @Date: 2021/8/11 14:19
 * @Description: 数据源Controller
 */
@RestController
@RequestMapping("/davco/database-bootstrap")
public class PoolSourceController {

    private PoolSourceService poolSourceService;

    @Autowired
    public void setPoolSourceService(PoolSourceService poolSourceService) {
        this.poolSourceService = poolSourceService;
    }

    /**
     * 获取当前数据库的所有待执行或已经执行脚本情况
     * @return
     * @throws FailedException
     */
    @GetMapping("/getAllScript")
    public ResponseUtils getAll() throws FailedException {
        return ResponseUtils.success(this.poolSourceService.getAll());
    }

    /**
     * 设置连接池
     * @param source
     * @return
     */
    @PostMapping("/setup")
    public ResponseUtils setUpSource(@RequestBody JSONObject source) throws FailedException {
        this.poolSourceService.createPool(source);
        return ResponseUtils.success();
    }

    /**
     * 测试连接
     * @param source
     * @return
     * @throws FailedException
     */
    @PostMapping("/test")
    public ResponseUtils test(@RequestBody JSONObject source) throws FailedException {
        this.poolSourceService.testConnect(source);
        return ResponseUtils.success();
    }

    /**
     * 上传Sql脚本
     * @param file
     * @return
     */
    @PostMapping("/upload-sqlScript")
    public ResponseUtils upload(MultipartFile file) throws Exception {
        this.poolSourceService.upload(file);
        return ResponseUtils.success();
    }

    /**
     * 获取所有记录的源
     * @return
     */
    @GetMapping("/getSources")
    public ResponseUtils getSources(){
        return ResponseUtils.success(this.poolSourceService.getSource());
    }

    /**
     * 执行指定脚本
     * @param scriptId
     * @param executeType
     * @return
     * @throws FailedException
     */
    @PostMapping("/execute")
    public ResponseUtils execute(@RequestBody JSONObject idAndType) throws FailedException {
        Integer scriptId = idAndType.getInteger("scriptId");
        Integer executeType = idAndType.getInteger("executeType");
        this.poolSourceService.execute(scriptId,executeType);
        return ResponseUtils.success();
    }

    /**
     * 为当前数据库添加新环境
     * @param source
     * @return
     * @throws FailedException
     */
    @PostMapping("/addEnvironment")
    public ResponseUtils addEnvironment(@RequestBody JSONObject source) throws FailedException {
        this.poolSourceService.addEnvironment(source);
        return ResponseUtils.success();
    }
}
