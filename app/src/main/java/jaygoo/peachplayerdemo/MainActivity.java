package jaygoo.peachplayerdemo;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import jaygoo.peachplayer.media.MediaLoaderView;
import jaygoo.peachplayer.media.PeachVideoView;
import jaygoo.peachplayer.media.ScreenChangeController;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    String TAG = "fuck";
    private FrameLayout fullScreen;
    private CustomMediaPlayerController mMediaController;
    private PeachVideoView mVideoView;
    private ScreenChangeController mScreenChangeController;
    MediaLoaderController mediaLoaderController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMediaController = (CustomMediaPlayerController) findViewById(R.id.mediaController);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_video_back);
        toolbar.setTitle(MainActivity1.NAME);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMediaController.setSupportActionBar(getSupportActionBar());
        mMediaController.setMediaPlayer(mVideoView);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = (PeachVideoView) findViewById(R.id.ijkVideoView);

        mVideoView.setMediaController(mMediaController);
//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Video/demo1.mp4");

//        mVideoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/Movies/Screenrecords/demo.mp4");
        mVideoView.setVideoURI(Uri.parse(MainActivity1.src));
//        mVideoView.setHudView(mHudView);
//        fullScreen = (FrameLayout) findViewById(R.id.full_screen);
        mScreenChangeController = new ScreenChangeController((FrameLayout)findViewById(R.id.video_screen),null);

        mVideoView.setLoaderView((MediaLoaderView)LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_media_loader, null));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null){
            mVideoView.resume();
            mVideoView.start();

        }
        if (mMediaController != null){
            mMediaController.show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mScreenChangeController != null){
            mScreenChangeController.onConfigurationChanged(newConfig);

        }

    }

    @Override
    protected void onStop() {
        if (mVideoView != null){
            mVideoView.pause();
//            mVideoView.start();
        }
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null){
            mVideoView.stopPlayback();
            mVideoView.release(true);
        }

    }
}
