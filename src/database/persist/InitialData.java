package database.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import database.persist.Account;

public class InitialData {

/*
	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Author> getAuthors() throws IOException {
		List<Author> authorList = new ArrayList<Author>();
		ReadCSV readAuthors = new ReadCSV("authors.csv");
		try {
			// auto-generated primary key for authors table
			Integer authorId = 1;
			while (true) {
				List<String> tuple = readAuthors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Author author = new Author();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file				
				Integer.parseInt(i.next());
				// auto-generate author ID, instead
				author.setAuthorId(authorId++);				
				author.setLastname(i.next());
				author.setFirstname(i.next());
				authorList.add(author);
			}
			System.out.println("authorList loaded from CSV file");
			return authorList;
		} finally {
			readAuthors.close();
		}
	}
	
	// reads initial Book data from CSV file and returns a List of Books
	public static List<Book> getBooks() throws IOException {
		List<Book> bookList = new ArrayList<Book>();
		ReadCSV readBooks = new ReadCSV("books.csv");
		try {
			// auto-generated primary key for table books
			Integer bookId = 1;
			while (true) {
				List<String> tuple = readBooks.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Book book = new Book();
				
				// read book ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file
				Integer.parseInt(i.next());
				// auto-generate book ID, instead
				book.setBookId(bookId++);				
//				book.setAuthorId(Integer.parseInt(i.next()));  // no longer in books table
				book.setTitle(i.next());
				book.setIsbn(i.next());
				book.setPublished(Integer.parseInt(i.next()));
				
				bookList.add(book);
			}
			System.out.println("bookList loaded from CSV file");			
			return bookList;
		} finally {
			readBooks.close();
		}
	}
	
	// reads initial BookAuthor data from CSV file and returns a List of BookAuthors
	public static List<BookAuthor> getBookAuthors() throws IOException {
		List<BookAuthor> bookAuthorList = new ArrayList<BookAuthor>();
		ReadCSV readBookAuthors = new ReadCSV("book_authors.csv");
		try {
			while (true) {
				List<String> tuple = readBookAuthors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				BookAuthor bookAuthor = new BookAuthor();
				bookAuthor.setBookId(Integer.parseInt(i.next()));				
				bookAuthor.setAuthorId(Integer.parseInt(i.next()));
				bookAuthorList.add(bookAuthor);
			}
			System.out.println("bookAuthorList loaded from CSV file");			
			return bookAuthorList;
		} finally {
			readBookAuthors.close();
		}
	}
*/
	
	// reads initial Account data from CSV file and returns a List of Accounts
	public static List<Account> getAccounts() throws IOException {
		List<Account> accountList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("Accounts.csv");
		try {
			// auto-generated primary key for Accounts table
			Integer accountId = 1;
			while (true) {
				List<String> tuple = readAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Account account = new Account();

				// read account ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up other CSV files				
				Integer.parseInt(i.next());
				// auto-generate account ID, instead
				account.setAccountId(accountId++);
				account.setUsername(i.next());
				account.setPassword(i.next());
				account.setBytes(i.next().getBytes());
				accountList.add(account);
			}
			System.out.println("accountList loaded from CSV file");
			return accountList;
		} finally {
			readAccounts.close();
		}
	}
	
	public static List<StringPair> getCommands() throws IOException {
		List<StringPair> commandList = new ArrayList<StringPair>();
		ReadCSV readCommands = new ReadCSV("Commands.csv");
		try {
			// auto-generated primary key for Accounts table
			//Integer accountId = 1;
			while (true) {
				List<String> tuple = readCommands.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				StringPair command = new StringPair();

				// read account ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up other CSV files				
				//Integer.parseInt(i.next());
				// auto-generate account ID, instead
				//account.setAccountId(accountId++);
				command.setFirst(i.next());
				command.setSecond(i.next());
				commandList.add(command);
			}
			System.out.println("accountList loaded from CSV file");
			return commandList;
		} finally {
			readCommands.close();
		}
	}
	
	public static List<StringPair> getDescriptions() throws IOException {
		List<StringPair> descriptionList = new ArrayList<StringPair>();
		ReadCSV readDescriptions = new ReadCSV("Descriptions.csv");
		try {
			// auto-generated primary key for Accounts table
			//Integer accountId = 1;
			while (true) {
				List<String> tuple = readDescriptions.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				StringPair description = new StringPair();

				// read account ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up other CSV files				
				//Integer.parseInt(i.next());
				// auto-generate account ID, instead
				//account.setAccountId(accountId++);
				description.setFirst(i.next());
				description.setSecond(i.next());
				descriptionList.add(description);
			}
			System.out.println("accountList loaded from CSV file");
			return descriptionList;
		} finally {
			readDescriptions.close();
		}
	}
}