package sample.models.stats;

import java.sql.Connection;

/**
 * @author rn046359
 */
public class StatsManager {
    private final Connection connection;

    public StatsManager(final Connection connection) {
        this.connection = connection;
    }


}
