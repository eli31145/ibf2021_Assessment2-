package Mod2Assessment.bookLibrary.Model;

import jakarta.json.JsonObject;

public class Book {

    private String key;
    private String title;
    private String description;
    private String exerpt;
    private boolean isCached;

    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getExerpt(){
        return exerpt;
    }
    public void setExerpt(String exerpt){
        this.exerpt = exerpt;
    }
    
    public boolean getIsCached(){
        return isCached;
    }
    public void setIsCached(boolean isCached){
        this.isCached = isCached;
    }

    public boolean fromIsCached() {
        return this.isCached;
    }

    public static Book create(JsonObject jObject){
        Book b = new Book();
        //set the array info inside book obj
        b.setKey(jObject.getString("key"));
        b.setTitle(jObject.getString("title"));
        return b;
    }


    
}
