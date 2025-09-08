package src.main.java.commands.cmd;

import src.main.java.commands.AbstractCommand;

public class ExportCommand extends AbstractCommand{

    public ExportCommand(String query){
        super(query);
    }

    @Override
    public void exec() {
        CSVGenerator.generate(".\\exp");
    }
    
}
