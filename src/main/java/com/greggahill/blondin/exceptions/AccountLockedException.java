package com.greggahill.blondin.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountLockedException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    public AccountLockedException(String message, String userName) {
        super(message);

        extensions.put("Failed Login", userName);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
       // return ErrorType.DataFetchingException;
        return ErrorType.ValidationError;
    }

}
