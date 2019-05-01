package com.greggahill.blondin.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateFormatException extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    public DateFormatException(String message, Long entryId) {
        super(message);
        extensions.put("Invalid date format. Expected \"dd-MMM-yyyy HH:mm:ss\" ", entryId);
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
        return ErrorType.DataFetchingException;
    }
}
