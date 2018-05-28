package mher.point.mhers.points.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import mher.point.mhers.points.R;
import mher.point.mhers.points.util.TypeFaceService;
import mher.point.mhers.points.help.help0.Help0;


public class Help1 extends AppCompatActivity {
    @BindView(R.id.help1_next)
    AppCompatButton next;
    @BindView(R.id.help1_text1)
    AppCompatTextView text1;
    @BindView(R.id.help1_text2)
    AppCompatTextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        ButterKnife.bind(this);

        next.setTypeface(TypeFaceService.getInstance().getBellotaBold(this));
        text1.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));
        text2.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));

        next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Help1.this, Help2.class);
        startActivity(i);
    }
});
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Help1.this, Help0.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }



}
