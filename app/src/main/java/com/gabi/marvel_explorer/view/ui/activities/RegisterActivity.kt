package com.gabi.marvel_explorer.view.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.gabi.marvel_explorer.R
import com.gabi.marvel_explorer.utils.FirebaseFirestoreUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.sdk27.coroutines.onClick


class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mainLayout : RelativeLayout
    private lateinit var formLinearLayout : LinearLayout
    private lateinit var progressBar : ProgressBar
    private lateinit var profilePicTooltip : TextView
    private lateinit var userProfilePic : ImageView
    private lateinit var usernameLabel : TextView
    private lateinit var usernameTooltip : TextView
    private lateinit var usernameError   : TextView
    private lateinit var usernameTextEdit : TextInputEditText
    private lateinit var emailErrorLabel : TextView
    private lateinit var emailLabel : TextView
    private lateinit var emailTextView : AutoCompleteTextView
    private lateinit var passwordLabel : TextView
    private lateinit var passwordErrorLabel : TextView
    private lateinit var passwordTooltip : TextView
    private lateinit var passwordTextEdit  : TextInputEditText
    private lateinit var passwordConfirmTextEdit : TextInputEditText
    private lateinit var confirmLabel : TextView
    private lateinit var authButton : Button

    private var profilePicBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val params = RelativeLayout.LayoutParams(300, 300)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        progressBar.visibility = View.INVISIBLE

        profilePicTooltip = TextView(this)
        userProfilePic = ImageView(this)
        usernameLabel = TextView(this)
        usernameTooltip = TextView(this)
        usernameError = TextView(this)
        usernameTextEdit = TextInputEditText(this)
        emailErrorLabel = TextView(this)
        emailLabel = TextView(this)
        emailTextView = AutoCompleteTextView(this)
        passwordErrorLabel = TextView(this)
        passwordLabel = TextView(this)
        passwordTooltip = TextView(this)
        passwordTextEdit = TextInputEditText(this)
        passwordConfirmTextEdit = TextInputEditText(this)
        confirmLabel = TextView(this)
        authButton = Button(this)

        val mainLayoutParams = RelativeLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

        mainLayout = RelativeLayout(this)
        mainLayout.background = getResources().getDrawable(R.drawable.app_background)
        mainLayout.gravity = Gravity.CENTER_HORIZONTAL
        mainLayout.layoutParams = mainLayoutParams
        mainLayout.setPadding(16, 16, 16, 16)


        val formScrollView = ScrollView(this)


        formLinearLayout = LinearLayout(this)
        formLinearLayout.orientation = LinearLayout.VERTICAL


        profilePicTooltip.text = "Profile picture"
        profilePicTooltip.setTextColor(Color.WHITE)
        profilePicTooltip.setPadding(0, 20, 0, 0)

        userProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.avatar_placeholder))

        userProfilePic.adjustViewBounds = true

        val profilePicLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            400)

        profilePicLayoutParams.setMargins(0,50,0,10)

        profilePicLayoutParams.gravity = Gravity.CENTER
        userProfilePic.layoutParams = profilePicLayoutParams


        val roundedCorners = GradientDrawable()
        roundedCorners.cornerRadius = 10f
        roundedCorners.setStroke(5, Color.WHITE)


        usernameLabel.text ="Username: "
        usernameLabel.setTextColor(Color.WHITE)


        usernameTooltip.text = "Username must not exceed 6 characters"
        usernameTooltip.setTextColor(Color.WHITE)


        usernameError.setTextColor(Color.RED)

        val usernameInputLayout = TextInputLayout(this)


        usernameTextEdit.setTextColor(Color.WHITE)
        usernameTextEdit.setPadding(30, 0, 0, 0)
        usernameTextEdit.inputType = InputType.TYPE_CLASS_TEXT
        usernameTextEdit.setBackgroundColor(Color.WHITE)
        usernameTextEdit.maxLines = 1
        usernameTextEdit.setSingleLine(true)
        usernameTextEdit.setBackground(roundedCorners)

        val usernameTextEditLayoutparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        usernameTextEditLayoutparams.setMargins(0,10,0,10)
        usernameTextEdit.layoutParams = usernameTextEditLayoutparams

        usernameInputLayout.addView(usernameTextEdit)

        val emailInputLayout = TextInputLayout(this)


        emailErrorLabel.setTextColor(Color.RED)


        emailLabel.text ="Email address: "
        emailLabel.setTextColor(Color.WHITE)


        emailTextView.setBackground(roundedCorners)
        emailTextView.setTextColor(Color.WHITE)
        emailTextView.setPadding(30, 0, 0, 0)

        emailTextView.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
        emailTextView.maxLines = 1
        emailTextView.setSingleLine(true)
        val emailTextViewLayoutparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        emailTextViewLayoutparams.setMargins(0,10,0,10)
        emailTextView.layoutParams = emailTextViewLayoutparams


        emailInputLayout.addView(emailTextView)


        val passwordInputLayout = TextInputLayout(this)
        val passwordConfirmInputLayout = TextInputLayout(this)


        passwordErrorLabel.setTextColor(Color.RED)

        passwordLabel.text = "Password: "
        passwordLabel.setTextColor(Color.WHITE)

        passwordTooltip.text = "Password must be at least 6 characters long"
        passwordTooltip.setTextColor(Color.WHITE)


        passwordTextEdit.setTextColor(Color.WHITE)
        passwordTextEdit.setPadding(30, 0, 0, 0)
        passwordTextEdit.setBackground(roundedCorners)
        passwordTextEdit.maxLines = 1
        passwordTextEdit.setSingleLine(true)
        passwordTextEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordTextEdit.transformationMethod = PasswordTransformationMethod.getInstance()


        val passwordTextEditLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        passwordTextEditLayoutParams.setMargins(0,10,0,10)
        passwordTextEdit.layoutParams = passwordTextEditLayoutParams


        confirmLabel.text = "Confirm password:"
        confirmLabel.setTextColor(Color.WHITE)
        confirmLabel.setPadding(0, 20, 0 ,0)

        passwordConfirmTextEdit.setTextColor(Color.WHITE)
        passwordConfirmTextEdit.setPadding(30, 0, 0, 0)

        passwordConfirmTextEdit.setBackground(roundedCorners)
        passwordConfirmTextEdit.maxLines = 1
        passwordConfirmTextEdit.setSingleLine(true)
        passwordConfirmTextEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordConfirmTextEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        val passwordConfirmationLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        passwordConfirmationLayoutParams.setMargins(0,10,0,10)
        passwordConfirmTextEdit.layoutParams = passwordConfirmationLayoutParams


        passwordInputLayout.addView(passwordTextEdit)
        passwordConfirmInputLayout.addView(passwordConfirmTextEdit)

        val loginButtonLayoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                100)
        loginButtonLayoutParams.setMargins(10, 30, 10, 0)

        val loginButton = TextView(this)
        loginButton.text = "Already have an account?"
        loginButton.setTextColor(Color.WHITE)
        loginButton.layoutParams = loginButtonLayoutParams

        loginButton.onClick {
            val login = Intent(this@RegisterActivity, LoginActivity::class.java)
            finish()
            startActivity(login)
        }


        authButton.top = 16
        authButton.text = "Sign up"
        authButton.textAlignment = LinearLayout.TEXT_ALIGNMENT_CENTER

       authButton.setOnClickListener { createAccount(emailTextView.text.toString(),
            passwordTextEdit.text.toString()) }

        userProfilePic.setOnClickListener {
            val photoPickIntent = Intent(Intent.ACTION_PICK)
            photoPickIntent.type = "image/*"
            startActivityForResult(photoPickIntent, 0)
        }

        formLinearLayout.addView(profilePicTooltip)
        profilePicTooltip.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        profilePicTooltip.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(userProfilePic)

        formLinearLayout.addView(usernameLabel)
        usernameLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        usernameLabel.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(usernameInputLayout)
        usernameInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        usernameInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(usernameTooltip)
        usernameTooltip.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        usernameTooltip.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(usernameError)
        usernameError.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        usernameError.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT


        formLinearLayout.addView(emailLabel)
        emailLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        emailLabel.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(emailInputLayout)
        emailInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        emailInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(emailErrorLabel)
        emailErrorLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        emailErrorLabel.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordLabel)
        passwordLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordLabel.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordInputLayout)
        passwordInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordTooltip)
        passwordTooltip.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordTooltip.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(confirmLabel)
        confirmLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        confirmLabel.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordConfirmInputLayout)

        passwordConfirmInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordConfirmInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordErrorLabel)
        passwordErrorLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordErrorLabel.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(authButton)
        authButton.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        authButton.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(loginButton)

        formScrollView.addView(formLinearLayout)
        formLinearLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        formLinearLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        mainLayout.addView(formScrollView)

        mainLayout.addView(progressBar, params)


        formScrollView.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        formScrollView.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

        setContentView(mainLayout)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
            val circleBitmap: Bitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val paint = Paint()
            paint.setShader(shader)
            val canvas = Canvas(circleBitmap)
            canvas.drawCircle((bitmap.width / 2).toFloat(),
                    (bitmap.height / 2).toFloat(), (bitmap.width / 2).toFloat(), paint)

            userProfilePic.imageBitmap = circleBitmap
            profilePicBitmap = circleBitmap

        }
    }


    private fun validateForm(): Boolean {
        var valid = true

        val email = emailTextView.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailErrorLabel.error = "Required."
            emailErrorLabel.text = "E-mail is required."
            valid = false
        } else {
            emailErrorLabel.error = null
            emailErrorLabel.text = ""
        }

        val password = passwordTextEdit.text.toString()
        val confirmPassword = passwordConfirmTextEdit.text.toString()
        if (password.length < 6 || confirmPassword.length < 6) {
            passwordErrorLabel.error = "pw_length"
            passwordErrorLabel.text = "Passwords do not meet the length requirement."
            valid = false

        } else if (password != confirmPassword) {
            passwordErrorLabel.error = "pw_mismatch"
            passwordErrorLabel.text = "Passwords do not match."
            valid = false
        } else {
            passwordErrorLabel.error = null
            passwordErrorLabel.text = ""
        }

        return valid
    }
    private fun createAccount(email: String, password: String) {

        mAuth = FirebaseAuth.getInstance()

        if (!validateForm()) {
            return
        }

        showProgress(true)

        if (profilePicBitmap == null) {
            profilePicBitmap = (userProfilePic.getDrawable() as BitmapDrawable).bitmap
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        showProgress(true)
                        FirebaseFirestoreUtil.initProfile(usernameTextEdit.text.toString(),
                                emailTextView.text.toString(),
                        profilePicBitmap) { finish() }


                    } else {
                        showProgress(false)

                    }

                }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            formLinearLayout.visibility = if (show) View.GONE else View.VISIBLE
            formLinearLayout.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            formLinearLayout.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            progressBar.visibility = if (show) View.VISIBLE else View.GONE
            progressBar.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            progressBar.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
            formLinearLayout.visibility = if (show) View.GONE else View.VISIBLE
        }
    }



}
