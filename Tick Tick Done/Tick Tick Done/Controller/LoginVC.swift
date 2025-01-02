//
//  LoginVC.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit

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
        self.title = "Login"
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
    
    //MARK: - Action methods -
    
    @IBAction func btnLoginTapped(_ sender: UIButton) {
        // Firebase Login here
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
