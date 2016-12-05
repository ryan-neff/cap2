package studyTool.models.notecardModels.noteCards;

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
    private Integer attempts;
    private Integer attemptsCorrect;


    public NoteCard() {
        setIsFront(true);
    }

    public NoteCard(String front, String back, String id, ArrayList<String> ImgPaths) {
        if (ImgPaths != null && ImgPaths.size() > 0) {
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

    public void addImg(String url){
        imgPaths.add(url);
        hasPics = true;

    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(final Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getAttemptsCorrect() {
        return attemptsCorrect;
    }

    public void setAttemptsCorrect(final Integer attemptsCorrect) {
        this.attemptsCorrect = attemptsCorrect;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteCard)) return false;

        final NoteCard noteCard = (NoteCard) o;

        if (getFront() != noteCard.getFront()) return false;
        if (isHasPics() != noteCard.isHasPics()) return false;
        if (getFront() != null ? !getFront().equals(noteCard.getFront()) : noteCard.getFront() != null) return false;
        if (getBack() != null ? !getBack().equals(noteCard.getBack()) : noteCard.getBack() != null) return false;
        if (getStackIndex() != null ? !getStackIndex().equals(noteCard.getStackIndex()) : noteCard.getStackIndex() != null)
            return false;
        if (getId() != null ? !getId().equals(noteCard.getId()) : noteCard.getId() != null) return false;
        if (getStackId() != null ? !getStackId().equals(noteCard.getStackId()) : noteCard.getStackId() != null)
            return false;
        return getImgPaths() != null ? getImgPaths().equals(noteCard.getImgPaths()) : noteCard.getImgPaths() == null;

    }

    @Override
    public int hashCode() {
        int result = getFront() != null ? getFront().hashCode() : 0;
        result = 31 * result + (getBack() != null ? getBack().hashCode() : 0);
        result = 31 * result + (getStackIndex() != null ? getStackIndex().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getStackId() != null ? getStackId().hashCode() : 0);
        result = 31 * result + (getIsFront() ? 1 : 0);
        result = 31 * result + (isHasPics() ? 1 : 0);
        result = 31 * result + (getImgPaths() != null ? getImgPaths().hashCode() : 0);
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
                ", isFront=" + isFront +
                ", hasPics=" + hasPics +
                ", imgPaths=" + imgPaths +
                '}';
    }
}
