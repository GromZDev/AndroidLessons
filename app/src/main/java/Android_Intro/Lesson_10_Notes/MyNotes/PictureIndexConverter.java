package Android_Intro.Lesson_10_Notes.MyNotes;

import java.util.Random;

import Android_Intro.Lesson_10_Notes.R;

public class PictureIndexConverter {
    private static Random rnd = new Random();
    private static Object syncObj = new Object();

    private static int[] picIndex = {R.drawable.fallout_1,
            R.drawable.fallout_2,
            R.drawable.fallout_3,
            R.drawable.fallout_4,
            R.drawable.fallout_5,
            R.drawable.fallout_6,
            R.drawable.fallout_7,
            R.drawable.fallout_8,
            R.drawable.fallout_9
    };

    public static int randomPictureIndex(){
        synchronized (syncObj){
            return rnd.nextInt(picIndex.length);
        }
    }
    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }
    public static int getIndexByPicture(int picture){
        for(int i = 0; i < picIndex.length; i++){
            if (picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }

}