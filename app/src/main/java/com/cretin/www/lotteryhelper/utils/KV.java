package com.cretin.www.lotteryhelper.utils;

import com.orhanobut.hawk.Hawk;

/**
 * key value实现,目前使用Hawk
 * Created by cretin on 3/8/16.
 */
public class KV {
    public static <T> T get(String key) {
        synchronized (KV.class) {
            return Hawk.get(key);
        }
    }

    public static <T> T get(String key, T defaultValue) {
        synchronized (KV.class) {
            return Hawk.get(key, defaultValue);
        }
    }

    public static <T> boolean put(String key, T value) {
        synchronized (KV.class) {
            return Hawk.put(key, value);
        }
    }

    public static <T> boolean remove(String key) {
        synchronized (KV.class) {
            return Hawk.remove(key);
        }
    }
}
