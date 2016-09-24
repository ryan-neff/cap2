package sample.models.notecardModels;

import sample.models.DbConnectionManager;
import sample.models.notecardModels.noteCards.NoteCard;
import sample.models.notecardModels.noteCards.Stack;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public Stack getSingleStack(final String stackName, final String userId) {
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

    public Map<String, Stack> getStacksByCourse(final String course, final String userId) {
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

    public Map<String,Stack> getStacksBySubject(final String course, final String subject, final String userId) {
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

    private List<NoteCard> getNoteCardsForStack(final String id, final String uid ) {
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

    public boolean createNoteCard(final NoteCard noteCard, final String userId) {
        try{
            final String query = "INSERT INTO notecard (id, front, back, stack_id, stack_index, user_id " +
                                 "VALUES (?, ?, ?, ?, ?, ?)";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, noteCard.getId());
            stmt.setString(2, noteCard.getFront());
            stmt.setString(3, noteCard.getBack());
            stmt.setString(4, noteCard.getStackId());
            stmt.setInt(5, noteCard.getStackIndex());
            stmt.setString(6, userId);

            final int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new note card was inserted successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createStack(final Stack stack, final String userId) {
        try {
            final String query = "INSERT INTO stacks (id, name, date_modified, date_created, course, subject, user_id " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, stack.getId());
            stmt.setString(2, stack.getName());
            stmt.setString(3, stack.getDateModified());
            stmt.setString(4, stack.getDateCreated());
            stmt.setString(5, stack.getCourse());
            stmt.setString(6, stack.getSubject());
            stmt.setString(7, userId);

            final int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new note card was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNoteCard(NoteCard updatedNoteCard, String userId) {
        try{
            final String query = "UPDATE notecard " +
                                 "SET front=?, back=?, stack_index=? "+
                                 "WHERE stack_id=? " +
                                 "AND id=?" +
                                 "AND user_id=?";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedNoteCard.getFront());
            stmt.setString(2, updatedNoteCard.getBack());
            stmt.setInt(3, updatedNoteCard.getStackIndex());
            stmt.setString(4, updatedNoteCard.getStackId());
            stmt.setString(5, updatedNoteCard.getId());
            stmt.setString(6, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Notecard was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStack(Stack updatedStack, String userId) {
        try {
            final String query = "INSERT UPDATE stacks " +
                                 "SET name=?, date_modified=?, course=?, subject=? " +
                                 "WHERE id=? " +
                                 "AND user_id=? ";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedStack.getName());
            stmt.setString(2, updatedStack.getDateModified());
            stmt.setString(3, updatedStack.getCourse());
            stmt.setString(4, updatedStack.getSubject());
            stmt.setString(5, updatedStack.getId());
            stmt.setString(6, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Stack was updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    public boolean deleteNoteCard(NoteCard noteCard, String userId) {
        try {
            final String query = "DELETE FROM notecard " +
                                 "WHERE id=? " +
                                 "AND user_id=? ";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, noteCard.getId());
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Note Card deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStack(Stack stack, String userId) {
        try {
            final String query = "DELETE FROM stacks " +
                    "WHERE id=? " +
                    "AND user_id=?";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, stack.getId());
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing Stack deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


