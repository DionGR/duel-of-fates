package no.ntnu.dof.view;

import lombok.Getter;

@Getter
public class Card_View extends Image{

    private String name;
    private String description;
    private int cost;

    public Card_View(String path, float Scale, String name, String description, int cost) {
        super(path, Scale);
        this.name = name;
        this.description = description;
        this.cost = cost;
    }


}
