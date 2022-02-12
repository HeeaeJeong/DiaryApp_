import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diary.model.Note
import com.nickpopyk.diaryapplication.data.NoteDatabase
import com.nickpopyk.diaryapplication.repository.NoteRepository
import kotlinx.coroutines.Dispatchers

class NoteViewModal(application: Application) : AndroidViewModel(application) {
    val allNotes: LiveData<List<Note>>
    val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}