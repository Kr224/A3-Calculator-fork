/**Refactored this class*/
package com.houarizegai.calculator.theme;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.houarizegai.calculator.theme.properties.Theme;
import com.houarizegai.calculator.theme.properties.ThemeList;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class ThemeLoader {

    private ThemeLoader() {
        throw new AssertionError("Constructor is not allowed");
    }

    public static Map<String, Theme> loadThemes() {
        ObjectMapper mapper = createObjectMapper();
        return readThemes(mapper);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper;
    }

    private static Map<String, Theme> readThemes(ObjectMapper mapper) {
        try {
            ThemeList themeList = mapper.readValue(new File("src/main/resources/application.yaml"), ThemeList.class);
            return themeList.getThemesAsMap();
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }
}
