package mher.point.flow.ten.help;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import mher.point.flow.ten.R;
import mher.point.flow.ten.GamePageActivity;
import mher.point.flow.ten.util.TypeFaceService;

import static mher.point.flow.ten.key.ConsKey.IS_CONTINU_KEY;

public class Help2 extends AppCompatActivity {

    @BindView(R.id.help2_text)
    AppCompatTextView text;
    @BindView(R.id.help2_next)
    AppCompatButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
        ButterKnife.bind(this);
        next.setTypeface(TypeFaceService.getInstance().getBellotaBold(this));
        text.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    public void newGame() {
        Intent i = new Intent(this, GamePageActivity.class);
        i.putExtra(IS_CONTINU_KEY, false);
        startActivity(i);
    }
}