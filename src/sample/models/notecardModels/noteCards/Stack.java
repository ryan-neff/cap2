package sample.models.notecardModels.noteCards;

import java.util.List;

/**
 * @author rn046359
 */
public class Stack {
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Stack)) return false;

        final Stack stack = (Stack) o;

        if (!getId().equals(stack.getId())) return false;
        if (!getName().equals(stack.getName())) return false;
        if (!getCourse().equals(stack.getCourse())) return false;
        if (getSubject() != null ? !getSubject().equals(stack.getSubject()) : stack.getSubject() != null) return false;
        if (!getDateCreated().equals(stack.getDateCreated())) return false;
        if (!getDateModified().equals(stack.getDateModified())) return false;
        return getNoteCards().equals(stack.getNoteCards());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getCourse().hashCode();
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + getDateCreated().hashCode();
        result = 31 * result + getDateModified().hashCode();
        result = 31 * result + getNoteCards().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", subject='" + subject + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", noteCards=" + noteCards +
                '}';
    }
}
