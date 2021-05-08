package com.hanna.sapeha.app.repository.util;


public class RepositoryUtil {

    public static int getStartPosition(int pageNumber, int pageSize) {
        return pageNumber * pageSize - pageSize;
    }
}
