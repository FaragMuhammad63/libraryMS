package com.farag.ultimate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(NotAuthorizedUserException.class)
	public ResponseEntity<ExceptionResponse> handleNotAuthorizedUserException(NotAuthorizedUserException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getErrorMessage());
		messages.add("NotAuthorizedUserException");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.name(), messages);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(EmptyArgumentsException.class)
	public ResponseEntity<ExceptionResponse> handleEmptyArgumentsException(EmptyArgumentsException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getErrorMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NO_CONTENT.value(),
				HttpStatus.NO_CONTENT.name(), messages);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NO_CONTENT);
	}


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getErrorMessage());
		ExceptionResponse errorResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.name(), messages);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getErrorMessage());
		ExceptionResponse errorResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.name(), messages);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}




	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationError(AuthenticationException ex) {

		logger.info("Start of handleAuthenticationError");
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.name(), messages);
		logger.info("End of handleAuthenticationError");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException ex) {
		List<String> messages = new ArrayList<>();
		messages.add(ex.getErrorMessage());
		ExceptionResponse errorResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.name(), messages);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionResponse> handleUnknownException(Exception ex, WebRequest request) {
//
//
//        List<String> messages = new ArrayList<>();
//        messages.add("Unexpected Error !");
//
//        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), messages);
//
//
//        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }

}
