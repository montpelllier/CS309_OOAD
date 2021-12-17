public class NewReleasePrice extends Price{
    public int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    @Override
    double getCharge(int daysRented) {
        //determine amounts for each line
        double result = 0;
        result += daysRented * 3;
        return result;
    }

    @Override
    int getFrequentRenterPoints(int daysRented) {
        if (daysRented > 1) return 2;
        else return 1;
    }
}