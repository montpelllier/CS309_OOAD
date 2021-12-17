package source;

public abstract class Price {
    public abstract int getPriceCode();

    abstract double getCharge(int daysRented);

    int getFrequentRenterPoints(){
        return 1;
    }
}
