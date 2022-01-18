package Mod2Assessment.bookLibrary.services;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Mod2Assessment.bookLibrary.Model.Book;
import Mod2Assessment.bookLibrary.repositories.BookRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@Service
public class BookCache {
    

    @Autowired
    private BookRepository bookRepo;

    //convert the book List into Json format and .toString()
    public void save(String city, List<Book> book){
        JsonArrayBuilder jAb = Json.createArrayBuilder();
        //for (JsonValue v : book){
           // jAb.add(v.toJson());
            bookRepo.save(city, jAb.build().toString());
        //}
    }

    //get city from Optional, if optional return empty, otherwise get string
    //create and return to us weather
    public Optional<List<Book>> get(String bookName){
        //Optional<String> opt = bookRepo.getBook(bookName);
        //if (opt.isEmpty())
            return Optional.empty();

    }
    
}
