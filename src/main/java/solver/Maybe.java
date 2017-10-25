package solver;

public class Maybe<A> {

    public A result;
    public boolean hadError;
    public String errorMessage;


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
        return "solver.Maybe[ hadError=" + hadError + ", errMessage=" + errorMessage + ", result=" + result + "]";
    }
}
