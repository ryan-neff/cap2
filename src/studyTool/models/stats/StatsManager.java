package studyTool.models.stats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import studyTool.models.DbConnectionManager;

/**
 * @author rn046359
 */
public class StatsManager {
    private final Connection DBM;
    private ArrayList<QueryResult> cachedResults;
    
    public StatsManager() {
        DBM = DbConnectionManager.connect();
        cachedResults = new ArrayList<>();
    }
    
    /**
     * Executes the desired query according to a list of known queries to execute:
     * 1: avg percentage correct across all notecards
     * 2: avg percentage correct across all stacks 
     * 3: size of each stack
     * 4: size of each category
     * 5: size of each sub category (1)
     * 6: size of each sub category (2)
     * 
     * 
     * The return value is an object containing data that was retrieved from the query. 
     * Use this.results.get(row desired - 1).get(column name) when using results.
     * @see QueryResult
     * 
     * @param index the number for the desired query as described above
     * @return A queryResult Object, or NULL on failure or an invalid index
     */
    public QueryResult getData(int index)
    {
        for(QueryResult q: cachedResults)
        {
            if(q.queryNum == index)
                return q;
        }
        
        
        final String query = getQuery(index);
        int columns = getNumColumns(index);
        try {
            
            if(query == null)
            {
                return null;
            }
            final Statement stmt = DBM.createStatement();
            final ResultSet rset = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rset.getMetaData();
            //I have the number of columns, so just fetch all column values as strings, 
            //loop through until all rows are hit.
            rset.afterLast();
            rset.previous();
            QueryResult q = new QueryResult(index, rset.getRow(), rsmd.getColumnCount());
            rset.beforeFirst();
            for(int i = 1; i <= columns; i++)
            {
               q.addColumnName(rsmd.getColumnName(i));
            }
            
            while(rset.next())
            {
                if(rset.isAfterLast()) break;
                for(int i = 1; i <= columns; i++)
                {
                   q.addValue(rset.getRow(), i, rset.getString(i));
                }
            }
            stmt.close();
            
            cachedResults.add(q);
            
            return q;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        return null;
        
    }
    
    public String getQuery(int x)
    {
        switch(x)
        {
            case 1: return "SELECT ROUND((SELECT SUM(percentage) FROM notecard_stats where user = 'test') / (SELECT COUNT(*) FROM notecard_stats where user = 'test'), 2)";
            case 2: return "SELECT ROUND((SELECT sum(current_percentage) FROM stack_stats inner join stacks using (stack_id) where user_id = 'test') / (select count(current_percentage) from stack_stats inner join stacks using (stack_id) where user_id = 'test'), 2)";
            case 3: return "select stacks.stack_id, name, count(card_id) as size from stacks inner join notecard where notecard.stack_id = stacks.stack_id group by stacks.stack_id";
            case 4: return "SELECT category, COUNT(*) FROM notecard where user_id = 'test' and category != \"NULL\" and category is not null group by category";
            case 5: return "SELECT subcategory1, COUNT(*) FROM notecard where user_id = 'test' and subcategory1 != \"NULL\" and subcategory1 is not null group by subcategory1";
            case 6: return "SELECT subcategory2, COUNT(*) FROM notecard where user_id = 'test' and subcategory2 != \"NULL\" and category is not null group by subcategory2";
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
        }
        return null;
    }
    
    public int getNumColumns(int x)
    {
        switch(x)
        {
            case 1: return 1;
            case 2: return 1;
            case 3: return 3;
            case 4: return 2;
            case 5: return 2;
            case 6: return 2;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
        }
        return -1;
    }


}
