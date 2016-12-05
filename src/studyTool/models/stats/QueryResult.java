/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyTool.models.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dan
 */
public class QueryResult {
    /**
     * The number corresponding to the query desired. 
     */
    public int queryNum;
    /**
     * The number of row results from the query.
     */
    public int numResults;
    /**
     * The number of columns in each row result.
     */
    public int numColumns;
    /**
     * The cell values. Use this.results.get(row desired - 1).get(column name)
     */
    public ArrayList<Map<String, String>> results;
    /**
     * The names of the columns. Can be used in conjunction with results if the names of the columns are unknown.
     */
    public ArrayList<String> columnNames; 

    /** 
     * A wrapper for the query results done by executeQuery(). 
     * @param   queryNum    the ID of the query that was executed
     * @param   numResults  the number of row results from the executed query
     * @param   numColumns  the number of columns from the executed query
     * @see                 StatsManager
    */
    public QueryResult(int queryNum, int numResults, int numColumns)
    {
        results = new ArrayList<>();
        for(int i = 0; i < numResults; i++)
        {
            results.add(new HashMap<>());
        }
        columnNames = new ArrayList<>();
        this.numResults = numResults;
        this.numColumns = numColumns;
        this.queryNum = queryNum;
    }
    
    /**
     * Adds a name to the list of column names for logistical purposes
     * @param name the name of the column to add to the list
     * @see StatsManager
     */
    public void addColumnName(String name)
    {
        columnNames.add(name);
    }
    
    /**
     * Adds a value to the corresponding HashMap that represents a row result
     * @param row the row number the query cursor is on
     * @param colNum the column currently being eveluated
     * @param val the value of the cell at the column in the current row
     * @see StatsManager
     */
    public void addValue(int row, int colNum, String val)
    {
        results.get(row - 1).put(columnNames.get(colNum - 1), val);
    }
}
