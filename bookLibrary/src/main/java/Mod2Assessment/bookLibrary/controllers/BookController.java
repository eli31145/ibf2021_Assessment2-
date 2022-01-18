package Mod2Assessment.bookLibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Mod2Assessment.bookLibrary.Model.Book;
import Mod2Assessment.bookLibrary.services.BookService;

@Controller
@RequestMapping (path = "/searchResult")
public class BookController {

    @Autowired private BookService bookSvc;
    
    @GetMapping(path ="{key}")
    public String searchSpecificBook(@PathVariable("key") String key, String bookName, Model model){

        Book book = bookSvc.searchSpecificBook(key, bookName);
        
        model.addAttribute("book", book);

        return "bookInfo";
    }







    
}
