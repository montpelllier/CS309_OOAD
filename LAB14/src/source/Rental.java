package source;

class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, DateRange range) {
        _movie = movie;
        _daysRented = (int)((range.end().getTime() - range.start().getTime()) / (1000 * 60 * 60 * 24));
    }
    public int getDaysRented() {
        return _daysRented;
    }

    public String getTitle() {
        return _movie.getTitle();
    }

    public int getPriceCode() {
        return _movie.getPriceCode();
    }

    public Movie getMovie() { return _movie; }

    public double getCharge() {
        return _movie.getCharge(_daysRented);
    }

    int getFrequentRenterPoints(StringBuilder result) {
        int frequentRenterPoints = 0;
        frequentRenterPoints++;
        // add bonus for a two day new release rental

        if ((getPriceCode() == Movie.NEW_RELEASE)
                && getDaysRented() > 1) frequentRenterPoints++;

        //show figures for this rental
        result.append("\t").append(getTitle());
        result.append("\t").append(String.valueOf(getCharge()));
        result.append("\n");
        return frequentRenterPoints;
    }
}
