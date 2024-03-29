package com.test.wordcounter.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ErrorResponseHandler {

	private Logger logger = LogManager.getLogger(ErrorResponseHandler.class);
	
	@ExceptionHandler(BadInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse  handleBadInput(BadInputException ex)
	{
		logger.error("Error encountered", ex);
		return new ErrorResponse(ex.getMessage());
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorResponse handleMaxSizeException(
      MaxUploadSizeExceededException ex) {
 
		logger.error("File too large", ex);
		return new ErrorResponse("File too large");
    }
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse  handleException(Exception ex)
	{
		logger.error("Error encountered", ex);
		return new ErrorResponse("Error occurred during processing.");
	}
	
	
}
