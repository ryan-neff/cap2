package sample.models.notecardModels.noteCards;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rn046359
 */
public class NoteCard {
    private String front;
    private String back;
    private Integer stackIndex = 0;
    private String id;
    private String stackId;
    private boolean isFront;
    private boolean hasPics = false;
    private List<String> imgPaths = new ArrayList();


    public NoteCard() {
    }

    public NoteCard(String front, String back, String id, ArrayList<String> ImgPaths) {
        if (ImgPaths.size() > 0) {
            imgPaths = ImgPaths;
            hasPics = true;

        }

        setIsFront(true);
        setFront(front);
        setBack(back);
        setId(id);
    }

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

    public boolean getIsFront() {
        return isFront;
    }

    public void setIsFront(final boolean front) {
        isFront = front;
    }

    public boolean isHasPics() {
        return hasPics;
    }

    public void setHasPics(final boolean hasPics) {
        this.hasPics = hasPics;
    }

    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(final List<String> imgPaths) {
        this.imgPaths = imgPaths;
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

    public void addImg(String url){
        imgPaths.add(url);
        hasPics = true;

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
