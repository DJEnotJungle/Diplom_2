package ru.yandex.practicum.jsonclass;

public class OrderJson {

    private String[] ingredients;

    public OrderJson(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
