package mher.point.flow.ten.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mhers on 17/04/2018.
 */

public class TypeFaceService {
    private static TypeFaceService INSTANCE = null;


    public static TypeFaceService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TypeFaceService();
        }
        return INSTANCE;
    }


    private TypeFaceService() {

    }

    private static final String TYPEFACE_FOLDER = "fonts";
    private static final String TYPEFACE_EXTENSION = ".ttf";

    private Map<String, Typeface> typeFaces = new HashMap<>(30);

    public Typeface getTypeFace(Context context, String fileName) {
        Typeface typeface = typeFaces.get(fileName);

        if (typeface == null) {
            String fontPath = new StringBuilder(TYPEFACE_FOLDER).append('/')
                    .append(fileName).append(TYPEFACE_EXTENSION).toString();
            typeface = Typeface.createFromAsset(context.getAssets(),
                    fontPath);
            typeFaces.put(fileName, typeface);
        }

        return typeface;
    }


    public Typeface getBellotaBold(Context context) {
        return getTypeFace(context, "Bellota-Bold");
    }

    public Typeface getBellotaRegular(Context context) {
        return getTypeFace(context, "Bellota-Regular");
    }
}
