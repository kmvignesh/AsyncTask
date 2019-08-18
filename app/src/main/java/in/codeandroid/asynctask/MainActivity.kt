package `in`.codeandroid.asynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var progressAsyncTask: ProgressAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disableView(btn_stop, true)

        btn_start.setOnClickListener {
            progressAsyncTask = ProgressAsyncTask()
            progress.max = 60
            progressAsyncTask!!.execute(60)
        }

        btn_stop.setOnClickListener {
            progressAsyncTask?.cancel(true)
        }

    }

    fun disableView(view: View, isDisabled: Boolean) {
        if (isDisabled) {
            view.alpha = 0.5f
            view.isEnabled = false
        } else {
            view.alpha = 1f
            view.isEnabled = true
        }
    }

    inner class ProgressAsyncTask : AsyncTask<Int, Int, Void>() {

        override fun onPreExecute() {
            disableView(btn_start, true)
            disableView(btn_stop, false)
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Int?): Void? {
            if (p0.isNotEmpty()) {
                if (p0[0] != null) {
                    for (i in 0..p0[0]!!) {
                        Thread.sleep(1000)
                        publishProgress(i)
                    }
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            if (values.isNotEmpty() && values[0] != null)
                progress.progress = values[0]!!
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Void?) {
            disableView(btn_start, false)
            disableView(btn_stop, true)
            super.onPostExecute(result)
        }

        override fun onCancelled() {
            disableView(btn_start, false)
            disableView(btn_stop, true)
            super.onCancelled()
        }

    }
}
