package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Storage {
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции


    public class Users {
        private List<UserDto> userDtoList = new ArrayList<>();
        // private AtomicLong counter = new AtomicLong(0);

        public void createUser(UserDto userDto) throws IllegalArgumentException{
            UserDto newUser = new UserDto();
            newUser.setId(userDto.getId());
            newUser.setAge(userDto.getAge());
            newUser.setTitle(userDto.getTitle());
            newUser.setFullName(userDto.getFullName());
            userDtoList.add(newUser);
        }

        public List<UserDto> findAllUsers() {
            return this.userDtoList;
        }

        public UserDto getUserById(Long id) {

            for (UserDto user : userDtoList) {
                if (Objects.equals(user.getId(), id)) {
                    return user;
                }
            }
            return null;
        }

        public UserDto updateUser(UserDto userDto) throws IllegalArgumentException {
            Long idToUpdate = 0L;
            for (UserDto user : userDtoList) {
                if (Objects.equals(user.getId(), userDto.getId())) {
                    idToUpdate = user.getId();
                    break;
                }
            }
            UserDto userDtoUpdates = new UserDto();
            userDtoUpdates.setId(userDto.getId());
            userDtoUpdates.setAge(userDto.getAge());
            userDtoUpdates.setTitle(userDto.getTitle());
            userDtoUpdates.setFullName(userDto.getFullName());
            userDtoList.set(Math.toIntExact(idToUpdate), userDtoUpdates);
            return userDtoUpdates;
        }

        public void deleteUserById(Long id) {
            userDtoList.removeIf(userDto -> Objects.equals(userDto.getId(), id));
        }
    }
    public class Books{
        private List<BookDto> booksWithUserList = new ArrayList<>();


        public List<BookDto> findAllBooks ( ){
            return this.booksWithUserList;
        }

        public void createBook ( BookDto bookDto) throws IllegalArgumentException{
            BookDto newBookDto = new BookDto();
            newBookDto.setId(bookDto.getId());
            newBookDto.setUserId(bookDto.getUserId());
            newBookDto.setTitle(bookDto.getTitle());
            newBookDto.setAuthor(bookDto.getAuthor());
            newBookDto.setPageCount(bookDto.getPageCount());
            booksWithUserList.add(newBookDto);
        }

        public BookDto updateBook ( BookDto bookDto) throws IllegalArgumentException{
            Long idToUpdate = 0L;
            for (BookDto book : booksWithUserList) {
                if (Objects.equals(book.getId(), bookDto.getId())) {
                    idToUpdate = book.getId();
                    break;
                }
            }

            BookDto bookDtoUpdated = new BookDto();
            bookDtoUpdated.setId(bookDto.getId());
            bookDtoUpdated.setUserId(bookDto.getUserId());
            bookDtoUpdated.setAuthor(bookDto.getAuthor());
            bookDtoUpdated.setPageCount(bookDto.getPageCount());
            bookDtoUpdated.setTitle(bookDto.getTitle());
            booksWithUserList.set(Math.toIntExact(idToUpdate), bookDtoUpdated);
            return bookDtoUpdated;
        }

        public BookDto getBookById ( Long id){
            for (BookDto book : booksWithUserList) {
                if (book.getId() == id) {
                    return book;
                }
            }
            return null;
        }

        public void deleteBook ( Long id ){
            booksWithUserList.removeIf(bookDto -> Objects.equals(bookDto.getId(), id));
        }

    }


}




