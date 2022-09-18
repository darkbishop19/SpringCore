package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.UserBookUpdateRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;



    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }


    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks( UserBookUpdateRequest userBookUpdateRequest) {
        log.info("Got user book update request: {}", userBookUpdateRequest);
        log.info("All books: {}", bookService.findAllBooks());
        log.info("All users: {}", userService.findAllUsers());
        UserDto userDto = userMapper.userUpdateRequestToUserDto(userBookUpdateRequest.getUserUpdateRequest());
        log.info("Mapped user request: {}", userDto);
        UserDto userDtoToUpdate = userService.updateUser(userDto);
        log.info("Updated user: {}", userDtoToUpdate);
        List<BookDto> bookDtoList = userBookUpdateRequest.getBookRequests().stream().map(bookMapper::bookRequestToBookDto).toList();
        List<Long> bookIdsList = new ArrayList<>();
        List<Long> check = new ArrayList<>();
        for ( int i=0;i< bookDtoList.size(); i++){
            bookDtoList.get(i).setUserId(userDtoToUpdate.getId());
            for ( BookDto bookFromDB: bookService.findAllBooks()){
                if ( bookFromDB.getUserId()== bookDtoList.get(i).getUserId() && !check.contains(bookFromDB.getId())){
                    bookDtoList.get(i).setId(bookFromDB.getId());
                    check.add(bookFromDB.getId());
                    bookService.updateBook(bookDtoList.get(i));
                    bookIdsList.add(bookDtoList.get(i).getId());
                    break;
                }
            }
            if (bookDtoList.get(i).getId() == null) {
                BookDto bookDtoToCreate =  bookService.createBook(bookDtoList.get(i));
                bookIdsList.add(bookDtoToCreate.getId());
            }

        }


        log.info("All books: {}", bookService.findAllBooks());
        log.info("All users: {}", userService.findAllUsers());



        return UserBookResponse.builder()
                .userId(userDtoToUpdate.getId())
                .booksIdList(bookIdsList)
                .build();
    }


    public UserBookResponse getUserWithBooks(Long userId) {

        log.info("Got user book get request: {}", userService.getUserById(userId));
        log.info("All users: {}", userService.findAllUsers());
        log.info("All books: {}", bookService.findAllBooks());
        if ( userService.getUserById(userId) == null){
            return null;
        }
        UserDto userDto = userService.getUserById(userId);

        List<Long> bookIdsList = bookService.findAllBooks().stream()
                .filter(bookDto -> bookDto.getUserId() == userDto.getId() )
                .map(BookDto::getId)
                .toList();
        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdsList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {

        log.info("Got user book delete request: {}", userService.getUserById(userId));
        log.info("All users: {}", userService.findAllUsers());
        log.info("All books: {}", bookService.findAllBooks());
        if ( userService.getUserById(userId) == null){
            log.info("Not existing user");
            return;
        }
        userService.deleteUserById(userId);
        List<BookDto> booksToDelete =  bookService.findAllBooks().stream()
                .filter(bookDto -> bookDto.getUserId() == userId)
                .toList();
        for ( BookDto bookToDelete: booksToDelete){
            bookService.deleteBookById(bookToDelete.getId());
        }
        log.info("-----------------------------After delete request ------------------------------------------");
        log.info("All users: {}", userService.findAllUsers());
        log.info("All books: {}", bookService.findAllBooks());
    }
}
