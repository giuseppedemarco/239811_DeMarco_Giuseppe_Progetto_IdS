package src.main.java.utils.builders;

import src.main.java.commands.cmd.ExportCommand;
public class ExportCommandBuilder {

    /**
     * Costruisce un'istanza di {@link ExportCommand}.
     * @return un'istanza di {@link ExportCommand}
     */
    public ExportCommand create() {
        return new ExportCommand(null);
    }
}
