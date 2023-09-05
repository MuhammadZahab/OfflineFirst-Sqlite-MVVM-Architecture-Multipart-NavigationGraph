package com.assessment.todo.ui.upload_pic

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import com.assessment.todo.data.upload_pic.remote.model.UploadRequestBody
import com.assessment.todo.databinding.FragmentPictureBinding
import com.assessment.todo.utils.Constants.URL_MULTIPART
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI


@AndroidEntryPoint
class PictureFragment : Fragment(),UploadRequestBody.UploadCallback {

    private lateinit var binding: FragmentPictureBinding
    private var imageUri: Uri? = null
    private lateinit var viewModel: UploadPictureViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPictureBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[UploadPictureViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        clickListener()

    }

    private fun observer() {

        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun clickListener() {
        binding.btnSelectPicture.setOnClickListener {

            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult?.launch(intent)
                }

        }

        binding.btnUploadPicture.setOnClickListener {

            uploadImage(imageUri!!)

        }
    }

    private fun uploadImage(imageUri:Uri) {

        if (imageUri == null) {
            return
        }

        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
            imageUri!!, "r", null
        ) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(URI(imageUri.toString()))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        viewModel.uploadPicture(URL_MULTIPART,
            file

        )

    }



  private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data!!

            imageUri = fileUri
            binding.imageview.setImageURI(fileUri)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleStateChange(state: UploadPictureViewModel.UploadPictureState) {
        when (state) {
            is UploadPictureViewModel.UploadPictureState.Init -> Unit
            is UploadPictureViewModel.UploadPictureState.Error -> handleError(state.rawResponse)
            is UploadPictureViewModel.UploadPictureState.Success -> handleSuccess(state.imageUploadResponseModel)
            is UploadPictureViewModel.UploadPictureState.IsLoading -> handleLoading(state.isLoading)
            is UploadPictureViewModel.UploadPictureState.Exception -> handleException(state.exception)
        }
    }


    private fun handleException(exception: Any) {
        Log.d("TAG", "handleException: $exception")
    }

    private fun handleError(response: Any) {
        Log.d("TAG", "handleException: $response")

    }

    private fun handleLoading(isLoading: Boolean) {
        Log.d("TAG", "handleException: $isLoading")
    }

    private fun handleSuccess(imageUploadResponseModel: ImageUploadResponseModel) {


        Glide
            .with(this)
            .load(imageUploadResponseModel.result?.get(0)?.image)
            .centerCrop()
            .into(binding.imageview);


    }

    override fun onProgressUpdate(percentage: Int) {
    }

}