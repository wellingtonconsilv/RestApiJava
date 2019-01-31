package com.cursospringboot.api.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String messageUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String messageDevelopment = ex.getCause().toString();
		List<Error> erros = Arrays.asList(new Error(messageUser, messageDevelopment));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Error> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String messageUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String messageDevelopment = ex.toString() ;
		List<Error> erros = Arrays.asList(new Error(messageUser, messageDevelopment));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String messageUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String messageDevelopment = ExceptionUtils.getRootCauseMessage(ex) ;
		List<Error> erros = Arrays.asList(new Error(messageUser, messageDevelopment));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Error> criarListaDeErros(BindingResult bindingResult){
		List<Error> erros = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {		
			String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String messageDevelopment = fieldError.toString();		
			erros.add(new Error(messageUser, messageDevelopment));
		}
		
		return erros;
	}
	
	public static class Error{
		private String messageUser;
		private String messageDevelopment;
		
		public Error(String messageUser, String messageDevelopment) {
			this.messageUser = messageUser;
			this.messageDevelopment = messageDevelopment;
		}

		public String getMessageUser() {
			return messageUser;
		}

		public String getMessageDevelopment() {
			return messageDevelopment;
		}
		
		
	}
}
