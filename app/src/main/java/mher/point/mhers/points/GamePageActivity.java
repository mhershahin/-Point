package mher.point.mhers.points;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mher.point.mhers.points.dialog.IDialogLisener;
import mher.point.mhers.points.gamelogic.CircleView;
import mher.point.mhers.points.interfac.IGameCalBack;
import mher.point.mhers.points.util.TypeFaceService;
import mher.point.mhers.points.util.SharedPref;
import mher.point.mhers.points.util.Sounds;

import static mher.point.mhers.points.key.ConsKey.IS_CONTINU_KEY;

public class GamePageActivity extends AppCompatActivity implements IGameCalBack, IDialogLisener {
    @BindView(R.id.toolbarGamePage)
    Toolbar toolbarGame;
    @BindView(R.id.game_layout)
    RelativeLayout gameLayout;
    @BindView(R.id.game_page_scor)
    AppCompatTextView textScore;
    private CircleView gameView;
    //    private boolean gameOver = false;
    private boolean isContinu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarGame);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textScore.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        isContinu = (boolean) getIntent().getExtras().getSerializable(IS_CONTINU_KEY);


    }

    @Override
    protected void onStart() {
        super.onStart();
        creatGame(isContinu);


    }

    private void creatGame(boolean isCont) {

        if (SharedPref.getInstance().getSound(this)) {
            Sounds.getInstance().playsound(this);
        } else {
            Sounds.getInstance().stopsound();
        }

//        gameOver = false;
        gameLayout.removeAllViews();
        gameView = new CircleView(this, isContinu, this, this);
        gameLayout.addView(gameView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    private void pauseGame() {
        isContinu = true;
        gameView.saveData();
        Sounds.getInstance().stopsound();

        

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GamePageActivity.this, Menu.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void setScore(int score) {
        textScore.setText(score + "");
    }

    @Override
    public void gameOver() {
//        gameOver = true;
    }

    @Override
    public void newGame() {
        creatGame(false);
    }

    @Override
    public void shareLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");


        Intent chooser = Intent.createChooser(sendIntent, " ");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);

    }
}
