package jvm.ncatz.netbour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickListener;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

public class ActivityAbout extends MaterialAboutActivity {

    private int cont;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        cont = 0;
    }

    @Nullable
    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.groupOthers_About);
    }

    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
        MaterialAboutCard.Builder builderCardApp = new MaterialAboutCard.Builder();
        builderCardApp.title(R.string.aboutApp);
        MaterialAboutCard.Builder builderCardAuthor = new MaterialAboutCard.Builder();
        builderCardAuthor.title(R.string.aboutAuthor);
        MaterialAboutCard.Builder builderCardSocial = new MaterialAboutCard.Builder();
        builderCardSocial.title(R.string.aboutSocial);
        MaterialAboutCard.Builder builderCardOther = new MaterialAboutCard.Builder();
        builderCardOther.title(R.string.aboutOther);

        IconicsDrawable iconAppVersion = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info_outline).color(Color.BLACK).sizeDp(20);
        IconicsDrawable iconAppRepository = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github_box).color(Color.BLACK).sizeDp(20);
        IconicsDrawable iconAuthorEmail = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_email).color(Color.RED).sizeDp(20);
        IconicsDrawable iconAuthorWeb = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_view_web).color(Color.RED).sizeDp(20);
        IconicsDrawable iconSocialGithub = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_github_alt).color(Color.BLUE).sizeDp(20);
        IconicsDrawable iconSocialLinkedin = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_linkedin).color(Color.BLUE).sizeDp(20);
        IconicsDrawable iconSocialStack = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_stackoverflow).color(Color.BLUE).sizeDp(20);
        IconicsDrawable iconSocialTwitter = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_twitter).color(Color.BLUE).sizeDp(20);
        IconicsDrawable iconOtherBugs = new IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_bug).color(Color.GRAY).sizeDp(20);

        MaterialAboutTitleItem itemAppName = new MaterialAboutTitleItem(getString(R.string.app_name), ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
        MaterialAboutActionItem itemAppVersion = new MaterialAboutActionItem(
                getString(R.string.app_version_title),
                getString(R.string.app_version_sub),
                iconAppVersion,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        cont++;
                        if (cont == 7) {
                            cont = 0;
                            //SebastianRunner
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

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void sendEmail(String subj) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "javimedinilla@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, subj);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, getString(R.string.email_report)));
    }
}
