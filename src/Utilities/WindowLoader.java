package Utilities;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * A functional interface that will be used by lambda expressions to load new windows.
 *
 * @author Scott VanBrunt
 */
public interface WindowLoader {
    public abstract void loadNewWindow(ActionEvent event, String url) throws IOException;
}
