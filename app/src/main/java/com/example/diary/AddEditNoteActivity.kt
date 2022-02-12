package com.example.diary

import NoteViewModal
import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.diary.model.Note
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    //on below line we are creating variables for our UI components.
    //UI 구성요소 변수 생성.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    //on below line we are creating variable for viewmodal and and integer for our note id.
    //viewmodal에 대한 변수와 noteid 정수 생성.
    lateinit var viewModal: NoteViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        //on below line we are initlaiing our view modal.
        //viewmodal 초기화
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)
        //on below line we are initializing all our variables.
        //모든 변수 초기화
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        //on below line we are getting data passsed via an intent.
        //intent를 통해 데이터를 받음
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            //on below line we are setting data to edit text.
            //텍스트 편집 데이터 설정
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Save Note")
        }

        //on below line we are adding click listner to our save button.
        //클릭하면 저장하는 리스너 생성.
        saveBtn.setOnClickListener {
            //on below line we are getting title and desc from edit text.
            //타이틀과 편집텍스트를 가져옴.
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            //on below line we are checking the type and then saving or updating the data.
            //체크한 후 저장하거나 데이터를 업데이트함.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    //if the string is not empty we are calling a add note method to add data to our room database.
                    //문자열이 비어있지 않으면, add note 함수를 호출해서 룸 데이터베이스에 데이터를 추가함.
                    viewModal.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            //opening the new activity on below line
            //새 activity 실행함.
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}


