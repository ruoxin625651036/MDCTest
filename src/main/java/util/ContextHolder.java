/**
 * Baifubao.com,Inc.
 * Copyright (c) 2017-2018 All Rights Reserved.
 */
package util;

import java.util.HashMap;
import java.util.Map;

/**
 * a little description
 *
 * @author dyj
 */
public class ContextHolder {
    private static ThreadLocal<Map<String, String>> context = new ThreadLocal<Map<String, String>>();

    public static void put(String key, String value) {
        Map<String, String> values = context.get();
        if (null == values) {
            values = new HashMap<String, String>();
            context.set(values);
        }
        values.put(key, value);
    }

    public static Map<String, String> get() {
        return context.get();
    }


}
