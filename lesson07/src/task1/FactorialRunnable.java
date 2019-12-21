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
    }

    private BigInteger getNumbFactorial() {
        startNumber = sm.getNearestKey(numberForFactorial);
        multiplyValue = BigInteger.valueOf(1l);
        if (sm.isContainsKey(startNumber)) {
            multiplyValue = sm.get(startNumber);
        }
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
