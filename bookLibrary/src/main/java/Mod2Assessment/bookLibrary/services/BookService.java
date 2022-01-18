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

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {
    
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
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
                .queryParam("fields", "key+title")
                .queryParam("limit", "20")
                .toUriString();
                
        logger.log(Level.INFO, String.format("URL: %s", url));

            RequestEntity<Void> req = RequestEntity.get(url).build();
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            
            if (resp.getStatusCode() != HttpStatus.OK)
                throw new IllegalArgumentException(
                    String.format("Error: Status code %s", resp.getStatusCode().toString())
                );
                
                final String body = resp.getBody();

        logger.log(Level.INFO, String.format("body: %s", body));

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
                    
                }

                return bookList; 

            } catch (Exception e){

            } return Collections.emptyList();


        }

        public Book searchSpecificBook(String key, String bookName){
           
            final String url = UriComponentsBuilder
                .fromUriString(Constants.INDIVIDUAL_BOOK_API)
                .pathSegment(bookName + ".json")
                .toUriString();
                
        logger.log(Level.INFO, String.format("URL: %s", url));

            RequestEntity<Void> req = RequestEntity.get(url).build();
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            
            if (resp.getStatusCode() != HttpStatus.OK)
                throw new IllegalArgumentException(
                    String.format("Error: Status code %s", resp.getStatusCode().toString())
                );
                
                final String body = resp.getBody();

        logger.log(Level.INFO, String.format("body: %s", body));

            try (InputStream is = new ByteArrayInputStream(body.getBytes())){
                JsonReader reader = Json.createReader(is);
                JsonObject jO = reader.readObject();

                //extract description, Exerpt and Cached
                //JsonArray jA = jO.getJsonArray("docs");
                String description = jO.getJsonObject("description").getString("value");
                String title = jO.getString("title");

                Book book = new Book();
                //not every book has exerpts, set only if exists
                if (jO.containsKey("exerpts")){
                String exerpt = jO.getJsonArray("exerpts").getJsonObject(0).getString("exerpt");
                book.setExerpt(exerpt);
               }

                book.setTitle(title);
                book.setDescription(description);

                return book;

            } catch (Exception e){

            }
           return null;
        }


        public static String convertTitle(String bookName){
            return bookName.trim().replaceAll(" ", "+");
        }

}
