package mher.point.mhers.points.help.help0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mher.point.mhers.points.R;
import mher.point.mhers.points.Menu;
import mher.point.mhers.points.util.TypeFaceService;
import mher.point.mhers.points.help.Help1;

/**
 * Created by mhers on 20/05/2018.
 */

public class Help0 extends AppCompatActivity implements IUnderstand {
    @BindView(R.id.next1)
    AppCompatButton nextToHelp1;
    @BindView(R.id.relativ_help)
    RelativeLayout relativeLayout;
    @BindView(R.id.help_text)
    AppCompatTextView textHelp;

   private HelpCircleView helpCircleView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        helpCircleView= new HelpCircleView(this,this);
        relativeLayout.addView(helpCircleView);


        nextToHelp1.setTypeface(TypeFaceService.getInstance().getBellotaBold(this));
        textHelp.setTypeface(TypeFaceService.getInstance().getBellotaRegular(this));

        nextToHelp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helpCircleView.stopBlink();
        Intent i = new Intent(Help0.this, Menu.class);
        Help0.this.finish();
        startActivity(i);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

    @Override
    public void understand() {
        goNext();
    }

    private void goNext() {
        Intent i = new Intent(Help0.this, Help1.class);
        startActivity(i);
    }


}