package com.hellokoding.jpa;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hellokoding.jpa.model.Book;
import com.hellokoding.jpa.model.Publisher;
import com.hellokoding.jpa.repository.BookRepository;
import com.hellokoding.jpa.repository.PublisherRepository;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */

	@Override
	@Transactional
	public void run(String... strings) throws Exception {

		final Publisher publisherA = publisherRepository.save(new Publisher("Publisher A"));
		final Publisher publisherB = publisherRepository.save(new Publisher("Publisher B"));

		final Book bookA = new Book("Book A", new HashSet<Publisher>() {
			{
				add(publisherA);
				add(publisherB);
			}
		});

		final Book bookB = new Book("Book B", new HashSet<Publisher>() {
			{
				add(publisherB);
			}
		});

		final Book bookC = new Book("Book C", new HashSet<Publisher>() {
			{
				add(publisherA);
			}
		});

		bookRepository.save(bookA);
		bookRepository.save(bookB);
		bookRepository.save(bookC);

		/*
		 * TODO: Why does this operation link books to publisherA in the repository?
		 * Isn't this just a local variable?
		 */
		publisherA.setBooks(new HashSet<Book>() {
			{
				add(bookA);
				add(bookC);
			}
		});

		// publisherB.setBooks(new HashSet<Book>() {
		// {
		// add(bookA);
		// add(bookB);
		// }
		// });

		// publisherRepository.save(publisherA);
		// publisherRepository.save(publisherB);

		// fetch all books
		for (Book book : bookRepository.findAll()) {
			logger.info(book.toString());
		}

		// fetch all publishers
		for (Publisher publisher : publisherRepository.findAll()) {
			logger.info(publisher.toString());
		}

	}
}
