package sample.models.notecardModels.noteCards;

/**
 * @author rn046359
 */
public class NoteCard {
    private String front;
    private String back;
    private Integer stackIndex;


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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteCard)) return false;

        final NoteCard noteCard = (NoteCard) o;

        if (!getFront().equals(noteCard.getFront())) return false;
        if (!getBack().equals(noteCard.getBack())) return false;
        return getStackIndex().equals(noteCard.getStackIndex());

    }

    @Override
    public int hashCode() {
        int result = getFront().hashCode();
        result = 31 * result + getBack().hashCode();
        result = 31 * result + getStackIndex().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NoteCard{" +
                "front='" + front + '\'' +
                ", back='" + back + '\'' +
                ", stackIndex=" + stackIndex +
                '}';
    }
}
