package org.arteco.sersoc.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Data
public class DataService {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(value = "oneColumnCache", key = "{#sql, #args}")
    public Object selectOneColumn(String sql, Object ...args){
        logger.info("Retrieving data from the database for sql: {} with args: {}", sql, Arrays.toString(args));
        return jdbcTemplate.queryForObject(sql, Object.class, args);
    }

    @Cacheable(value = "oneRowCache", key = "{#sql, #args}")
    public Map<String, Object> selectOneRow(String sql, Object ...args){
        logger.info("Retrieving data from the database for sql: {} with args: {}", sql, Arrays.toString(args));
        return jdbcTemplate.queryForMap(sql, args);
    }

    @Cacheable(value = "manyRowsCache", key = "{#sql, #args}")
    public List<Map<String, Object>> selectMany(String sql, Object ...args){
        logger.info("Retrieving data from the database for sql: {} with args: {}", sql, Arrays.toString(args));
        return jdbcTemplate.queryForList(sql, args);
    }
}
