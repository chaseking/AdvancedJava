package me.chaseking.advancedjava.finalproject.user;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import me.chaseking.advancedjava.finalproject.utils.PaneHolder;

/**
 * @author Chase King
 */
public abstract class User implements PaneHolder {
    protected static final Border BORDER = new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.MEDIUM));

    private final String name;

    protected User(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}