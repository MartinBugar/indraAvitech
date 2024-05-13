package com.martyx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    private CommandService commandService;

    public CommandProcessor() {
        this.commandService = new CommandServiceBean();
    }

    public List<UserDefinition> processCommand(String element) throws SQLException {
        commandService = new CommandServiceBean();
        if (element.equals("PrintAll")) {
            return commandService.printAll();
        } else if (element.equals("DeleteAll")) {
            return commandService.deleteAll();
        } else if (element.startsWith("Add")) {
            String userGuid = null;
            String userName = null;
            Integer userId = null;
            List<String> parameters = new ArrayList<>();
            Pattern stringPattern = Pattern.compile("\"([^\"]*)\"");
            Matcher matcherString = stringPattern.matcher(element);
            while (matcherString.find()) {
                parameters.add(matcherString.group(1));
            }
            userGuid = parameters.get(0);
            userName = parameters.get(1);
            Pattern integerPattern = Pattern.compile("([0-9]+).* ");
            Matcher integerMatcher = integerPattern.matcher(element);
            while (integerMatcher.find()) {
                userId = Integer.parseInt(integerMatcher.group(1));
            }
            UserDefinition definition = commandService.add(userId, userGuid, userName);
            System.out.println("UserEntity added to DB: "
                    + "ID: " + definition.getUserId()
                    + ", GUID: " + definition.getUserGuid()
                    + ", NAME: " + definition.getUserName());

            return Collections.singletonList(definition);
        }

        return null;
    }
}
