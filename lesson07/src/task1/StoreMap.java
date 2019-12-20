package task1;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreMap {
    private ConcurrentHashMap<Integer, BigInteger> numbersFactorialMap = new ConcurrentHashMap<>();

    public void put(Integer key, BigInteger value) {
        numbersFactorialMap.putIfAbsent(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, BigInteger> entry : numbersFactorialMap.entrySet()) {
            sb.append("Число ");
            sb.append(entry.getKey());
            sb.append("Факториал: ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public BigInteger get(Integer key) {
        if (numbersFactorialMap.containsKey(key))
            return numbersFactorialMap.get(key);
        else return BigInteger.valueOf(1l);
    }

    public boolean isContainsKey(Integer key) {
        return numbersFactorialMap.containsKey(key);
    }

    public Integer getNearestKey(Integer numb) {
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