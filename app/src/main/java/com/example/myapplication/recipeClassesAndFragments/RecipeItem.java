package com.example.myapplication.recipeClassesAndFragments;

public class RecipeItem {

    private String foodName;
    private String url;
    private String time;

    public RecipeItem(){
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
