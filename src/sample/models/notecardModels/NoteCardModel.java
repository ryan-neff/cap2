package sample.models.notecardModels;

import com.sun.tools.corba.se.idl.constExpr.Not;
import sample.models.DbConnectionManager;
import sample.models.notecardModels.noteCards.NoteCard;
import sample.models.notecardModels.noteCards.Stack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author rn046359
 */
public class NoteCardModel {
   final Connection connection;

    public NoteCardModel() {
        connection = DbConnectionManager.connect();
    }

    public Stack getSingleStack(String stackName, String userId) {
        try {
         final String query = "SELECT id, name, course, subject, date_created, date_modified " +
                              "FROM stacks WHERE name = '"+ stackName +
                              "' AND user_id = '"  + userId +
                              "' ORDER BY date_created DESC";

         final Statement stmt = connection.createStatement();
         final ResultSet resultSet = stmt.executeQuery(query);
         final Stack stack = new Stack();
         if(resultSet.next()) {
             stack.setName(resultSet.getString("name"));
             stack.setCourse(resultSet.getString("course"));
             stack.setSubject(resultSet.getString("subject"));
             stack.setDateCreated(resultSet.getString("date_created"));
             stack.setDateModified(resultSet.getString("date_modified"));
             stack.setNoteCards(getNoteCardsForStack(resultSet.getString("id"), userId));
         }
         return stack;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Stack> getStacksByCourse(String course, String userId) {
        try {
        final String query = "SELECT id, name, course, subject, date_created, date_modified " +
                             "FROM stacks WHERE course = '"+ course +
                             "' AND user_id = '"  + userId +
                             "' ORDER BY date_created DESC";

        final Statement stmt = connection.createStatement();
        final ResultSet result = stmt.executeQuery(query);
        final List<Stack> stacks = new ArrayList<>();

        while(result.next()) {
            final Stack stack = new Stack();
            stack.setName(result.getString("name"));
            stack.setCourse(result.getString("course"));
            stack.setSubject(result.getString("subject"));
            stack.setDateCreated(result.getString("date_created"));
            stack.setDateModified(result.getString("date_modified"));
            stack.setNoteCards(getNoteCardsForStack(result.getString("id"), userId));
            stacks.add(stack);
        }

        final Map<String, Stack> mapByStackName = stacks.stream().collect(Collectors.toMap(Stack::getName, Function.identity()));
            return mapByStackName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String,Stack> getStacksBySubject(String course, String subject, String userId) {
        try {
            final String query = "SELECT id, name, course, subject, date_created, date_modified " +
                                "FROM stacks, WHERE course = '"+ course +
                                "' AND subject = '" + subject +
                                "' AND user_id = '" + userId +
                                "' ORDER BY date_created DESC";

            final Statement stmt = connection.createStatement();
            final ResultSet result = stmt.executeQuery(query);
            final List<Stack> stacks = new ArrayList<>();

            while(result.next()) {
                final Stack stack = new Stack();
                stack.setName(result.getString("name"));
                stack.setCourse(result.getString("course"));
                stack.setSubject(result.getString("subject"));
                stack.setDateCreated(result.getString("date_created"));
                stack.setDateModified(result.getString("date_modified"));
                stack.setNoteCards(getNoteCardsForStack(result.getString("id"), userId));
                stacks.add(stack);
            }

            final Map<String, Stack> mapByStackName = stacks.stream().collect(Collectors.toMap(Stack::getName, Function.identity()));
            return mapByStackName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private List<NoteCard> getNoteCardsForStack(String id, String uid ) {
        try {
           final String query = "SELECT front, back, stack_index FROM notecard " +
                                "WHERE stack_id = " + id +
                                " AND user_id = " + uid +
                                " ORDER BY id DESC";

           final Statement stmt = connection.createStatement();
           final ResultSet results = stmt.executeQuery(query);
           final List<NoteCard> noteCards = new ArrayList<>();

            while(results.next()) {
                final NoteCard noteCard = new NoteCard();
                noteCard.setFront(results.getString("front"));
                noteCard.setBack(results.getString("back"));
                noteCard.setStackIndex(results.getInt("stack_index"));
                noteCards.add(noteCard);
            }

           return noteCards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
