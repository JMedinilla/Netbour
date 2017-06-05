package jvm.ncatz.netbour;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickListener;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import de.cketti.mailto.EmailIntentBuilder;
import info.hoang8f.widget.FButton;

public class ActivityAbout extends MaterialAboutActivity {

    private MediaPlayer player;

    private int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.groupOthers_About);
    }

    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
        count = 0;

        MaterialAboutCard.Builder builderCardApp = new MaterialAboutCard.Builder();
        builderCardApp.title(R.string.aboutApp);
        MaterialAboutCard.Builder builderCardAuthor = new MaterialAboutCard.Builder();
        builderCardAuthor.title(R.string.aboutAuthor);
        MaterialAboutCard.Builder builderCardSocial = new MaterialAboutCard.Builder();
        builderCardSocial.title(R.string.aboutSocial);
        MaterialAboutCard.Builder builderCardOther = new MaterialAboutCard.Builder();
        builderCardOther.title(R.string.aboutOther);

        IconicsDrawable iconAppVersion = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info_outline)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconAppRepository = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github_box)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconAppLicenses = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_file)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconAuthorEmail = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_email)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconAuthorWeb = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_view_web)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconSocialGithub = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github_alt)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconSocialLinkedin = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_linkedin)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconSocialStack = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_stackoverflow)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconSocialTwitter = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_twitter)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);
        IconicsDrawable iconOtherBugs = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_bug)
                .color(ContextCompat.getColor(this, R.color.grey_800)).sizeDp(20);

        MaterialAboutTitleItem itemAppName = new MaterialAboutTitleItem(getString(R.string.app_name), ContextCompat.getDrawable(this, R.drawable.logo160));
        MaterialAboutActionItem itemAppVersion = new MaterialAboutActionItem(
                getString(R.string.app_version_title),
                getString(R.string.app_version_sub),
                iconAppVersion,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        count++;
                        if (count == 7) {
                            try {
                                stopPlaying();
                                player = MediaPlayer.create(ActivityAbout.this, R.raw.easteregg);
                                player.start();
                                count = 0;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        MaterialAboutActionItem itemAppRepository = new MaterialAboutActionItem(
                getString(R.string.app_repository_title),
                getString(R.string.app_repository_sub),
                iconAppRepository,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "https://github.com/JMedinilla/Netbour";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemAppLicenses = new MaterialAboutActionItem(
                getString(R.string.app_licenses_title),
                getString(R.string.app_licenses_sub),
                iconAppLicenses,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        DialogPlus dialogPlus = DialogPlus.newDialog(ActivityAbout.this)
                                .setGravity(Gravity.BOTTOM)
                                .setContentHolder(new ViewHolder(R.layout.licenses_dialog))
                                .setCancelable(true)
                                .setExpanded(true, 600)
                                .create();

                        View view = dialogPlus.getHolderView();
                        FButton apacheButton = (FButton) view.findViewById(R.id.apacheButton);
                        FButton mitButton = (FButton) view.findViewById(R.id.mitButton);
                        WebView webView = (WebView) view.findViewById(R.id.licensesWeb);
                        apacheButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AssetManager am = getAssets();
                                    InputStream is = am.open("apache");

                                    Scanner s = new Scanner(is).useDelimiter("\\A");
                                    String apache = s.hasNext() ? s.next() : "";

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAbout.this);
                                    builder.setTitle(R.string.apache_title);
                                    builder.setMessage(apache);
                                    builder.create().show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        mitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    AssetManager am = getAssets();
                                    InputStream is = am.open("mit");

                                    Scanner s = new Scanner(is).useDelimiter("\\A");
                                    String mit = s.hasNext() ? s.next() : "";

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAbout.this);
                                    builder.setTitle(R.string.mit_title);
                                    builder.setMessage(mit);
                                    builder.create().show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        try {
                            AssetManager am = getAssets();
                            InputStream is = am.open("licenses.html");

                            webView.loadData(inputStreamToString(is), "text/html", "UTF-8");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialogPlus.show();
                    }
                });
        MaterialAboutTitleItem itemAuthorName = new MaterialAboutTitleItem(
                getString(R.string.author_name),
                ContextCompat.getDrawable(this, R.drawable.favicon));
        MaterialAboutActionItem itemAuthorEmail = new MaterialAboutActionItem(
                getString(R.string.author_email_title),
                getString(R.string.author_email_sub),
                iconAuthorEmail,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        sendEmail(getString(R.string.author_email_subject));
                    }
                });
        MaterialAboutActionItem itemAuthorWeb = new MaterialAboutActionItem(
                getString(R.string.author_web_title),
                getString(R.string.author_web_sub),
                iconAuthorWeb,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "https://jvmedinilla.ncatz.com/";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemSocialGithub = new MaterialAboutActionItem(
                getString(R.string.social_github_title),
                getString(R.string.social_github_sub),
                iconSocialGithub,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "https://github.com/JMedinilla";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemSocialLinkedin = new MaterialAboutActionItem(
                getString(R.string.social_linkedin_title),
                getString(R.string.social_linkedin_sub),
                iconSocialLinkedin,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "https://www.linkedin.com/in/javier-medinilla-ag%C3%BCera-951749121/";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemSocialStackoverflow = new MaterialAboutActionItem(
                getString(R.string.social_stack_title),
                getString(R.string.social_stack_sub),
                iconSocialStack,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "http://stackoverflow.com/users/7426526/jmedinilla?tab=profile";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemSocialTwitter = new MaterialAboutActionItem(
                getString(R.string.social_twitter_title),
                getString(R.string.social_twitter_sub),
                iconSocialTwitter,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        String url = "https://twitter.com/JMedinillaDev";
                        openUrl(url);
                    }
                });
        MaterialAboutActionItem itemOtherBugs = new MaterialAboutActionItem(
                getString(R.string.other_bug_title),
                getString(R.string.other_bug_sub),
                iconOtherBugs,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        sendEmail(getString(R.string.other_bug_subject));
                    }
                });

        builderCardApp.addItem(itemAppName);
        builderCardApp.addItem(itemAppVersion);
        builderCardApp.addItem(itemAppRepository);
        builderCardApp.addItem(itemAppLicenses);
        builderCardAuthor.addItem(itemAuthorName);
        builderCardAuthor.addItem(itemAuthorEmail);
        builderCardAuthor.addItem(itemAuthorWeb);
        builderCardSocial.addItem(itemSocialGithub);
        builderCardSocial.addItem(itemSocialLinkedin);
        builderCardSocial.addItem(itemSocialStackoverflow);
        builderCardSocial.addItem(itemSocialTwitter);
        builderCardOther.addItem(itemOtherBugs);

        MaterialAboutList.Builder builderList = new MaterialAboutList.Builder();
        builderList.addCard(builderCardApp.build());
        builderList.addCard(builderCardAuthor.build());
        builderList.addCard(builderCardSocial.build());
        builderList.addCard(builderCardOther.build());

        return builderList.build();
    }

    private static String inputStreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void sendEmail(String subj) {
        EmailIntentBuilder.from(ActivityAbout.this)
                .to("javimedinilla@gmail.com")
                .subject(subj)
                .start();
    }

    private void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
