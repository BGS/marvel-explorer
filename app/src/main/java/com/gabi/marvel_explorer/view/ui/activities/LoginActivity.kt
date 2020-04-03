package com.gabi.marvel_explorer.view.ui.activities


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.widget.*
import com.gabi.marvel_explorer.R
import com.gabi.marvel_explorer.utils.FirebaseAuthUtil
import org.jetbrains.anko.sdk27.coroutines.onClick


class LoginActivity : AppCompatActivity() {
    private lateinit var formLinearLayout : LinearLayout
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayoutParams = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

        var mainLayout = RelativeLayout(this)
        mainLayout.background = resources.getDrawable(R.drawable.app_background)


        mainLayout.gravity = Gravity.CENTER_HORIZONTAL
        mainLayout.layoutParams = mainLayoutParams
        mainLayout.setPadding(16, 16, 16, 16)

        val params = RelativeLayout.LayoutParams(300, 300)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        progressBar.visibility = View.INVISIBLE


        val formScrollView = ScrollView(this)


        val errorLabel = TextView(this)
        errorLabel.setTextColor(Color.RED)

        formLinearLayout = LinearLayout(this)
        formLinearLayout.orientation = LinearLayout.VERTICAL

        val userProfilePic = ImageView(this)
        userProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.avatar_placeholder))
        userProfilePic.adjustViewBounds = true
        val profilePicLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            400)

        profilePicLayoutParams.setMargins(0,50,0,10)
        userProfilePic.layoutParams = profilePicLayoutParams

        val roundedCorners = GradientDrawable()
        roundedCorners.cornerRadius = 10f
        roundedCorners.setStroke(5, Color.WHITE)

        val emailInputLayout = TextInputLayout(this)

        val emailLabel = TextView(this)
        emailLabel.text = "Email address:"
        emailLabel.setTextColor(Color.WHITE)

        val emailTextView = AutoCompleteTextView(this)
        emailTextView.setBackground(roundedCorners)
        emailTextView.setTextColor(Color.WHITE)
        emailTextView.setPadding(30, 0, 0, 0)
        emailTextView.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
        emailTextView.maxLines = 1
        emailTextView.setSingleLine(true)

        val emailTextViewEditLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        emailTextViewEditLayoutParams.setMargins(0,10,0,10)
        emailTextView.layoutParams = emailTextViewEditLayoutParams

        emailInputLayout.addView(emailTextView)


        val passwordInputLayout = TextInputLayout(this)

        val passwordLabel = TextView(this)
        passwordLabel.setTextColor(Color.WHITE)
        passwordLabel.text = "Password"

        val passwordTextEdit = TextInputEditText(this)
        passwordTextEdit.setBackground(roundedCorners)
        passwordTextEdit.setTextColor(Color.WHITE)
        passwordTextEdit.setPadding(30, 0, 0, 0)
        passwordTextEdit.maxLines = 1
        passwordTextEdit.setSingleLine(true)
        passwordTextEdit.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordTextEdit.transformationMethod = PasswordTransformationMethod.getInstance()

        val passwordTexteEditEditLayoutparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            100)

        passwordTexteEditEditLayoutparams.setMargins(0,10,0,10)
        passwordTextEdit.layoutParams = passwordTexteEditEditLayoutparams

        passwordInputLayout.addView(passwordTextEdit)

        val registerButtonLayoutParams =  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                100)
        registerButtonLayoutParams.setMargins(10, 30, 10, 0)

        val registerButton = TextView(this)
        registerButton.text = "Don't have an account?"
        registerButton.setTextColor(Color.WHITE)
        registerButton.layoutParams = registerButtonLayoutParams

        registerButton.onClick {
            val register = Intent(this@LoginActivity, RegisterActivity::class.java)
            finish()
            startActivity(register)
        }

        val authButton = Button(this)
        authButton.top = 16
        authButton.text = "Sign In"
        authButton.textAlignment = LinearLayout.TEXT_ALIGNMENT_CENTER

        authButton.onClick {
            showProgress(true)
            val email = emailTextView.text.toString()
            val password = passwordTextEdit.text.toString()

            FirebaseAuthUtil.authenticateUser(email, password) {
                if (it) {
                    val home = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(home)
                } else {
                    errorLabel.error = ""
                    errorLabel.text = "Invalid authentication data"
                    showProgress(false)
                }
            }

        }

        formLinearLayout.addView(userProfilePic)

        formLinearLayout.addView(emailLabel)
        emailLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        emailLabel.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(emailInputLayout)
        emailInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        emailInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordLabel)
        passwordLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordLabel.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(passwordInputLayout)
        passwordInputLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        passwordInputLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(errorLabel)
        errorLabel.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        errorLabel.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(authButton)
        authButton.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        authButton.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        formLinearLayout.addView(registerButton)
        formScrollView.addView(formLinearLayout)
        formLinearLayout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        formLinearLayout.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT

        mainLayout.addView(formScrollView)
        formScrollView.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        formScrollView.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT

        mainLayout.addView(progressBar, params)


        setContentView(mainLayout)
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
