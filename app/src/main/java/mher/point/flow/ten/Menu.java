package mher.point.flow.ten;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mher.point.flow.ten.util.TypeFaceService;
import mher.point.flow.ten.help.help0.Help0;
import mher.point.flow.ten.util.SharedPref;
import mher.point.flow.ten.util.Sounds;

import static mher.point.flow.ten.key.ConsKey.IS_CONTINU_KEY;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.sound)
    AppCompatImageView soundView;
    @BindView(R.id.score_liner)
    LinearLayout scoreLiner;

    @BindView(R.id.continuGame)
    AppCompatButton continuBut;
    @BindView(R.id.new_game)
    AppCompatButton newGameBut;
    @BindView(R.id.help)
    AppCompatButton helpBut;

    @BindView(R.id.best_text)
    AppCompatTextView bestText;
    @BindView(R.id.best)
    AppCompatTextView best;
    @BindView(R.id.score_text)
    AppCompatTextView scoreText;
    @BindView(R.id.score)
    AppCompatTextView score;

    private boolean isSound;
    private boolean canContinuGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        continuBut.setVisibility(SharedPref.getInstance().getbtnList(this) != null ? View.VISIBLE : View.VISIBLE);
        best.setText(SharedPref.getInstance().getBest(this)+"");
        canContinuGame=checkContinuGame();
        initFonts();
        soundView.setOnClickListener(this);
        continuBut.setOnClickListener(this);
        newGameBut.setOnClickListener(this);
        helpBut.setOnClickListener(this);

    }

    private boolean checkContinuGame() {
        if (SharedPref.getInstance().getbtnList(this) != null) {
            if(SharedPref.getInstance().getbtnList(this).size()>1){
                scoreLiner.setVisibility(View.VISIBLE);
                continuBut.setVisibility(View.VISIBLE);
                score.setText(SharedPref.getInstance().getScore(this)+"");
                return true;
            }else {
                scoreLiner.setVisibility(View.GONE);
                continuBut.setVisibility(View.GONE);
                return false;
            }

        }else {
            scoreLiner.setVisibility(View.GONE);
            continuBut.setVisibility(View.GONE);
            return false;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        isSound=  SharedPref.getInstance().getSound(this);
        initSound(isSound);
    }
    private void initFonts() {

        continuBut.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        newGameBut.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        helpBut.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        score.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        scoreText.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        best.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        bestText.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sound:
                isSound=isSound?false:true;
                initSound(isSound);
                break;
            case R.id.continuGame:
                continueGame();
                break;
            case R.id.new_game:
                newGame();
                break;
            case R.id.help:
                help();
                break;

        }
    }
    private void continueGame() {
        Sounds.getInstance().stopsound();
        Intent intent = new Intent(this, GamePageActivity.class);
        intent.putExtra(IS_CONTINU_KEY, true);
        startActivity(intent);

    }
    public void newGame() {
        Sounds.getInstance().stopsound();
        Intent i = new Intent(this, GamePageActivity.class);
        i.putExtra(IS_CONTINU_KEY, false);
        startActivity(i);
    }
    public void help() {
        Sounds.getInstance().stopsound();
        Intent i  =new Intent(this,Help0.class);
        startActivity(i);

    }
    private void initSound(boolean k) {
        if (k) {
            soundView.setBackgroundResource(R.drawable.sound);
            Sounds.getInstance().playsound(this);
        } else {
            soundView.setBackgroundResource(R.drawable.mute);
            Sounds.getInstance().stopsound();
        }
        SharedPref.getInstance().saveSound(this,k);

    }

    @Override
    public void onBackPressed() {
        Sounds.getInstance().stopsound();
        Menu.this.finish();
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Sounds.getInstance().stopsound();
    }
}
