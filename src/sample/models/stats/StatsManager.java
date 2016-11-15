package sample.models.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import sample.models.DbConnectionManager;

/**
 * @author rn046359
 */
public class StatsManager {
    private final Connection DBM;

    public StatsManager() {
        DBM = DbConnectionManager.connect();
        try 
        {
            String query = "CREATE OR REPLACE VIEW 'username' . 'cardStats' AS SELECT * FROM notecard_stats where user = username";   
            Statement stmt = DBM.createStatement();
            stmt.executeQuery(query);
            query = "CREATE OR REPLACE VIEW 'username' . 'stackStats' AS SELECT * FROM stack_stats where user = username";   
            stmt = DBM.createStatement();
            stmt.executeQuery(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        
//        //start of population of DB.   
//        String arr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//        Random rand = new Random();
//        for(int i = 0; i < 100; i++)
//        {
//            String arr2 = "";
//            String arr3 = "";
//            for(int j = 0; j < 5; j++)
//            {
//                char x = arr.charAt(rand.nextInt(arr.length() - 1));
//                arr2 += x;
//                arr3 += x;
//            }
//        }
        
    }
    
    public void getData()
    {
        
        try {
            //avg percentage correct across all notecards
            final String query = "SELECT ROUND(SUM(SELECT percentage FROM 'cardStats') / COUNT(SELECT percentage FROM 'cardStats'), 2)";
            /*
            //avg percentage correct across all stacks 
            final String query = "SELECT ROUND(SUM(SELECT percentage FROM 'stackStats') / COUNT(SELECT percentage FROM 'stackStats'), 2)";
            
            //size of each stack
            final String query = "select distinct stack_id, count(stack_id) from notecard group by stack_id";
            
            //size of each category
            final String query = "select distinct category, count(category) from notecard group by category";
            
            //
            final String query = "SELECT ROUND(SUM(SELECT percentage FROM 'username') / COUNT(SELECT percentage FROM 'username'), 2)";
            
            
            final String query = "SELECT ROUND(SUM(SELECT percentage FROM 'username') / COUNT(SELECT percentage FROM 'username'), 2)";
            
            
            final String query = "SELECT ROUND(SUM(SELECT percentage FROM 'username') / COUNT(SELECT percentage FROM 'username'), 2)";
            */
            
            final Statement stmt = DBM.createStatement();
            final ResultSet rset = stmt.executeQuery(query);
            
            float pct = rset.getFloat(0);
            System.out.println(pct);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //prepare query
        
        //set the vars based on an fxml view 
        
        /*
        dropdowns: 
            possible addition: day/month/year/hour
            total/stack
            attempted count/percentage
        */
        
        //execute
        
        //get results 
        
        //update fxml to display somehow
          
    }
    


}
