import com.example.myapplicationlist.data.Faculty
import com.example.myapplicationlist.repository.MainRepository
import com.example.myapplicationlist.data.ListOfFaculty
import androidx.lifecycle.Observer

class FacultyViewModel : ViewModel() {
    var facultyList: MutableLiveData<ListOfFaculty> = MutableLiveData()
    private var _faculty: Faculty? = null

    val faculty
        get() = _faculty

    private val facultyListObserver = Observer<ListOfFaculty> { list ->
        facultyList.postValue(list)
    }

    init {
        MainRepository.getInstance().listOfFaculty.observeForever(facultyListObserver)
        MainRepository.getInstance().faculty.observeForever {
            _faculty = it
        }
    }

    fun deleteFaculty() {
        if (_faculty != null)
            MainRepository.getInstance().deleteFaculty(_faculty!!)
    }

    fun appendFaculty(facultyName: String) {
        val faculty = Faculty()
        faculty.name = facultyName
        MainRepository.getInstance().updateFaculty(faculty)
    }

    fun updateFaculty(facultyName : String){
        if (_faculty!=null){
            _faculty!! name=FacultyName
            MainRepository.getInstance().updateFaculty(_faculty!!)
        }
    }

    fun setCurrentFaculty(faculty:Faculty){
        MainRepository.getInstance().setCurrentFaculty(faculty)
    }
}