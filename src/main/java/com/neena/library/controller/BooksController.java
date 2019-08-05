package com.neena.library.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neena.library.manager.BookManager;
import com.neena.library.model.Book;
import com.neena.library.model.BookInventory;

@Controller
public class BooksController {


	BookManager bookManager;

	@Inject
	public BooksController(BookManager bookManager) {
		// TODO Auto-generated constructor stub
		this.bookManager = bookManager;
	}

	@RequestMapping(value = { "/books" }, method = RequestMethod.GET)
	public String getBooks(Model model) {
		List<Book> books = bookManager.getBooks();
		model.addAttribute("books", books);
		return "books";
	}
	
	@ModelAttribute
	@RequestMapping(value = { "/addBook" }, method = RequestMethod.GET)
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		return "addBook"; 
		
	}
	
	
	@RequestMapping(value = { "/processAddBook" }, method = RequestMethod.POST)
	public String processAddBook(@ModelAttribute("book") Book book, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println( result.getAllErrors());
	    }
		System.out.println("I am in the method" + book.getBookName()); 
		
		bookManager.addBook(book);
		model.addAttribute("book", new Book()); 
		return "addBook"; 
	}
	
	@ModelAttribute
	@RequestMapping(value = { "/updateBook" }, method = RequestMethod.GET)
	public String updateBook(
			@RequestParam(value="id", required=true) Long id,
			Model model) {
		Book book = bookManager.getBookById(id); 
		model.addAttribute("book", book); 
		return "updateBook"; 
		
	}
	
	
	
	@RequestMapping(value = { "/processUpdateBook" }, method = RequestMethod.POST)
	public String processUpdateBook(
			@ModelAttribute("book") Book book, BindingResult result, ModelMap model) {
		bookManager.updateBook(book);
		List<Book> books = bookManager.getBooks();
		model.addAttribute("books", books);
		return "books"; 
		
		
	}
	
	
	@RequestMapping(value = { "/processDelete" }, method = RequestMethod.GET)
	public String deleteBook(
			@RequestParam(value="id", required=true) Long id,
			Model model) {
		bookManager.deleteBookById(id); 
		List<Book> books = bookManager.getBooks();
		model.addAttribute("books", books);
		return "books";
		
	}
}
