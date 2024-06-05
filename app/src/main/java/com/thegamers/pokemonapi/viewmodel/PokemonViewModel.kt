import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thegamers.pokemonapi.model.Pokemon
import com.thegamers.pokemonapi.model.PokemonDetail
import com.thegamers.pokemonapi.network.PokemonApi
import com.thegamers.pokemonapi.network.dao.CaughtPokemon
import com.thegamers.pokemonapi.network.db.PokemonDatabase
import com.thegamers.pokemonapi.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PokemonRepository
    val allCaughtPokemon: LiveData<List<CaughtPokemon>>

    init {
        val pokemonDatabase = PokemonDatabase.getDatabase(application)
        val pokemonDao = pokemonDatabase.pokemonDao()
        repository = PokemonRepository(pokemonDao)
        allCaughtPokemon = repository.allCaughtPokemon
    }

    fun insert(caughtPokemon: CaughtPokemon) = viewModelScope.launch {
        repository.insert(caughtPokemon)
    }

    fun delete(caughtPokemon: CaughtPokemon) = viewModelScope.launch {
        repository.delete(caughtPokemon)
    }

    // Network operations using Retrofit
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail> get() = _pokemonDetail

    fun fetchPokemonList(limit: Int) {
        viewModelScope.launch {
            try {
                val response = PokemonApi.apiService.getPokemonList(limit)
                _pokemonList.postValue(response.results)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                val response = PokemonApi.apiService.getPokemonDetail(name)
                _pokemonDetail.postValue(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
