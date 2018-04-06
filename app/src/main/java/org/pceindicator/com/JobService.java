package org.pceindicator.com;
import com.firebase.jobdispatcher.JobParameters;

/**
 * Created by ABHIJAY on 1/30/2018.
 */

public class JobService extends com.firebase.jobdispatcher.JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
