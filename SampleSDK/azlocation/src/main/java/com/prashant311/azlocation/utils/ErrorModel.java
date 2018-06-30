package com.prashant311.azlocation.utils;

/**
 * Created by AzTeam.
 */

public class ErrorModel
{
    public Exception exception;
    public String statusCode="404";

    public Exception getException() {
        return exception;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
