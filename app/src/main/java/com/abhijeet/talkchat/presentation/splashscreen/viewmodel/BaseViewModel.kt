package com.abhijeet.talkchat.presentation.splashscreen.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import com.abhijeet.talkchat.models.Message
import com.abhijeet.talkchat.presentation.splashscreen.chat_box.ChatDesignModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.collections.emptyList
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class BaseViewModel: ViewModel() {

    // 1
    fun searchUserByPhoneNumber(phoneNumber: String, callback: (ChatDesignModel?)-> Unit){

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null){

            Log.e("BaseViewModel", "Current user is not authenticated")
            callback(null)
            return
        }

        val databaseReference = FirebaseDatabase
            .getInstance()
            .getReference("users")

        databaseReference
            .orderByChild("phoneNumber")
            .equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot){


                    if(snapshot.exists()){
                        val user = snapshot
                            .children.first()
                            .getValue(ChatDesignModel::class.java)
                        callback(user)
                    }else{
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user: ${error.message}")
                    callback(null)

                }
            })
    }


// 2
    fun getChatForUser(userId: String, callback: (List<ChatDesignModel>?) -> Unit){

        val chatref = FirebaseDatabase
            .getInstance()
            .getReference("users/$userId/chats")
        chatref
            .orderByChild("userId")
            .equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = mutableListOf<ChatDesignModel>()

                    for (childSnapshot in snapshot.children){

                        val chat = childSnapshot
                            .getValue(ChatDesignModel::class.java)

                        if (chat!= null){
                            chatList.add(chat)
                            }
                    }
                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel","Error fetching user chats : ${error.message}")

                    callback(emptyList())
                }
            }
        )
    }

    private val _chatList = MutableStateFlow<List<ChatDesignModel>>(emptyList())
    val chatList = _chatList.asStateFlow()

    init {
        loadChatData()
    }



    fun loadChatData(){

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if ( currentUserId != null){

            val chatRef = FirebaseDatabase
                .getInstance()
                .getReference("chats")

            chatRef.orderByChild("userId").equalTo(currentUserId)
                .addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val chatList = mutableListOf<ChatDesignModel>()

                        for (childSnapshot in snapshot.children){

                            val chat = childSnapshot
                                .getValue(ChatDesignModel::class.java)

                            if (chat!= null){
                                chatList.add(chat)
                            }

                        }

                        _chatList.value = chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModel","Error fetching user chats: ${error.message}")
                    }
                })
        }
    }


    fun addChat(newChat: ChatDesignModel){

        val currentUserId= FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId!= null){

            val newChatRef = FirebaseDatabase
                .getInstance()
                .getReference("chats").push()

            val chatWithUser = newChat.copy(userId =currentUserId)
            newChatRef.setValue(chatWithUser).addOnSuccessListener {

                Log.d("BaseViewModel","chat added successfully to firebase")
            }.addOnFailureListener { exception ->
                Log.e("BaseViewModel","Failed to add chat: ${exception.message}")
            }

        }else{
            Log.e("BaseViewModel","No User is authenticated")
        }
    }


    private val databaseReference= FirebaseDatabase.getInstance().reference

    fun sendMessage(senderPhoneNumber:String,receivePhoneNumber: String, messageText: String){

        val messageId = databaseReference.push().key?:return
        val message = Message(senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timeStamp = System.currentTimeMillis()
        )

        databaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receivePhoneNumber)
            .child(messageId)
            .setValue(message)


        databaseReference.child("message")
            .child(receivePhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }


    fun getMessage(
        senderPhoneNumber: String,
        receivePhoneNumber: String,
        onNewMessage: (Message)-> Unit
    ){

        val messageRef = databaseReference.child("message")
            .child(senderPhoneNumber)
            .child(receivePhoneNumber)


        messageRef.addChildEventListener(object : ChildEventListener{

            override fun onChildAdded(snapshot: DataSnapshot, previosChildName: String?) {

                val message = snapshot.getValue(Message::class.java)

                if (message != null){
                    onNewMessage(message)
                }

            }

            override fun onChildChanged(
                p0: DataSnapshot,
                p1: String?
            ) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildMoved(
                p0: DataSnapshot,
                p1: String?
            ) {
            }

            override fun onCancelled(p0: DatabaseError) {


            }
        })
    }


    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receivePhoneNumber: String,
        onLastMessageFetched: (String, String)-> Unit
    ){
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("message")
            .child(senderPhoneNumber)
            .child(receivePhoneNumber)


        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        val lastMessage = snapshot.
                        children
                            .firstOrNull()?.
                            child("message")?.
                            value as?String

                        val timestamp = snapshot.
                        children
                            .firstOrNull()?.
                            child("timestamp")?.
                            value as?String


                        onLastMessageFetched(lastMessage?:"No Message",timestamp?: "--:--")
                    }else{

                        onLastMessageFetched("No message","--:--")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No message","--:--")
                }
            })
    }


    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListedLoaded :(List<ChatDesignModel>)-> Unit
    ){

        val chatList =mutableListOf<ChatDesignModel>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    snapshot.children.forEach{  child ->
                         val phoneNumber = child.key?:return@forEach

                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("image").value as? String

                        val profileImageBitmap = image?.let {decodeBase64toBitmap(it)}

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,phoneNumber){ lastMessage, time->

                            chatList.add(
                                ChatDesignModel(
                                    name=name,
                                    image = image,
                                    message = lastMessage,
                                    time = time
                                )
                            )
                            if (chatList.size == snapshot.childrenCount.toInt()){
                                onChatListedLoaded(chatList)
                            }
                        }

                    }
                }else{
                    onChatListedLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {


                onChatListedLoaded(emptyList())
            }


        })
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBase64toBitmap(
        base64Image: String
    ): Bitmap? {
        return try {

            val decodeByte = Base64.decode(base64Image,
                android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.size)
        }catch (e: IOException){
            null
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun base64ToBitmap(base64String: String): Bitmap?{
        return try {

            val decodeByte = Base64.decode(base64String,
                android.util.Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(decodeByte)
            BitmapFactory.decodeStream(inputStream)
        }catch (e: IOException){
            null
        }

    }

}