package studyTool.models.controllersAndViews;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

class Projector extends Pane {
        final Random random = new Random();
  // List of URL strings
        List<String> imageFiles = new Vector<String>();
          // The current index into the imageFile
        int currentIndex = -1;
          // Enumeration of next and previous button directions
        public static enum ButtonMove {NEXT, PREV};
          // Current image view display
        public ImageView currentImageView;
          // Loading progress indicator
        public ProgressIndicator progressIndicator;
          // mutex */
        public AtomicBoolean loading = new AtomicBoolean();
        Pane projectorPane;
        public Projector(){

        }






}
