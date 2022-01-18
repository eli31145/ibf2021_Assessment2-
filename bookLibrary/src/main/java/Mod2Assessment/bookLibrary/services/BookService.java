package Mod2Assessment.bookLibrary.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import Mod2Assessment.Constants;
import Mod2Assessment.bookLibrary.Model.*;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class BookService {
    
        
        private final String appId;

        //create constructor to set env
        public BookService(){
            String k = System.getenv(Constants.enVariable);
            if ((k != null) && (k.trim().length()>0))
                appId = k;
            else 
                appId = null;
        }

        public List<Book> search(String bookName){
            final String url = UriComponentsBuilder
                .fromUriString(Constants.BOOK_API)
                .queryParam("q", convertTitle(bookName))
                //gets you title and 20 results, desc & exerpt not included
                .queryParam("fields", "key+title&limit=20")
                .toUriString();

            RequestEntity<Void> req = RequestEntity.get(url).build();
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            
            if (resp.getStatusCode() != HttpStatus.OK)
                throw new IllegalArgumentException(
                    String.format("Error: Status code %s", resp.getStatusCode().toString())
                );
                
                final String body = resp.getBody();
            
            try (InputStream is = new ByteArrayInputStream(body.getBytes())){
                JsonReader reader = Json.createReader(is);
                JsonObject jO = reader.readObject();

                //extract information from json object based in search result
                JsonArray jA = jO.getJsonArray("docs");

                List<Book> bookList = new LinkedList<>();
                
                for(JsonValue v: jA){
                    JsonObject jObject = (JsonObject) v;
                    //use method created to convert jO to book obj
                    Book b = Book.create(jObject);
                    bookList.add(b);
                    return bookList; 
                }

            } catch (Exception e){

            } return Collections.emptyList();


        }


        public static String convertTitle(String bookName){
            return bookName.trim().replaceAll(" ", "+");
        }

}
