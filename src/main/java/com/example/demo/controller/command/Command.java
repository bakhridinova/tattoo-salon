package com.example.demo.controller.command;

import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
