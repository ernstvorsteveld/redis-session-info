package nl.vorstdev.db.sessionstore;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionData {

    private Map<String, String> values = Maps.newHashMap();

    public Map<String, String> getValues() {
        return values;
    }
}
