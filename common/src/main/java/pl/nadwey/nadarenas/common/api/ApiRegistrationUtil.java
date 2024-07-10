package pl.nadwey.nadarenas.common.api;

import pl.nadwey.nadarenas.api.NadArenas;
import pl.nadwey.nadarenas.api.NadArenasProvider;

import java.lang.reflect.Method;

public class ApiRegistrationUtil {
    private static final Method REGISTER;
    private static final Method UNREGISTER;

    static {
        try {
            REGISTER = NadArenasProvider.class.getDeclaredMethod("register", NadArenas.class);
            REGISTER.setAccessible(true);

            UNREGISTER = NadArenasProvider.class.getDeclaredMethod("unregister");
            UNREGISTER.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registerProvider(NadArenas nadArenas) {
        try {
            REGISTER.invoke(null, nadArenas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void unregisterProvider() {
        try {
            UNREGISTER.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}