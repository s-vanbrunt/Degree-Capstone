package Utilities;

/**
 * A functional interface that will be used by lambda expressions to view and clear reports.
 *
 * @author Scott VanBrunt
 */
public interface ReportView {
    public abstract void reportView(String message);
}
