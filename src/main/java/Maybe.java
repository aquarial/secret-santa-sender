public class Maybe<A> {

    boolean isError;
    String errorMessage;
    A result = null;


    Maybe(A item) {
        this.isError = false;
        this.errorMessage = null;
        this.result = item;
    }

    @SuppressWarnings("unused")
    Maybe(String isError, String errorMessage) {
        this.isError = true;
        this.errorMessage = errorMessage;
        this.result = null;
    }

    @Override
    public String toString() {
        return "Maybe[ isError=" + isError + ", errMessage=" + errorMessage + ", result=" + result + "]";
    }
}
