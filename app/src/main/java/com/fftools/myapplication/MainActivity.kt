package com.fftools.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.SeekBar
import com.fftools.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bitmap: Bitmap
    private lateinit var mapOutput: Mat

    init {
        System.loadLibrary("native-lib")
        if (OpenCVLoader.initDebug()) {
            Log.d("fasdfafdas", "install success")
        } else {
            Log.d("fasdfafdas", "install fail")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMain()
    }

    private var scale = 0f
    private fun initMain() {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_woman)
        bitmap = resizeBitmap(bitmap, 10000000)
        binding.iv1.setImageBitmap(bitmap)
        mapOutput = Mat()
        binding.bt.setOnClickListener {
            val mapInput = bitmapToMat(bitmap)
            scaleImage(mapInput.nativeObjAddr, mapOutput.nativeObjAddr, 1.6f)

            binding.iv2.setImageBitmap(matToBitmap(mapOutput))

        }



    }

    private external fun otherWay(bitmapInput: Bitmap,bitmapOutput: Bitmap)



    private external fun scaleImage(input: Long, output: Long, scale: Float)

    private external fun testImage(input: Long, output: Long)

    private fun bitmapToMat(bitmap: Bitmap): Mat {
        val mat = Mat(bitmap.height, bitmap.width, CvType.CV_8UC4)
        Utils.bitmapToMat(bitmap, mat)
        return mat
    }

    private fun matToBitmap(mat: Mat): Bitmap {
        val bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, bitmap)
        return bitmap
    }


    private fun resizeBitmap(bitmap: Bitmap, maxSizeInBytes: Int): Bitmap {
        var width = bitmap.width
        var resizedBitmap = bitmap
        val reduceWith = width / 20
        while (resizedBitmap.byteCount > maxSizeInBytes) {
            width -= reduceWith
            resizedBitmap = Bitmap.createScaledBitmap(
                bitmap,
                width,
                ((bitmap.height * width) / bitmap.width),
                true
            )
        }
        return resizedBitmap
    }

}