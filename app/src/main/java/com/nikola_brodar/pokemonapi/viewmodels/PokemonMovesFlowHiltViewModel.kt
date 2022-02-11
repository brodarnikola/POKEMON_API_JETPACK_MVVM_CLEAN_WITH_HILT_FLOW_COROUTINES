/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nikola_brodar.pokemonapi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola_brodar.domain.ResultState
import com.nikola_brodar.domain.usecase.PokemonMovesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonMovesFlowHiltViewModel @Inject constructor(
    private val getPokemonMoves: PokemonMovesUseCase
) : ViewModel() {

    private val _pokemonMovesMutableLiveData: MutableLiveData<ResultState<*>> = MutableLiveData()

    val pokemonMovesData: LiveData<ResultState<*>> = _pokemonMovesMutableLiveData

    fun getPokemonMovesFromLocalStorage() {

        viewModelScope.launch {
            delay(1000)
            getPokemonMoves.execute()
                .flowOn(Dispatchers.IO)
                .onEach { _pokemonMovesMutableLiveData.value = it }
                .catch { e -> e.printStackTrace() }
                .launchIn(viewModelScope)
        }
    }



}

