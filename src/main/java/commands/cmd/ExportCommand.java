package src.main.java.commands.cmd;

import src.main.java.commands.AbstractCommand;
import src.main.java.utils.CSVGenerator;

public class ExportCommand extends AbstractCommand{

    public ExportCommand(String query){
        super(query);
    }

    @Override
    public void exec() {
        CSVGenerator.generate(".\\exp");
    }
    
}
