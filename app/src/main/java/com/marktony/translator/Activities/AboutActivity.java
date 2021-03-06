package com.marktony.translator.Activities;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.marktony.translator.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout layoutRate;
    private LinearLayout layoutFeedbackAdvice;
    private LinearLayout layoutDonate;
    private TextView tvGitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();

        layoutFeedbackAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Uri uri = Uri.parse(getString(R.string.sendto));
                    Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                    intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.topic));
                    intent.putExtra(Intent.EXTRA_TEXT,
                            getString(R.string.phone_model) + Build.MODEL
                                    + "\n" + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n");
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    Snackbar.make(layoutFeedbackAdvice, R.string.no_mail_app,Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        layoutDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder  dialog = new MaterialDialog.Builder(AboutActivity.this);
                dialog.title(R.string.donate_title);
                dialog.content(R.string.donate_content);
                dialog.positiveText(R.string.OK);
                dialog.negativeText(R.string.cancel);
                dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        //将指定账号添加到剪切板
                        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", getString(R.string.donate_account));
                        manager.setPrimaryClip(clipData);

                        dialog.dismiss();
                    }
                });
                dialog.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Uri uri = Uri.parse("market://details?id="+getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex){
                    Snackbar.make(layoutRate, R.string.no_app_store,Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        tvGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(getString(R.string.github_url))));
            }
        });

    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutRate = (LinearLayout) findViewById(R.id.LL_rate);
        layoutFeedbackAdvice = (LinearLayout) findViewById(R.id.LL_bugs);
        layoutDonate = (LinearLayout) findViewById(R.id.LL_support);

        tvGitHub = (TextView) findViewById(R.id.tv_github);

    }

}