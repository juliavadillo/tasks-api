package com.project.tasksmanager.utils;

public class DescriptionMapper {
    public static String mapToDescriptionMessage(String description, Long id, String title) {
        StringBuilder message = new StringBuilder();
        message.append("Description of Task [");
        message.append(id);
        message.append(": ");
        message.append(title);
        message.append("] is: ");
        message.append(description);
        return message.toString();
    }
}
