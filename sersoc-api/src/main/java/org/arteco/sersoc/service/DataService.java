package org.arteco.sersoc.service;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class DataService {

    private final JdbcTemplate jdbcTemplate;

    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Object selectOneColumn(String sql, Object ...args){
        return jdbcTemplate.queryForObject(sql, Object.class, args);
    }

    public Map<String, Object> selectOneRow(String sql, Object ...args){
        return jdbcTemplate.queryForMap(sql, args);
    }

    public List<Map<String, Object>> selectMany(String sql, Object ...args){
        return jdbcTemplate.queryForList(sql, args);
    }
}
