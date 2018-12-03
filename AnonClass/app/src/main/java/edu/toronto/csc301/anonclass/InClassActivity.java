package edu.toronto.csc301.anonclass;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.FileItem;
import edu.toronto.csc301.anonclass.util.PassingData;
import edu.toronto.csc301.anonclass.util.Question;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;

/**
 * AnonClassActivity->ClassStarterFragment->this
 *
 * This activity contains two children fragments
 * 1. ChatRoomFragment
 * 2. FileFragment
 *
 * Contains SendQuestion AsyncTask used to send a new question to server
 *
 * This Activity calls startHandlerThread() onCreate which starts to refresh the question set
 * periodically.
 */
public class InClassActivity extends AppCompatActivity
        implements ChatRoomFragment.OnChatRoomFragmentInteractionListener,
                    FileFragment.OnFileFragmentInteractionListener{

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
    private Course course;
    private SendQuestionTask mSendQuestionTask;
    private List<Question> questions = new ArrayList<>();
    private ChatRoomFragment chat_frag = null;
    private FileFragment file_frag = null;

    private HandlerThread mHandlerThread = null;
    private Handler mHandler = null;

    private static String TAG = "InClassActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class);

        user = User.deSerialize(getIntent().getStringExtra("user"));
        course = Course.deSerialize(getIntent().getStringExtra("course"));
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

        startHandlerThread();
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
        attemptSendQuestion(new Question(user.getEmail(), question, new Date(), course.getCourse_id()));
    }

    @Override
    public List<Question> requestQuestions() {
        return questions;
    }


    @Override
    public void onListFragmentInteraction(FileItem file) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(file.getUrl()));
        startActivity(i);
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
                    chat_frag = ChatRoomFragment.newInstance();
                    return chat_frag;
                case 1:
                    file_frag = new FileFragment();
                    return file_frag;
                default:
                    return ChatRoomFragment.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    private void attemptSendQuestion(Question question) {
        if (mSendQuestionTask != null) {
            return;
        }

        mSendQuestionTask = new SendQuestionTask(question);
        mSendQuestionTask.execute((Void) null);
    }

    private void startHandlerThread(){
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mHandler.post(new refreshRunnable());
    }
    public class refreshRunnable implements Runnable{

        @Override
        public void run() {

            final retMsg ret;
            if (LoginActivity.DEBUG == 1){
                ret = retMsg.getQuestionsRet(0, Question.getDummyQuestions());
            }else {
                ret = PassingData.RefreshQuestionRoom(course.getCourse_id());
            }

            if (ret.getErrorCode() == 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InClassActivity.this.questions.clear();
                        InClassActivity.this.questions.addAll(ret.getQuestions());
                        if (InClassActivity.this.chat_frag != null) {
                            InClassActivity.this.chat_frag.refreshList();
                        }
                    }
                });

            }else {
                Log.i(TAG, "get question failed");
            }
            mHandler.postDelayed(this, 10000);
        }
    }
    /**
     * send question to server
     */
    public class SendQuestionTask extends AsyncTask<Void, Void, retMsg> {

        private final Question question;

        SendQuestionTask(Question question) {
            this.question = question;
        }

        @Override
        protected retMsg doInBackground(Void... params) {

            if (LoginActivity.DEBUG == 1){
                Question.addDummyQuestion(question);
                return retMsg.getErrorRet(0);
            }
            return PassingData.AskingQuestion(question);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mSendQuestionTask = null;
            //showProgress(false);

            if (ret.getErrorCode() == 0) {
                if (InClassActivity.this.chat_frag != null){
                    InClassActivity.this.chat_frag.message.setText("");
                }
                InClassActivity.this.mHandler.post(new refreshRunnable());
            } else {
                Toast.makeText(InClassActivity.this, "Message send failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mSendQuestionTask = null;
        }
    }
}
