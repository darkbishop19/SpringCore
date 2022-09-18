package com.edu.ulab.app.web.request;

import lombok.Data;

import java.util.List;
@Data
public class UserBookUpdateRequest {
    private UserUpdateRequest userUpdateRequest;
    private List<BookRequest> bookRequests;




}
