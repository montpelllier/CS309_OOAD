package source;

class Rental {
    private final Movie movie = new Movie("movie_title", Movie.REGULAR);

    private int _daysRented;
    public Rental(String title, int priceCode, DateRange dateRange) {
        movie.setTitle(title);
        movie.set_priceCode(priceCode);
        _daysRented = (int)((dateRange.end().getTime() - dateRange.start().getTime()) / (1000 * 60 * 60 * 24));
    }
    public int getDaysRented() {
        return _daysRented;
    }

    public String getTitle() {
        return movie.getTitle();
    }
    
    public int getPriceCode() {
        return movie.get_priceCode();
    }
}
