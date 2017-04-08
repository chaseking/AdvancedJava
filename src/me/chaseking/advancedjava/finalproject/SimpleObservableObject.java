package me.chaseking.advancedjava.finalproject;

import javafx.beans.property.ObjectPropertyBase;

import java.util.function.Supplier;

/**
 * @author Chase King
 */
public class SimpleObservableObject<T> extends ObjectPropertyBase<T> {
    private final Supplier<T> supplier;

    public SimpleObservableObject(Supplier<T> supplier){
        this.supplier = supplier;
    }

    public SimpleObservableObject(T value){
        this(() -> value);
    }

    @Override
    public T get(){
        return supplier.get();
    }

    @Override
    public Object getBean(){
        return null;
    }

    @Override
    public String getName(){
        return null;
    }
}