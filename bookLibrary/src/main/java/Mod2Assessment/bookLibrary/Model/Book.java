package Mod2Assessment.bookLibrary.Model;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Book {

    private String key;
    private String title;

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

    public static Book create(JsonObject jObject){
        Book b = new Book();
        //set the array info inside book obj
        b.setKey(jObject.getString("key"));
        b.setTitle(jObject.getString("title"));
        return b;
    }


    
}
