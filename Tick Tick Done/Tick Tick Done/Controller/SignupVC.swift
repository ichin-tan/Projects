//
//  SignupVC.swift
//  Tick Tick Done
//
//  Created by CP on 30/12/24.
//

import Foundation
import UIKit

class SignupVC: UIViewController {
    
    //MARK: - Outlets & Variables -
    
    @IBOutlet weak var txtSignupEmail: UITextField!
    @IBOutlet weak var txtSignupPassword: UITextField!
    @IBOutlet weak var btnSignup: UIButton!
    @IBOutlet weak var btnEye: UIButton!
    
    //MARK: - Lifecycle methods -

    override func viewDidLoad() {
        super.viewDidLoad()
        self.applyStyle()
        self.setUp()
    }
        
    //MARK: - Custom methods -
    
    func setUp() {
        
    }
    
    func applyStyle() {
        self.title = "Signup"
        self.navigationItem.setHidesBackButton(true, animated: true)
        self.addBackButton()
        self.view.setGradientWith(hex1: "AFF9B7",hex2: "EEFAF0")
        self.txtSignupEmail.superview?.layer.borderWidth = 2
        self.txtSignupPassword.superview?.layer.borderWidth = 2
        self.txtSignupEmail.superview?.layer.borderColor = UIColor.appGreen.cgColor
        self.txtSignupPassword.superview?.layer.borderColor = UIColor.appGreen.cgColor
        self.txtSignupEmail.superview?.layer.cornerRadius = 15
        self.txtSignupPassword.superview?.layer.cornerRadius = 15
        self.txtSignupEmail.textColor = UIColor.appGreen
        self.txtSignupPassword.textColor = UIColor.appGreen
        self.btnSignup.superview?.layer.cornerRadius = 15
        self.btnSignup.layer.cornerRadius = 15
        self.txtSignupPassword.isSecureTextEntry = true
        self.btnEye.setBackgroundImage(UIImage(systemName: "eye.fill"), for: .normal)
    }
    
    func addBackButton() {
        let backbutton = UIButton(type: .custom)
        backbutton.setImage(UIImage(systemName: "arrowshape.left.fill"), for: .normal)
        backbutton.tintColor = UIColor.appGreen
        backbutton.addTarget(self, action: #selector(btnBackTapped), for: .touchUpInside)
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(customView: backbutton)
    }
    
    //MARK: - Action methods -
        
    @IBAction func btnSignupTapped(_ sender: UIButton) {
        
    }
    
    @IBAction func btnEyeTapped(_ sender: UIButton) {
        self.txtSignupPassword.isSecureTextEntry.toggle()
        
        let backImg = self.txtSignupPassword.isSecureTextEntry ? UIImage(systemName: "eye.fill") : UIImage(systemName: "eye.slash.fill")
        
        self.btnEye.setImage(backImg, for: .normal)
    }
    
    @objc func btnBackTapped() {
        self.navigationController?.popViewController(animated: true)
    }
}
