package com.subhash1e.mailchimp
import java.util.*
import javax.mail.*
import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Message
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import kotlinx.coroutines.DefaultExecutor.thread
import kotlin.concurrent.thread

//import com.touwolf.mailchimp.impl.MailchimpClientImpl

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

//import com.chimpmail.api.ChimpMail
//import com.mailchimp.MailchimpClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btnSendMail)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSubject = findViewById<EditText>(R.id.etSubject)
        val etMessage = findViewById<EditText>(R.id.etMessage)


        btn.setOnClickListener {
                sendMail(etEmail,etSubject,etMessage)
        }
    }

    private fun sendMail(etEmail: EditText, etSubject: EditText, etMessage: EditText) {



            // Set up the mail server
            val host = "smtp.sendgrid.net"
            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.ssl.enable", "true")
                put("mail.smtp.host", host)
                put("mail.smtp.port", "465")
            }

            // Set up authentication
            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication() =
                    PasswordAuthentication("apikey","*****************************")
            })

            try {
                // Create a default MimeMessage object
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress("********"))
                    addRecipient(Message.RecipientType.TO, InternetAddress(etEmail.text.toString()))
                    subject = etSubject.text.toString()
                    setText(etMessage.text.toString())
                }

                // Send the message
                thread(start = true) {
                    Transport.send(message)
                    println("Email sent successfully.")

                    println("running from thread(): ${Thread.currentThread()}")
                }
                Toast.makeText(this,"Mail sent",Toast.LENGTH_LONG).show()



            } catch (e: MessagingException) {
                e.printStackTrace()
            }


    }


}