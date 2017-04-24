package ua.zp.zsmu.ratos.learning_session.service.util;

import java.util.*;

/**
 * Created by Andrey on 13.04.2017.
 */
public class Shuffler {

        private Shuffler() {}

        /**
         * Produces a subset of the original array by randomly selecting m elements
         * using the algorithm
         * @param items
         * @param m - how many values shall be selected randomly
         * @param <T>
         * @return
         */
        public static <T> List<T> shuffle(List<T> items, int m){
                List<T> res = new ArrayList<T>(m);
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
}
