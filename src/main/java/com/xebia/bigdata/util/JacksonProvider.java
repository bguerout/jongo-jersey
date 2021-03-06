package com.xebia.bigdata.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JacksonProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonProvider() {
        mapper = createMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        VisibilityChecker<?> checker = mapper.getSerializationConfig().getDefaultVisibilityChecker();
        mapper.setVisibilityChecker(checker.withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY));

        mapper.registerModule(new SimpleModule("jersey"));

        return mapper;
    }

}
