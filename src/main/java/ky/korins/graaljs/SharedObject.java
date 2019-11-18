package ky.korins.graaljs;

import java.util.HashMap;
import java.util.Map;

public class SharedObject {

    private static final SharedObject instance = new SharedObject();

    private SharedObject() {

    }

    public static SharedObject getInstance(){
        return instance;
    }

    public final Map<String, Object> map = new HashMap<>();

    public transient long counter;
}
