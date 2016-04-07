import gov.nasa.jpf.util.OATHash;

import java.util.HashMap;
/**
 * Created by Nicki on 04.04.2016.
 */
public class CacheStorage {

    private static HashMap<Integer, Object> storage = new HashMap<>();

    //set to config.getBoolean("nhandler.cache”) during initialization!
    public static boolean toBeCached;

    public static void putEntry(Object caller, Object result, Object... args) {
      storage.put(getHashCode(caller, args), result);
    }

    public static boolean containsEntry(Object caller, Object... args) {
      return storage.containsKey(getHashCode(caller, args));
    }

    public Object getResult(Object caller, Object... args) {
      return storage.get(getHashCode(caller, args));
    }
    
    public static Object invokeDelegatedCall(Method method, Object caller, Object... args) {
      if(!toBeCached) {
        try {
          return method.invoke(caller, args);
        } 
        catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
          return null;
        }
      }
      if(containsEntry(caller, args)) {
        return getResult(caller, args);
      }
      else {
        try {
          Object returnVal = method.invoke(caller, args);
          putEntry(caller, returnVal, args);
          return returnVal;
        }
        catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
          return null;
        }
      }
    }
    
    //calculates and returns the hash value of the given parameters
    private static int getHashCode(Object caller, Object... args) {
      int hash;
      if(args != null && args.length != 0) {
        int[] hashCodes = new int[args.length + 1];
        hashCodes[0] = caller != null ? caller.hashCode() : 0;
        for (int i = 1; i < hashCodes.length; i++) {
        Object arg = args[i - 1];
        hashCodes[i] = arg != null ? arg.hashCode() : 0;
        }
        return OATHash.hash(hashCodes);
      }
      else {
        hash = caller != null ? caller.hashCode() : 0;
        return OATHash.hash(hash);
      }
    }
}
