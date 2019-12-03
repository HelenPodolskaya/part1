package task1;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class FactorialRunnable implements Callable<BigInteger> {
    private Integer numberForFactorial;
    private Integer startNumber;
    private BigInteger muliplyValue;

    public FactorialRunnable(Integer startNumb,Integer number,BigInteger multValue) {
        numberForFactorial = number;
        startNumber = startNumb;
        muliplyValue = multValue;
    }

    private BigInteger getNumbFactorial(Integer startNumber,Integer stopNumber,BigInteger multValue) {
        BigInteger factorialNumber = multValue;
        for (int i = startNumber; i <= stopNumber; i++) {
            factorialNumber = factorialNumber.multiply(BigInteger.valueOf(i));
        }
        return factorialNumber;
    }

    @Override
    public BigInteger call() throws Exception {
        return getNumbFactorial(startNumber,numberForFactorial,muliplyValue);
    }
}
