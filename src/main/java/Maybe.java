public class Maybe<A> {

    A result;
    boolean hadError;
    String errorMessage;


    Maybe(A item) {
        this.hadError = false;
        this.errorMessage = null;
        this.result = item;
    }

    @SuppressWarnings("unused")
    Maybe(String hadError, String errorMessage) {
        this.hadError = true;
        this.errorMessage = errorMessage;
        this.result = null;
    }

    @Override
    public String toString() {
        return "Maybe[ hadError=" + hadError + ", errMessage=" + errorMessage + ", result=" + result + "]";
    }
}
