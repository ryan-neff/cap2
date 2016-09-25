package sample.models.notecardModels.noteCards;

/**
 * @author rn046359
 */
public class NoteCard {
    private String front;
    private String back;
    private Integer stackIndex;
    private String id;
    public String stackId;



    public String getFront() {
        return front;
    }

    public void setFront(final String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(final String back) {
        this.back = back;
    }

    public Integer getStackIndex() {
        return stackIndex;
    }

    public void setStackIndex(final Integer stackIndex) {
        this.stackIndex = stackIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(final String stackId) {
        this.stackId = stackId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteCard)) return false;

        final NoteCard noteCard = (NoteCard) o;

        if (!getFront().equals(noteCard.getFront())) return false;
        if (!getBack().equals(noteCard.getBack())) return false;
        if (!getStackIndex().equals(noteCard.getStackIndex())) return false;
        if (!getId().equals(noteCard.getId())) return false;
        return getStackId().equals(noteCard.getStackId());

    }

    @Override
    public int hashCode() {
        int result = getFront().hashCode();
        result = 31 * result + getBack().hashCode();
        result = 31 * result + getStackIndex().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getStackId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NoteCard{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                ", stackIndex=" + stackIndex +
                ", id='" + id + '\'' +
                ", stackId='" + stackId + '\'' +
                '}';
    }
}
