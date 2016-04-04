import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicki on 04.04.2016.
 */
public class CacheStorage {

    private static Map<StorageKey, Object> storage = new HashMap<>();

    public static void putEntry(Object caller, Object result, Object... args)
    {
        storage.put(new StorageKey(caller, args), result);
    }

    public static boolean containsEntry(Object caller, Object result, Object... args)
    {
        return storage.containsKey(new StorageKey(caller, args));
    }

    /**
     * Checks whether the method is specified to be cached.
     * @param caller
     * @param result
     * @param args
     * @return true if certain method needs to be cached or false otherwise
     */
    public static boolean toBeCached(Object caller, Object result, Object... args)
    {
        return config.getBoolean("nhandler.cache");
    }

    public Object getResult(Object caller, Object... args)
    {
        return storage.get(new StorageKey(caller, args));
    }
}
