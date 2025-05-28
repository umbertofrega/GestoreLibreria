package tranfers;

import java.util.LinkedList;

public abstract class LibroAstratto implements Cloneable{

    @Override
    protected synchronized LibroAstratto clone() throws CloneNotSupportedException {
        return (LibroAstratto) super.clone();
    }
}
