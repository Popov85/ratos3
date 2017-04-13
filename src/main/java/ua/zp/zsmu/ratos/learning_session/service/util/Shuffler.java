package ua.zp.zsmu.ratos.learning_session.service.util;

import java.util.*;

/**
 * Created by Andrey on 13.04.2017.
 */
public class Shuffler {

        private Shuffler() {}

        public static <T> Set<T> shuffle(List<T> items, int m){
                HashSet<T> res = new HashSet<T>(m);
                Random rnd = new Random();
                int n = items.size();
                for(int i=n-m;i<n;i++){
                        int pos = rnd.nextInt(i+1);
                        T item = items.get(pos);
                        if (res.contains(item))
                                res.add(items.get(i));
                        else
                                res.add(item);
                }
                return res;
        }

        public static <T> Set<T> shuffleComposite(HashMap<Integer, List<T>> items, int m, int n){
                HashSet<T> res = new HashSet<T>(n);
                for (List<T> ts : items.values()) {
                        Set<T> subset = shuffle(ts, m);
                        res.addAll(subset);
                }
                return res;
        }

}
