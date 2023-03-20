package com.gyh.servicefile.utils;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "apikey", ignoreInvalidFields = true)
@Setter
public class AliPhoneConfig {

    private List<Map<String, String>> ali = new ArrayList<>();

    private static final String POOL_KEY="pool_key";
    private static final String PRODUCT = "dtplsapi";
    private static final String DOMAIN = "domain";
    private static final String SECRET = "key_secret";
    private static final String FILE_PATH = "file_path";
    public static final String KEY = "key_id";

    public String get(String key) {
        return ali.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }

    public List<Map<String, String>> getAli() {
        return ali;
    }

    public void setAli(List<Map<String, String>> ali) {
        this.ali = ali;
    }

    public String getPoolKey() {
        return get(POOL_KEY);
    }

    public String getPRODUCT() {
        return get(PRODUCT);
    }

    public String getDOMAIN() {
        return get(DOMAIN);
    }

    public String getSECRET() {
        return get(SECRET);
    }

    public String getFilePath() {
        return get(FILE_PATH);
    }

    public String getKEY() {
        return get(KEY);
    }
}
