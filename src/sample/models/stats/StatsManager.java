package sample.models.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sample.models.DbConnectionManager;

/**
 * @author rn046359
 */
public class StatsManager {
    private final Connection DBM;

    public StatsManager() {
        DBM = DbConnectionManager.connect();
        System.out.println("Testing stats");
    }
    
    public void getData()
    {
        
        try {
            final String query = "SELECT * FROM notecard_stats";
            final Statement stmt = DBM.createStatement();
            final ResultSet rset = stmt.executeQuery(query);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //prepare query
        
        //set the vars based on an fxml view 
        
        /*
        dropdowns: 
            day/month/year/hour
            total/stack
            attempted count/percentage
        */
        
        //execute
        
        //get results 
        
        //update fxml to display somehow
          
    }
    


}
