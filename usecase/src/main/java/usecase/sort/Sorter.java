package usecase.sort;

import domain.entity.PriceComparable;
import domain.entity.Ratable;
import org.jetbrains.annotations.NotNull;
import usecase.sort.comparator.*;

import java.util.List;


public final class Sorter {

    private final PriceComparator priceComparator;
    private final RatingComparator ratingComparator;

    public Sorter(){
        this.priceComparator = new PriceComparator();
        this.ratingComparator = new RatingComparator();
    }

    // mutates input, TODO: discuss alternative
    public void sortByPrice(@NotNull List<? extends PriceComparable> lst){
        lst.sort(priceComparator);
    }

    public void sortByRating(@NotNull List<? extends Ratable> lst){
        lst.sort(ratingComparator);
    }

}
