import java.util.Arrays;

/**
 * Created by Nicki on 04.04.2016.
 */
public class StorageKey
{
    private Object caller;
    Object[] args;

    public StorageKey(Object caller, Object... args) {
        this.caller = caller;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageKey that = (StorageKey) o;

        if (caller != null ? !caller.equals(that.caller) : that.caller != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, that.args);

    }

    @Override
    public int hashCode() {
        int result = caller != null ? caller.hashCode() : 0;
        result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
        return result;
    }
}
