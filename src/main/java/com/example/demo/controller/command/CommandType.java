package com.example.demo.controller.command;

import com.example.demo.controller.command.impl.common.*;
import com.example.demo.controller.command.impl.design.FinishCreateNewDesignCommand;
import com.example.demo.controller.command.impl.design.ProceedNewDesignCommand;
import com.example.demo.controller.command.impl.design.StartCreateNewDesignCommand;
import com.example.demo.controller.command.impl.gotopage.*;
import com.example.demo.controller.command.impl.order.CancelOrderCommand;
import com.example.demo.controller.command.impl.order.CreateNewOrderCommand;
import com.example.demo.controller.command.impl.order.ProceedOrderCommand;
import com.example.demo.controller.command.impl.rating.FinishCreateNewRatingCommand;
import com.example.demo.controller.command.impl.rating.FinishEditRatingCommand;
import com.example.demo.controller.command.impl.rating.StartCreateNewRatingCommand;
import com.example.demo.controller.command.impl.rating.StartEditRatingCommand;

public enum CommandType {
    // common commands
    HOME(new DefaultCommand()),
    DEFAULT(new DefaultCommand()),
    CATALOG(new GoToImageCatalogCommand()),
    IMAGE_DETAILS(new GoToImageDetailsCommand()),

    // sign in/up/out commands
    SIGN_OUT(new SignOutUserCommand()),
    START_SIGN_IN(new StartSignInUserCommand()),
    FINISH_SIGN_IN(new FinishSignInUserCommand()),
    START_SIGN_UP(new StartSignUpUserCommand()),
    FINISH_SIGN_UP(new FinishSignUpUserCommand()),

    // account related commands
    ACCOUNT_DETAILS(new GoToUserDetailsCommand()),
    ACCOUNT_RATINGS(new GoToUserRatingsCommand()),
    ACCOUNT_ORDERS(new GoToUserOrdersCommand()),
    ACCOUNT_IMAGES(new GoToUserImagesCommand()),
    START_DELETE_ACCOUNT(new StartDeleteUserCommand()),
    FINISH_DELETE_ACCOUNT(new FinishDeleteUserCommand()),
    START_EDIT_ACCOUNT_DETAILS(new StartEditUserDetailsCommand()),
    FINISH_EDIT_ACCOUNT_DETAILS(new FinishEditUserDetailsCommand()),

    // order related commands
    CANCEL_ORDER(new CancelOrderCommand()),
    PROCEED_ORDER(new ProceedOrderCommand()),
    CREATE_NEW_ORDER(new CreateNewOrderCommand()),

    // rating related commands
    START_EDIT_RATING(new StartEditRatingCommand()),
    FINISH_EDIT_RATING(new FinishEditRatingCommand()),
    START_CREATE_RATING(new StartCreateNewRatingCommand()),
    FINISH_CREATE_RATING(new FinishCreateNewRatingCommand()),
    START_CREATE_DESIGN(new StartCreateNewDesignCommand()),
    FINISH_CREATE_DESIGN(new FinishCreateNewDesignCommand()),

    // admin commands
    ALL_TASKS(new GoToAllTasksCommand()),
    ALL_USERS(new GoToAllUsersCommand()),
    ALL_ORDERS(new GoToAllOrdersCommand()),
    ALL_IMAGES(new GoToAllImagesCommand()),
    ALL_RATINGS(new GoToAllRatingsCommand()),
    STATISTICS(new GoToStatisticsPageCommand()),
    PROCEED_NEW_DESIGN(new ProceedNewDesignCommand());

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String command) {
        try {
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            return commandType.command;
        } catch (Throwable e) {
            return DEFAULT.command;
        }
    }
}
