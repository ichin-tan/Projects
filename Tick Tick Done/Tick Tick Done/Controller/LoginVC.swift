//
//  LoginVC.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit
import FirebaseCore
import FirebaseFirestore
import FirebaseAuth

class LoginVC: UIViewController {
    
    //MARK: - Outlets & Variables -
        
    @IBOutlet weak var txtLoginEmail: UITextField!
    @IBOutlet weak var txtLoginPassword: UITextField!
    @IBOutlet weak var btnLogin: UIButton!
    @IBOutlet weak var btnEye: UIButton!
    
    //MARK: - Lifecycle methods -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.applyStyle()
        self.setUp()
    }
    
    //MARK: - Custom methods -
    
    func setUp() {
        self.title = Global.AppMessages.login
        self.txtLoginPassword.delegate = self
        self.txtLoginEmail.delegate = self
    }
    
    func applyStyle() {
        Global.setMainBackground(for: self.view)
        self.txtLoginEmail.superview?.layer.borderWidth = 2
        self.txtLoginPassword.superview?.layer.borderWidth = 2
        self.txtLoginEmail.superview?.layer.borderColor = UIColor.appGreen.cgColor
        self.txtLoginPassword.superview?.layer.borderColor = UIColor.appGreen.cgColor
        self.txtLoginEmail.superview?.layer.cornerRadius = 15
        self.txtLoginPassword.superview?.layer.cornerRadius = 15
        self.txtLoginEmail.textColor = UIColor.appGreen
        self.txtLoginPassword.textColor = UIColor.appGreen
        self.btnLogin.superview?.layer.cornerRadius = 15
        self.btnLogin.layer.cornerRadius = 15
        self.txtLoginPassword.isSecureTextEntry = true
        self.btnEye.setBackgroundImage(UIImage(systemName: "eye.fill"), for: .normal)
        self.txtLoginEmail.keyboardType = .emailAddress
    }
    
    func login() {
        if isValidated() {
            let strEmail = self.txtLoginEmail.text?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
            let strPassword = self.txtLoginPassword.text?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
            Auth.auth().signIn(withEmail: strEmail, password: strPassword) { authResult, error in
                if (authResult == nil) {
                    if (error?.localizedDescription == Global.FirebaseErrors.invalidCred) {
                        self.showAlert(message: Global.AppMessages.invalidCred)
                    } else {
                        self.showAlert(message: Global.AppMessages.somethingWentWrong)
                    }
                } else {
                    isUserLoggedIn = true
                    SCENE_DELEGATE.setTabbar()
                }
            }
        }
    }
    
    func isValidated() -> Bool {
        let strEmail = self.txtLoginEmail.text?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
        let strPassword = self.txtLoginPassword.text?.trimmingCharacters(in: .whitespacesAndNewlines) ?? ""
        if (strEmail.isEmpty) {
            self.showAlert(message: Global.AppMessages.enterEmail)
            return false
        } else if !(Global.isValidEmail(strEmail)) {
            self.showAlert(message: Global.AppMessages.enterValidEmail)
            return false
        } else if (strPassword.isEmpty) {
            self.showAlert(message: Global.AppMessages.enterPassword)
            return false
        }
        return true
    }
    
    //MARK: - Action methods -
    
    @IBAction func btnLoginTapped(_ sender: UIButton) {
        self.login()
    }
    
    @IBAction func btnSignupTapped(_ sender: UIButton) {
        let signupVC = STORYBOARD.instantiateViewController(identifier: "SignupVC") as! SignupVC
        self.navigationController?.pushViewController(signupVC, animated: true)
    }
    
    @IBAction func btnEyeTapped(_ sender: UIButton) {
        self.txtLoginPassword.isSecureTextEntry.toggle()
        
        let backImg = self.txtLoginPassword.isSecureTextEntry ? UIImage(systemName: "eye.fill") : UIImage(systemName: "eye.slash.fill")
        
        self.btnEye.setImage(backImg, for: .normal)
    }
}

//MARK: - UITextfield delegate -

extension LoginVC: UITextFieldDelegate {
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if(textField == self.txtLoginEmail) {
            self.txtLoginPassword.becomeFirstResponder()
        } else {
            self.txtLoginPassword.resignFirstResponder()
        }
        return true
    }
    
}
