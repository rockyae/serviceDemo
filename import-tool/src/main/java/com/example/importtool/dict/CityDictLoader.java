package com.example.importtool.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 加载市/区字典 JSON。
 * <p>优先级：配置 {@code import.dict.path} → 系统属性 {@code import.dict.path}
 * → 环境变量 {@code IMPORT_DICT_PATH} → classpath {@code data/city-dict.json}。</p>
 */
@Component
public class CityDictLoader {

    static final String CLASSPATH_DEFAULT = "data/city-dict.json";
    private static final String SYS_PROP = "import.dict.path";
    private static final String ENV_PATH = "IMPORT_DICT_PATH";

    private final ObjectMapper objectMapper;
    private final String configuredPath;

    public CityDictLoader(ObjectMapper objectMapper,
                          @Value("${import.dict.path:}") String configuredPath) {
        this.objectMapper = objectMapper;
        this.configuredPath = configuredPath;
    }

    public CityDictEnvelope load() throws IOException {
        Path external = resolveExternalPath();
        if (external != null) {
            try (InputStream in = Files.newInputStream(external)) {
                return objectMapper.readValue(in, CityDictEnvelope.class);
            }
        }
        Resource cp = new ClassPathResource(CLASSPATH_DEFAULT);
        if (!cp.exists()) {
            throw new IOException("未找到字典文件：请设置 import.dict.path / " + ENV_PATH
                    + " 指向完整 JSON，或将文件放到 classpath:" + CLASSPATH_DEFAULT);
        }
        try (InputStream in = cp.getInputStream()) {
            return objectMapper.readValue(in, CityDictEnvelope.class);
        }
    }

    private Path resolveExternalPath() {
        String p = configuredPath;
        if (p == null || p.isBlank()) {
            p = System.getProperty(SYS_PROP);
        }
        if (p == null || p.isBlank()) {
            p = System.getenv(ENV_PATH);
        }
        if (p == null || p.isBlank()) {
            return null;
        }
        Path path = Path.of(p.trim());
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("字典路径不是可读文件: " + path.toAbsolutePath());
        }
        return path;
    }
}
