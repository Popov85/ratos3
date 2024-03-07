package ua.edu.ratos.service.utils;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @link https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 */
@Component
public class CollectionShuffler {

    /**
     * Adopts a Fisher–Yates truncated shuffle algorithms
     * @param items source collection for shuffling
     * @param m the size of new collection derived from source collection
     * @param <T> any obj
     * @return shuffled and reduced in size list
     */
    public <T> List<T> shuffle(Collection<T> items, int m){
        if (m > items.size())
            throw new RuntimeException("Shuffling error: new collection size (m) cannot be greater than the source collection size");
        List<T> result = new ArrayList<>(m);
        Random rnd = new Random();
        List<T> source = new ArrayList<>(items);
        int n = source.size();
        for(int i=n-m;i<n;i++){
            int pos = rnd.nextInt(i+1);
            T item = source.get(pos);
            if (result.contains(item))
                result.add(source.get(i));
            else
                result.add(item);
        }
        return result;
    }

    /**
     * Standard Java Collection shuffler wrapper
     * @param items
     * @param <T>
     * @return shuffled list
     */
    public <T> List<T> shuffle(Collection<T> items){
        List<T> result = new ArrayList<>(items);
        Collections.shuffle(result);
        return result;
    }
}
