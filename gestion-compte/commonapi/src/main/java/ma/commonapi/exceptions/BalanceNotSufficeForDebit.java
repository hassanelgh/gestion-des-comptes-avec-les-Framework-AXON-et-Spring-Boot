package ma.commonapi.exceptions;

public class BalanceNotSufficeForDebit extends RuntimeException {
    public BalanceNotSufficeForDebit(String s) {
        super(s);
    }
}
