package com.yonyou.databasebootstrap.service.mapper;

import com.yonyou.databasebootstrap.entity.DataBaseSource;
import com.yonyou.databasebootstrap.service.dto.DataBaseSourceDto;
import com.yonyou.databasebootstrap.service.pojo.DataPoolSource;
import com.yonyou.databasebootstrap.utils.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Ember
 * @Date: 2021/8/13 17:10
 * @Description: 数据源转换类
 */
@Component
public class DataBaseSourceMapper {


    protected DataBaseSource baseDo(DataPoolSource poolSource){
        DataBaseSource dataBaseSource = new DataBaseSource();
        dataBaseSource.setSourceName(poolSource.getSourceName());
        dataBaseSource.setHost(poolSource.getHost());
        dataBaseSource.setPort(poolSource.getPort());
        dataBaseSource.setDriver(poolSource.getDriver());
        dataBaseSource.setUserName(poolSource.getUserName());
        dataBaseSource.setPassword(poolSource.getPassword());
        dataBaseSource.setDataBaseName(poolSource.getDataBaseName());
        dataBaseSource.setStatus(poolSource.getStatus());
        return dataBaseSource;
    }

    /**
     * 针对第一次添加数据库（没有其他环境）
     * @param poolSource
     * @return
     */
    public DataBaseSource poolSourceMapper(DataPoolSource poolSource){
        DataBaseSource dataBaseSource = baseDo(poolSource);
        dataBaseSource.setCode(RandomUtils.getUUID());
        return dataBaseSource;
    }

    /**
     * 已经有其他环境的数据库进行转化
     * @param poolSource
     * @param code
     * @return
     */
    public DataBaseSource poolSourceMapper(DataPoolSource poolSource,String code){
        DataBaseSource dataBaseSource = baseDo(poolSource);
        dataBaseSource.setCode(code);
        return dataBaseSource;
    }

    /**
     * 数据源转化为前端显示的DTO
     * @param dataBaseSource
     * @return
     */
    public DataBaseSourceDto baseSourceToDto(DataBaseSource dataBaseSource){
        DataBaseSourceDto dataBaseSourceDto = new DataBaseSourceDto();
        dataBaseSourceDto.setId(dataBaseSource.getId());
        dataBaseSourceDto.setStatus(dataBaseSource.getStatus());
        dataBaseSourceDto.setDriver(dataBaseSource.getDriver());
        dataBaseSourceDto.setDataBaseName(dataBaseSource.getDataBaseName());
        dataBaseSourceDto.setHost(dataBaseSource.getHost());
        dataBaseSourceDto.setPort(dataBaseSource.getPort());
        dataBaseSourceDto.setCreateTime(dataBaseSource.getCreateTime());
        return dataBaseSourceDto;
    }

    /**
     * DataBaseSource批量转化DTO
     * @param sources
     * @return
     */
    public List<DataBaseSourceDto> baseSourceToListDto(List<DataBaseSource> sources){
        List<DataBaseSourceDto> dataBaseSourceDtos  = new LinkedList<>();
        sources.forEach((item) -> {
            DataBaseSourceDto dataBaseSourceDto = baseSourceToDto(item);
            dataBaseSourceDtos.add(dataBaseSourceDto);
        });
        return dataBaseSourceDtos;
    }
}
