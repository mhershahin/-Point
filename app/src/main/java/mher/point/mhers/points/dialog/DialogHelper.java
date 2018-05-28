package mher.point.mhers.points.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;


import mher.point.mhers.points.R;
import mher.point.mhers.points.util.TypeFaceService;
import mher.point.mhers.points.util.SharedPref;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by mhers on 17/04/2018.
 */

public class DialogHelper {
    public static DialogHelper INSTANCE = null;

    public static DialogHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DialogHelper();
        }
        return INSTANCE;
    }

    public void showDialog(Context context,final IDialogLisener iDialogLisener,int scorValu){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().requestFeature(FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(500, 400);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        AppCompatTextView gameOver = (AppCompatTextView) dialog.findViewById(R.id.dialog_text_gameover);
        AppCompatTextView bestText = (AppCompatTextView) dialog.findViewById(R.id.dialog_best_text);
        AppCompatTextView best = (AppCompatTextView) dialog.findViewById(R.id.dialog_best);
        AppCompatTextView scoreText = (AppCompatTextView) dialog.findViewById(R.id.dialog_score_text);
        AppCompatTextView score = (AppCompatTextView) dialog.findViewById(R.id.dialog_score);
        AppCompatImageView share = (AppCompatImageView) dialog.findViewById(R.id.dialog_share);
        AppCompatImageView newGame = (AppCompatImageView) dialog.findViewById(R.id.dialog_new_game);


        gameOver.setTypeface(TypeFaceService.getInstance().getBellotaBold(context));
        bestText.setTypeface(TypeFaceService.getInstance().getBellotaRegular(context));
        best.setTypeface(TypeFaceService.getInstance().getBellotaRegular(context));
        scoreText.setTypeface(TypeFaceService.getInstance().getBellotaRegular(context));
        score.setTypeface(TypeFaceService.getInstance().getBellotaRegular(context));

        score.setText(scorValu+"");
        best.setText(SharedPref.getInstance().getBest(context)+"");

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            iDialogLisener.shareLink();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iDialogLisener.newGame();

            }
        });

//        text.setText(isRefres?activity.getString(R.string.play_again):activity.getString(R.string.exit));
//
//        pozitiv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                lisener.yes();
//            }
//        });
//
//        negativ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }

}

