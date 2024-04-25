package org.arteco.sersoc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Service
public class NashornService {

    private final ScriptEngine engine;

    public NashornService() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        this.engine = scriptEngineManager.getEngineByName("nashorn");
    }

    public void putInContext(String key, Object value) {
        // Añade un objeto al contexto de scripts
        engine.put(key, value);
    }

    public Object getFromContext(String key) {
        // Recupera un objeto del contexto
        return engine.get(key);
    }

    public Object executeScript(String script) throws ScriptException {
        // Ejecuta el script de JavaScript y devuelve el resultado
        return engine.eval(script);
    }

    public String serializeObjectToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
