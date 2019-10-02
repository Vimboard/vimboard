package com.github.vimboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ResourceBadRequestException extends RuntimeException {
}
