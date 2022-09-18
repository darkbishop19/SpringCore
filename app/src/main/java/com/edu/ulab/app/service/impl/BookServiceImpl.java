package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private Storage.Books booksRepository = new Storage().new Books();
    private AtomicLong counter = new AtomicLong(-1);
    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(counter.incrementAndGet());
        booksRepository.createBook(bookDto);
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {

        return   booksRepository.updateBook(bookDto);

    }

    @Override
    public BookDto getBookById(Long id) {

        return  booksRepository.getBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        booksRepository.deleteBook(id);
    }

    @Override
    public List<BookDto> findAllBooks (){
        return booksRepository.findAllBooks();
    }

}
