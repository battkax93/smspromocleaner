package sunny.smspromocleaner.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MyJobServices extends JobService {
    private static final String TAG = MyJobServices.class.getSimpleName();


    @Override
    public boolean onStartJob(JobParameters params) {

        Log.w(TAG, "onStartJob JobId: " + params.getJobId());

        Toast.makeText(this,"tesJobScheduler",Toast.LENGTH_SHORT).show();

        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "onStopJob");
        Toast.makeText(this, "onStopJob JobId:" + params.getJobId(), Toast.LENGTH_SHORT).show();
        return false;
    }


}
