package Mod2Assessment.bookLibrary.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Mod2Assessment.bookLibrary.Model.Book;
import Mod2Assessment.bookLibrary.services.BookCache;
import Mod2Assessment.bookLibrary.services.BookService;

@Controller
@RequestMapping (path = "/searchResult")
public class SearchController {

    @Autowired private BookService bookSvc;
    @Autowired private BookCache bookCache;

    @GetMapping
    public String searchBook(@RequestParam(required = true) String bookName, Model model){

    Optional<List<Book>> opt = bookCache.get(bookName);
    List<Book> bookList = Collections.emptyList();
      
    if (opt.isPresent()){
        bookList = opt.get();
    } else 
        try {
            //bookList = bookSvc.getBook(bookName);
            if (bookList.size()>0)
            bookCache.save(bookName, bookList);
        } catch (Exception e){
            //logger.log(Level.WARNING, String.format("Warning: %s", e.getMessage()));
        }


        try{
            //getting bookName
            bookList = bookSvc.search(bookName);
        } catch (Exception e){
            e.printStackTrace();
        }
    
    //returns all items in the booklist
        if (bookList.size()>0){
            for (int i = 0; i<bookList.size(); i++)
            bookList.get(i);
        }

        model.addAttribute("bookName", bookName);
        model.addAttribute("bookList", bookList);


        return "searchResult";
    }




}
