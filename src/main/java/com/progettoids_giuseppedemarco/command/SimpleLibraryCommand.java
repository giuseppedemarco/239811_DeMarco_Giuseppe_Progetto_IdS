package com.progettoids_giuseppedemarco.command;

public class SimpleLibraryCommand implements LibraryCommand {
    private final String label;
    private final Runnable action;

    public SimpleLibraryCommand(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void execute() {
        action.run();
    }
}
