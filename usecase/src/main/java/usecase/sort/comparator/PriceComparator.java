package usecase.sort.comparator;

import domain.entity.PriceComparable;

import java.util.Comparator;

public class PriceComparator implements Comparator<PriceComparable> {

    @Override
    public int compare(PriceComparable o1, PriceComparable o2) {
        float p1 = o1.getPrice();
        float p2 = o2.getPrice();

        if(p1<p2){return -1;} else if(p1 == p2){return 0;}

        return 1;
    }
}
