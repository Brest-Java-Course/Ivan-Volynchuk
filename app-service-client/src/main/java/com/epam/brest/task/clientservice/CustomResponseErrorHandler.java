package com.epam.brest.task.clientservice;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by fieldistor on 02.12.14.
 */
public class CustomResponseErrorHandler  implements ResponseErrorHandler {

    /**
     * Delegates to {@link #hasError(HttpStatus)} with the response status code.
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return hasError(getHttpStatusCode(response));
    }

    private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode;
        try {
            statusCode = response.getStatusCode();
        }
        catch (IllegalArgumentException ex) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
                    response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
        }
        return statusCode;
    }

    /**
     * Template method called from {@link #hasError(ClientHttpResponse)}.
     * <p>The default implementation checks if the given status code is
     * {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR}
     * or {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
     * Can be overridden in subclasses.
     * @param statusCode the HTTP status code
     * @return {@code true} if the response has an error; {@code false} otherwise
     */
    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }

    /**
     * This default implementation throws a {@link HttpClientErrorException} if the response status code
     * is {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}, a {@link HttpServerErrorException}
     * if it is {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR},
     * and a {@link RestClientException} in other cases.
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = getHttpStatusCode(response);

        if(statusCode.is4xxClientError()) {

            switch(statusCode) {
                case BAD_REQUEST:
                    //throw new NoItemsFoundException(IOUtils.toString(response.getBody(), getCharset(response)),"");
                case NOT_FOUND:
                    //throw new NoItemsFoundException(IOUtils.toString(response.getBody(), getCharset(response)),"");
                default:
                    throw new RestClientException("Unknown status code [" + statusCode + "]");
            }
        }else {

            switch (statusCode.series()) {
                case CLIENT_ERROR:
                    throw new HttpClientErrorException(statusCode, response.getStatusText(),
                            response.getHeaders(), getResponseBody(response), getCharset(response));
                case SERVER_ERROR:
                    throw new HttpServerErrorException(statusCode, response.getStatusText(),
                            response.getHeaders(), getResponseBody(response), getCharset(response));
                default:
                    throw new RestClientException("Unknown status code [" + statusCode + "]");
            }
        }

    }
//returns body of respons
    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return FileCopyUtils.copyToByteArray(responseBody);
            }
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }
//returns charset
    private Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType.getCharSet() : null;
    }

}
