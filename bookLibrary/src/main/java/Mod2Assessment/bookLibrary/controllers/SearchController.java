package Mod2Assessment.bookLibrary.controllers;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Mod2Assessment.bookLibrary.Model.Book;
import Mod2Assessment.bookLibrary.services.BookService;

@Controller
@RequestMapping (path = "/searchResult")
public class SearchController {
    
    @Autowired
    private BookService bookSvc;

    @GetMapping
    public String searchBook(@RequestParam(required = true) String bookName, Model model){

    List<Book> bookList = Collections.emptyList();

          //previous code without checking cache 
      System.out.println("book: " + bookName);
        try{
            //getting weather
            bookSvc.search(bookName);
        } catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("bookName", bookName);

  /*   try{bookList = bookSvc.search(bookName)
        if (bookList.size()>0
            bookCache.save(bookName, bookList));
        } catch (Exception e){
            Logger.log(Level.WARNING, String.format("Warning: %s", e.getMessage()));
        }
         */


        return "searchResult";
    }




}
