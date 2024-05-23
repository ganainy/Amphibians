/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui.screens

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.Amphibian
import com.example.marsphotos.network.AmphibiansApi
import kotlinx.coroutines.launch
import java.io.IOException


/**
 * UI state for the Home screen
 */
sealed interface AmphibianUiState {
    data class Success(val amphibians:
                      List<Amphibian> ) : AmphibianUiState
    data object Error : AmphibianUiState
    data object Loading : AmphibianUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class AmphibianViewModel : ViewModel() {

    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
       getAmphibians()
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getAmphibians() {
        viewModelScope.launch {
            amphibianUiState = AmphibianUiState.Loading
            amphibianUiState = try {
              val result = AmphibiansApi.retrofitService.getAmphibians()
                AmphibianUiState.Success(
                    result
                )
            } catch (e: IOException) {
                AmphibianUiState.Error
            } catch (e: HttpException) {
                AmphibianUiState.Error
            }
        }
        }

}
