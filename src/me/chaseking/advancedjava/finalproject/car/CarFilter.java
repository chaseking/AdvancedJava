package me.chaseking.advancedjava.finalproject.car;

/**
 * @author Chase King
 */
public interface CarFilter {
    CarFilter ALL = new CarFilter(){
        @Override
        public boolean willShow(Car car){
            return true;
        }

        @Override
        public String toString(){
            return "All";
        }
    };

    boolean willShow(Car car);
}