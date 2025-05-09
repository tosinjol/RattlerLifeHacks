package edu.famu.rattlerlifehacks.util;

public record ApiResponse<T> (boolean success, String message, T data, Object error){
}

