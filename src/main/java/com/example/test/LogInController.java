package com.example.test;

import com.example.test.DAO.AccountDAO;
import com.example.test.entities.PersonalAccount;
import com.example.test.utils.FieldVerifier;
import com.example.test.utils.ImageConverter;
import com.example.test.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class LogInController {
    //TODO add cleanup temp when the app is shut down
    public ImageView image;
    private Stage stage;
    private Scene scene;

    private Image openEye = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/eye-close-up.png")));
    private Image closedEye = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/eyebrow.png")));
    private boolean passwordIsShown = false;

    @FXML
    private ImageView showPassword;
    @FXML
    private Label forgot;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button login;
    @FXML
    private Label messageLabel;
    @FXML
    protected void onShowPassword(){
        passwordIsShown = !passwordIsShown;
        if (passwordIsShown) {
            showPassword.setImage(openEye);
        } else {
            showPassword.setImage(closedEye);
        }

    }
    @FXML
    protected void onSubmit(){
        boolean userIsValid = FieldVerifier.emailIsValid(user) || FieldVerifier.phoneIsValid(user);
        boolean passIsValid = FieldVerifier.isValid(pass);
        if (userIsValid && passIsValid){
            boolean loginIsValid = FieldVerifier.isValid(user, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            FieldVerifier.isValid(pass, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            if (loginIsValid){
                System.out.println("valid login!");
                AccountDAO.loadUser(user.getText());
                System.out.println(PersonalAccount.getInstance());
                image.setImage(new Image(ImageConverter.convertByteArrayToInputStream(PersonalAccount.getInstance().getProfilePicture())));
            }
            else {
                messageLabel.setText("Incorrect Credentials!");
            }
        }
    }
    @FXML
    protected void onSignUp() throws Exception {
        SceneSwitcher.goTo(getClass(),"accountType",login);
    }
}