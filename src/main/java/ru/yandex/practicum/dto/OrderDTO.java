package ru.yandex.practicum.dto;

public class OrderDTO {

    private String[] ingredients;

    public OrderDTO(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
