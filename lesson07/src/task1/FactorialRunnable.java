package task1;

import java.math.BigInteger;

public class FactorialRunnable implements Runnable {
    private Integer numberForFactorial;
    private Integer startNumber;
    private BigInteger multiplyValue;
    private StoreMap sm;

    public FactorialRunnable(Integer number, StoreMap storeMap) {
        numberForFactorial = number;
        this.sm = storeMap;
        startNumber = storeMap.getKey(number);
        multiplyValue = BigInteger.valueOf(1l);
        if (storeMap.isContainsKey(startNumber)) {
            multiplyValue = storeMap.get(startNumber);
        }
    }

    private BigInteger getNumbFactorial() {
        BigInteger factorialNumber = multiplyValue;
        for (int i = startNumber; i <= numberForFactorial; i++) {
            factorialNumber = factorialNumber.multiply(BigInteger.valueOf(i));
        }
        return factorialNumber;
    }

    @Override
    public void run() {
        sm.put(numberForFactorial, getNumbFactorial());
    }
}
