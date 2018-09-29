//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.os;

public class SystemProperties {
    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;

    public SystemProperties() {
    }

    private static String native_get(String var0) {
        return (String) OverrideMethod.invokeA("android.android.os.SystemProperties#native_get(Ljava/lang/String;)Ljava/lang/String;", true, (Object)null);
    }

    private static String native_get(String var0, String var1) {
        return (String) OverrideMethod.invokeA("android.android.os.SystemProperties#native_get(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", true, (Object)null);
    }

    private static int native_get_int(String var0, int var1) {
        return OverrideMethod.invokeI("android.android.os.SystemProperties#native_get_int(Ljava/lang/String;I)I", true, (Object)null);
    }

    private static long native_get_long(String var0, long var1) {
        return OverrideMethod.invokeL("android.android.os.SystemProperties#native_get_long(Ljava/lang/String;J)J", true, (Object)null);
    }

    private static boolean native_get_boolean(String var0, boolean var1) {
        return OverrideMethod.invokeI("android.android.os.SystemProperties#native_get_boolean(Ljava/lang/String;Z)Z", true, (Object)null) != 0;
    }

    private static void native_set(String var0, String var1) {
        OverrideMethod.invokeV("android.android.os.SystemProperties#native_set(Ljava/lang/String;Ljava/lang/String;)V", true, (Object)null);
    }

    public static String get(String key) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else {
            return native_get(key);
        }
    }

    public static String get(String key, String def) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else {
            return native_get(key, def);
        }
    }

    public static int getInt(String key, int def) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else {
            return native_get_int(key, def);
        }
    }

    public static long getLong(String key, long def) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else {
            return native_get_long(key, def);
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else {
            return native_get_boolean(key, def);
        }
    }

    public static void set(String key, String val) {
        if(key.length() > 31) {
            throw new IllegalArgumentException("key.length > 31");
        } else if(val != null && val.length() > 91) {
            throw new IllegalArgumentException("val.length > 91");
        } else {
            native_set(key, val);
        }
    }
}
