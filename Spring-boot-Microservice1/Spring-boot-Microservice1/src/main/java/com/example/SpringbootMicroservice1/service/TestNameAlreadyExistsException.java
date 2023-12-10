package com.example.SpringbootMicroservice1.service;


    public class TestNameAlreadyExistsException extends RuntimeException {

        public TestNameAlreadyExistsException(String message) {
            super(message);
        }
    }


