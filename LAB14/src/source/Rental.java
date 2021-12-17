package source;

import java.util.Date;

class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, Date start, Date end) {
        _movie = movie;
        _daysRented = (int)((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
    }
    public int getDaysRented() {
        return _daysRented;
    }

    public String getTitle() {
        return _movie.getTitle();
    }
    
    public int getPriceCode() {
        return _movie.get_priceCode();
    }
}
