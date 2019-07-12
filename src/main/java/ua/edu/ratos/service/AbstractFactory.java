package ua.edu.ratos.service;

import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractFactory<K, V extends NamedService<K>> {

    private final Map<K, V> map;

    protected AbstractFactory(List<V> list) {
        this.map = list
                .stream()
                .collect(Collectors.toMap(NamedService::name, Function.identity()));
    }

    /**
     * Factory method for getting an appropriate implementation of a service
     * @param name name of service impl.
     * @return concrete service impl.

     */
    public V getInstance(@NonNull final K name) {
        V t = map.get(name);
        if(t == null)
            throw new RuntimeException("Unknown service name: " + name);
        return t;
    }
}
