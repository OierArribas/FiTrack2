package com.example.fittrack.ui.Screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.asImageBitmap


import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.fittrack.ui.Navigation.NavItem
import com.example.fittrack.ui.ViewModels.LoginResultHandler
import com.example.fittrack.ui.ViewModels.LoginViewModel
import com.example.fittrack.ui.ViewModels.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Camera(
    applicationContext: Context,
    navController: NavController,
    loginViewModel: LoginViewModel
){


    fun takePhoto(
        controler: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ){
        controler.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object: OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    Log.i("Camera,","Photo succesfully taken")
                    

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    onPhotoTaken(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera,","Could not take photo exception")
                }
            }
        )
    }

    val scope = rememberCoroutineScope()
    val bitmaps by loginViewModel.bitmaps.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val controler = remember{
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            PhotoBottomSheetContent(bitmapList = bitmaps, modifier = Modifier.fillMaxWidth())
        }
    ) {
        padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CameraPreview(
                controler = controler,
                modifier = Modifier.fillMaxSize()
            )
        }
        IconButton(onClick = {
                controler.cameraSelector =
                    if(controler.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA){
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
            },
            modifier = Modifier
                .offset(16.dp, 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh,
                contentDescription = "Switch camera"
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                            takePhoto(
                                controler = controler,
                                onPhotoTaken = loginViewModel::onTakePhoto
                            )
                            //navController.navigate(route = NavItem.ScreenPrincipal.route)
                    }
                ) {
                    Icon(Icons.Default.Camera, contentDescription = "Cámara")
                }
            }
        }

        /*
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {

            IconButton(onClick = {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            },
                modifier = Modifier.background(Color.Black)
            ) {
                Icon(imageVector = Icons.Default.AccountBox,
                    contentDescription = "OpenGallery")
            }
        }
*/


    }

}

@Composable
fun CameraPreview(
    controler: LifecycleCameraController,
    modifier: Modifier

){
    val lifecycleCameraController = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controler
                controler.bindToLifecycle(lifecycleCameraController)
            }
        },
        modifier = modifier
    )
}

@Composable
fun PhotoBottomSheetContent(bitmapList: List<Bitmap>, modifier: Modifier) {

    if(bitmapList.isEmpty()){
        Box (
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Text(text = "There are no files yet")
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier,
        ) {
            items(bitmapList.size) { index ->
                Image(
                    bitmap = bitmapList[index].asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(1f)
                )
            }
        }
    }



}



