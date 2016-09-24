package sample.models.notecardModels.noteCards;

import java.util.List;

/**
 * @author rn046359
 */
public class Stack {
    private String name;
    private String course;
    private String subject;
    private String dateCreated;
    private String dateModified;
    private List<NoteCard> noteCards;


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(final String course) {
        this.course = course;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(final String dateModified) {
        this.dateModified = dateModified;
    }


    public List<NoteCard> getNoteCards() {
        return noteCards;
    }

    public void setNoteCards(final List<NoteCard> noteCards) {
        this.noteCards = noteCards;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Stack)) return false;

        final Stack stack = (Stack) o;

        if (!getName().equals(stack.getName())) return false;
        if (!getCourse().equals(stack.getCourse())) return false;
        if (!getSubject().equals(stack.getSubject())) return false;
        if (!getDateCreated().equals(stack.getDateCreated())) return false;
        if (getDateModified() != null ? !getDateModified().equals(stack.getDateModified()) : stack.getDateModified() != null)
            return false;
        return getNoteCards() != null ? getNoteCards().equals(stack.getNoteCards()) : stack.getNoteCards() == null;

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getCourse().hashCode();
        result = 31 * result + getSubject().hashCode();
        result = 31 * result + getDateCreated().hashCode();
        result = 31 * result + (getDateModified() != null ? getDateModified().hashCode() : 0);
        result = 31 * result + (getNoteCards() != null ? getNoteCards().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", subject='" + subject + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", noteCards=" + noteCards +
                '}';
    }
}
