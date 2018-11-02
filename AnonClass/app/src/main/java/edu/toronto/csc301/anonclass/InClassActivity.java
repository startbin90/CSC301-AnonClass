package edu.toronto.csc301.anonclass;

import android.content.IntentSender;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.toronto.csc301.anonclass.util.Question;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;

public class InClassActivity extends AppCompatActivity implements ChatRoomFragment.OnChatRoomFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private User user;
    private String session;
    private GetQuestionsTask mGetQuestionTask;
    private SendQuestionTask mSendQuestionTask;
    private List<Question> questions = new ArrayList<>();
    private Fragment current_frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class);

        user = User.deSerialize(getIntent().getStringExtra("user"));
        session = getIntent().getStringExtra("session_num");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        attemptGetQuestions();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_in_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            onBackPressed();
            return true;
        }*/
        onBackPressed();
        return true;
    }

    @Override
    public void onSendClicked(String question) {
        attemptSendQuestion(new Question(user.getEmail(), question, new Date()));
    }

    @Override
    public List<Question> requestQuestions() {
        return questions;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    Fragment frag = ChatRoomFragment.newInstance();
                    current_frag = frag;
                    return frag;
                case 1:
                    return ChatRoomFragment.newInstance();
                case 2:
                    return ChatRoomFragment.newInstance();
                default:
                    return ChatRoomFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private void attemptGetQuestions() {
        if (mGetQuestionTask != null) {
            return;
        }

        mGetQuestionTask = new GetQuestionsTask(session);
        mGetQuestionTask.execute((Void) null);
    }

    private void attemptSendQuestion(Question question) {
        if (mSendQuestionTask != null) {
            return;
        }

        mSendQuestionTask = new SendQuestionTask(session, question);
        mSendQuestionTask.execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SendQuestionTask extends AsyncTask<Void, Void, retMsg> {

        private final String session;
        private final Question question;

        SendQuestionTask(String session, Question question) {
            this.session = session;
            this.question = question;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt to send a question to session

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            return retMsg.getErrorRet(0);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mSendQuestionTask = null;
            //showProgress(false);

            if (ret.getErrorCode() == 0) {
                InClassActivity.this.questions.add(question);
                if (InClassActivity.this.current_frag instanceof ChatRoomFragment){
                    ((ChatRoomFragment) InClassActivity.this.current_frag).refreshList();
                    ((ChatRoomFragment) InClassActivity.this.current_frag).message.setText("");
                }
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mSendQuestionTask = null;
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetQuestionsTask extends AsyncTask<Void, Void, retMsg> {

        private final String session;

        GetQuestionsTask(String session) {
            this.session = session;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            return retMsg.getQuestionsRet(0, Question.getDummyQuestions());
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mGetQuestionTask = null;
            //showProgress(false);

            if (ret.getErrorCode() == 0) {
                InClassActivity.this.questions.clear();
                InClassActivity.this.questions.addAll(ret.getQuestions());
                if (InClassActivity.this.current_frag instanceof ChatRoomFragment){
                    ((ChatRoomFragment) InClassActivity.this.current_frag).refreshList();
                }
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mGetQuestionTask = null;
        }
    }

}
