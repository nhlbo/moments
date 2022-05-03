package com.example.moments.ui.main.qrCode

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.budiyev.android.codescanner.*
import com.example.moments.R
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.image_qr_code.*


class QRCodeViewPagerAdapter : FragmentStateAdapter {
    lateinit var userID: String

    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, userID: String) : super(
        fragmentManager,
        lifecycle
    ) {
        this.userID = userID
    }

    constructor(fragment: Fragment) : super(fragment)

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> QRCodeFragment(userID)
            else -> {
                CameraFragment()
            }
        }
    }
}

class QRCodeFragment(val userID: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.image_qr_code, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createQRCode()
    }

    private fun createQRCode() {
        val multiFormatWriter = MultiFormatWriter()

        try {
            val bitMatrix: BitMatrix = multiFormatWriter.encode(
                userID, BarcodeFormat.QR_CODE, 500, 500
            )
            val barcodeEncoder = BarcodeEncoder();
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imQRCode.setImageBitmap(bitmap);
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }
}

class CameraFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.scan_qr_code, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupPermissions(view)
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            codeScanner.decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    val intent = Intent(activity, OtherProfileActivityView::class.java)
                    intent.putExtra("USER_ID",it.text)
                    startActivity(intent)
                }
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions(view: View) {
        val permissions: Int = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA
            )
        }!!
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.CAMERA),10)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            10 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context,"You need the camera permission to be able to use this function",Toast.LENGTH_SHORT)
                }
            }
        }
    }
}