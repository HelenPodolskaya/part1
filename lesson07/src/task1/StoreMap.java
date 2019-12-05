package task1;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class StoreMap {
    public ConcurrentHashMap<Integer, BigInteger> numbersFactorialMap = new ConcurrentHashMap<>();

    public synchronized void put(Integer key, BigInteger value) {
        numbersFactorialMap.putIfAbsent(key, value);
    }

    public synchronized BigInteger get(Integer key) {
        if (numbersFactorialMap.containsKey(key))
            return numbersFactorialMap.get(key);
        else return BigInteger.valueOf(1l);
    }

    public synchronized boolean isContainsKey(Integer key) {
        return numbersFactorialMap.containsKey(key);
    }

    public synchronized Integer getKey(Integer numb) {
        Iterator<Integer> iterator = numbersFactorialMap.keySet().iterator();
        Integer key = 1;
        while ((iterator.hasNext())) {
            Integer nextKey = iterator.next();
            if (nextKey <= numb && nextKey > key)
                key = nextKey;
        }
        return key;
    }
}
