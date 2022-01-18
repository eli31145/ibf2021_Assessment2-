package Mod2Assessment.bookLibrary.repositories;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import Mod2Assessment.bookLibrary.Model.Book;

@Repository
public class BookRepository {
    @Autowired 
    //change bean name
        @Qualifier("TODO_REDIS")
        private RedisTemplate<String, String> template;

        public void save(String key, String value){
            template.opsForValue().set(key, value, 10, TimeUnit.MINUTES);    
        }
        
        public Optional<Book> getBook(String bookName){
        return Optional.ofNullable(
             template.opsForValue().get(bookName));
        } 
}
