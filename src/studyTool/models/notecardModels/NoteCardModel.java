package studyTool.models.notecardModels;

import studyTool.models.DbConnectionManager;
import studyTool.models.notecardModels.noteCards.NoteCard;
import studyTool.models.notecardModels.noteCards.StackModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Model for the NoteCards and NoteCard StackModel
 * @author rn046359
 */
public class NoteCardModel {
   final Connection connection;

    public NoteCardModel() {
        connection = DbConnectionManager.connect();
    }

    /**
     * Get a single stack from the DB
     *
     * @param stackName
     *          the stack name
     *
     * @param userId
     *          the user id
     *
     * @return the stack
     */
    public StackModel getSingleStack(final String stackName, final String userId) {
        final StackModel stackModel = new StackModel();
        try {
         final String query = "SELECT stack_id, name, course, subject, date_created, date_modified " +
                              " FROM stacks WHERE name= ?" +
                              " AND user_id= ?" +
                              " ORDER BY date_created DESC";

         final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, stackName);
            stmt.setString(2, userId);
         final ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
             stackModel.setName(resultSet.getString("name"));
             stackModel.setCourse(resultSet.getString("course"));
             stackModel.setSubject(resultSet.getString("subject"));
             stackModel.setDateCreated(resultSet.getString("date_created"));
             stackModel.setDateModified(resultSet.getString("date_modified"));
             stackModel.setNoteCards(getNoteCardsForStack(resultSet.getString("stack_id"), userId));
         }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stackModel;
    }

    /**
     * Get a all  stacks by

     * @param userId
     *          the user id
     *
     * @return A Map of stacks mapped by stack name
     */
    public Map<String, StackModel> getAllStacks(final String userId) {
        final PreparedStatement stmt;
        final ResultSet result;
        final List<StackModel> stacks;
        try {
            final String query = "SELECT stack_id, name, course, subject, date_created, date_modified " +
                    "FROM stacks WHERE user_id = ?" +
                    " ORDER BY date_created DESC";

            stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);
            result = stmt.executeQuery();
            stacks = new ArrayList<>();

            while(result.next()) {
                final StackModel stack = new StackModel();
                stack.setId(result.getString("stack_id"));
                stack.setName(result.getString("name"));
                stack.setCourse(result.getString("course"));
                stack.setSubject(result.getString("subject"));
                stack.setDateCreated(result.getString("date_created"));
                stack.setDateModified(result.getString("date_modified"));
                stack.setNoteCards(getNoteCardsForStack(result.getString("stack_id"), userId));
                stacks.add(stack);
            }

            final Map<String, StackModel> mapByStackName = stacks.stream().collect(Collectors.toMap(StackModel::getName, Function.identity()));
            return mapByStackName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a group of stacks by course name
     *
     * @param course
     *          the course name
     *
     * @param userId
     *          the user id
     *
     * @return A Map of stacks mapped by stack name
     */
    public List<String> getStacksByCourse(final String course, final String userId) {
       final PreparedStatement stmt;
       final ResultSet result;
        final List<String> stacks;
        try {
        final String query = "SELECT name " +
                             "FROM stacks WHERE course = ?" +
                             " AND user_id = ?" +
                             " ORDER BY date_created DESC";

        stmt = connection.prepareStatement(query);
            stmt.setString(1, course);
            stmt.setString(2, userId);
        result = stmt.executeQuery();
        stacks = new ArrayList<>();

        while(result.next()) {
            final StackModel stack = new StackModel();
            stacks.add(result.getString("name"));
        }
        return stacks;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a group of stacks by subject name
     *
     * @param course
     *          the course name
     *
     * @param subject
     *          the subject name
     *
     * @param userId
     *          the user id
     *
     * @return a Map of stacks mapped by stack name
     */
    public Map<String,StackModel> getStacksBySubject(final String course, final String subject, final String userId) {
        try {
            final String query = "SELECT id, name, course, subject, date_created, date_modified " +
                                "FROM stacks, WHERE course = ?" +
                                " AND subject = ?" +
                                " AND user_id = ?" +
                                " ORDER BY date_created DESC";

            final PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, course);
                stmt.setString(2, subject);
                stmt.setString(3, userId);
            final ResultSet result = stmt.executeQuery();
            final List<StackModel> stacks = new ArrayList<>();

            while(result.next()) {
                final StackModel stack = new StackModel();
                stack.setName(result.getString("name"));
                stack.setCourse(result.getString("course"));
                stack.setSubject(result.getString("subject"));
                stack.setDateCreated(result.getString("date_created"));
                stack.setDateModified(result.getString("date_modified"));
                stack.setNoteCards(getNoteCardsForStack(result.getString("id"), userId));
                stacks.add(stack);
            }

            final Map<String, StackModel> mapByStackName = stacks.stream().collect(Collectors.toMap(StackModel::getName, Function.identity()));
            return mapByStackName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Get all notecards that belong to a single stack
     *
     * @param stackId
     *          the stack id
     *
     * @param uid
     *          the user id
     *
     * @return the list of notecards
     */
    private List<NoteCard> getNoteCardsForStack(final String stackId, final String uid ) {
        try {
           final String query = "SELECT front, back, stack_index, card_id, stack_id, attempts, attemptsCorrect FROM notecard " +
                                "WHERE stack_id = ? " +
                                "AND user_id = ? " +
                                "ORDER BY card_id DESC";

           final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, stackId);
            stmt.setString(2, uid);
           final ResultSet results = stmt.executeQuery();
           final List<NoteCard> noteCards = new ArrayList<>();

            while(results.next()) {
                final NoteCard noteCard = new NoteCard();
                noteCard.setFront(results.getString("front"));
                noteCard.setBack(results.getString("back"));
                noteCard.setStackIndex(results.getInt("stack_index"));
                noteCard.setStackId(results.getString("stack_id"));
                noteCard.setId(results.getString("card_id"));
                noteCard.setAttempts(results.getInt("attempts"));
                noteCard.setAttemptsCorrect(results.getInt("attemptsCorrect"));
                noteCards.add(noteCard);
            }

           return noteCards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNoteCardID(final NoteCard noteCard, String userID) {
        try {
            final String query = "SELECT card_id FROM notecard " +
                                 "WHERE front = ? " +
                                 "AND back = ? " +
                                 "AND user_id = ?";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, noteCard.getFront());
            stmt.setString(2, noteCard.getBack());
            stmt.setString(3, userID);

            final ResultSet id = stmt.executeQuery();

            while(id.next()) {
                noteCard.setId(id.getString("card_id"));
            }
            return noteCard.getId();

        }catch(SQLException e) {
        e.printStackTrace();
    }
        return null;
    }

    public String getStackID(final StackModel stack, String userID) {
        try {
            final String getStackIdQuery = "SELECT stack_id FROM stacks " +
                    "WHERE name = ? AND user_id = ?";

            final PreparedStatement stmt = connection.prepareStatement(getStackIdQuery);
            stmt.setString(1, stack.getName());
            stmt.setString(2, userID);

            final ResultSet id = stmt.executeQuery();

            while(id.next()) {
                stack.setId(id.getString("stack_id"));
            }
            return stack.getId();

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert a notecard into the database
     *
     * @param noteCard
     *          the notecard instance
     *
     * @param userId
     *          the user id
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean createNoteCard(final NoteCard noteCard, final String userId, final StackModel stack) {
        try{
            final String getStackIdQuery = "SELECT stack_id FROM stacks " +
                                            "WHERE name = ? AND user_id = ?";
            final PreparedStatement stackIdStmt = connection.prepareStatement(getStackIdQuery);
            stackIdStmt.setString(1, stack.getId());
            stackIdStmt.setString(2, userId);

            final ResultSet id = stackIdStmt.executeQuery();

            while(id.next()) {
                System.out.println("A new note card was inserted successfully!");
                noteCard.setStackId(id.getString("stack_id"));
            }


            final String query = "INSERT INTO notecard (card_id, front, back, stack_id, stack_index, user_id, stackname, category, subcategory1, attempts, attemptsCorrect) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, noteCard.getId());
            stmt.setString(2, noteCard.getFront());
            stmt.setString(3, noteCard.getBack());
            stmt.setString(4, noteCard.getStackId());
            stmt.setInt(5, noteCard.getStackIndex());
            stmt.setString(6, userId);
            stmt.setString(7, stack.getName());
            stmt.setString(8, stack.getCourse());
            stmt.setString(9, stack.getSubject());
            stmt.setString(10, noteCard.getAttempts().toString());
            stmt.setString(11, noteCard.getAttemptsCorrect().toString());


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

    /**
     * Insert a stackModel into the database
     *
     * @param stackModel
     *          the new stackModel instance
     *
     * @param userId
     *          the user id
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean createStack(final StackModel stackModel, final String userId) {
        try {
            final String query = "INSERT INTO stacks (stack_id, name, date_modified, date_created, course, subject, user_id) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
            final PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, null);
            stmt.setString(2, stackModel.getName());
            stmt.setString(3, stackModel.getDateModified());
            stmt.setString(4, stackModel.getDateCreated());
            stmt.setString(5, stackModel.getCourse());
            stmt.setString(6, stackModel.getSubject());
            stmt.setString(7, userId);

            final int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new stack was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update a specific notecard
     *
     * @param updatedNoteCard
     *          the updated notecard instance
     *
     * @param userId
     *          the user id
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean updateNoteCard(NoteCard updatedNoteCard, String userId) {
        try{
            final String query = "UPDATE notecard " +
                                 "SET front= ?, back= ?, stack_index= ?, attempts = ?, attemptsCorrect= ? "+
                                 "WHERE stack_id= ? " +
                                 "AND card_id= ? " +
                                 "AND user_id= ?";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedNoteCard.getFront());
            stmt.setString(2, updatedNoteCard.getBack());
            stmt.setInt(3, updatedNoteCard.getStackIndex());
            stmt.setInt(4, updatedNoteCard.getAttempts());
            stmt.setInt(5, updatedNoteCard.getAttemptsCorrect());
            stmt.setString(6, updatedNoteCard.getStackId());
            stmt.setString(7, updatedNoteCard.getId());
            stmt.setString(8, userId);

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

    /**
     * Update a specific stack
     *
     * @param updatedStackModel
     *          the updated stack instance
     *
     * @param userId
     *          the user id
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean updateStack(StackModel updatedStackModel, String userId) {
        try {
            final String query = "UPDATE stacks " +
                                 "SET name=?, date_modified=?, course=?, subject=? " +
                                 "WHERE id=? " +
                                 "AND user_id=? ";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedStackModel.getName());
            stmt.setString(2, updatedStackModel.getDateModified());
            stmt.setString(3, updatedStackModel.getCourse());
            stmt.setString(4, updatedStackModel.getSubject());
            stmt.setString(5, updatedStackModel.getId());
            stmt.setString(6, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing StackModel was updated successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    /**
     * Delete a specific notecard from the database
     *
     * @param noteCard
     *          the notecard instance to delete
     *
     * @param userId
     *          the user id
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean deleteNoteCard(NoteCard noteCard, String userId) {
        try {
            final String query = "DELETE FROM notecard " +
                                 "WHERE card_id= ? " +
                                 "AND user_id= ? ";

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

    /**
     * Delete a specific stackModel from the database
     *
     * @param stackModel
     *          the stackModel instance to delete
     *
     * @param userId
     *          the userId
     *
     * @return True if the insert was successful. False otherwise.
     */
    public boolean deleteStack(StackModel stackModel, String userId) { //TODO may need refactor
        try {
            final String query = "DELETE FROM stacks " +
                    "WHERE stack_id= ? " +
                    "AND user_id=?";

            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, stackModel.getId());
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing StackModel deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
